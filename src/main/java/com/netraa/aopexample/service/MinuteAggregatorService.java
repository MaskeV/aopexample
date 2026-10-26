package com.netraa.aopexample.service;

import com.netraa.aopexample.document.MetricsPerMinute;
import com.netraa.aopexample.entity.Log;
import com.netraa.aopexample.repository.MetricPerMinuteRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.Optional;
import java.util.concurrent.ConcurrentHashMap;

@Service
public class MinuteAggregatorService {

    private static final Logger LOG = LoggerFactory.getLogger(MinuteAggregatorService.class);
    private final ConcurrentHashMap<String, MetricsPerMinute> map = new ConcurrentHashMap<>();
    private final AggregateService aggregateService;
    private final MetricPerMinuteRepository metricsRepository;

    public MinuteAggregatorService(AggregateService aggregateService, MetricPerMinuteRepository metricsRepository) {
        this.aggregateService = aggregateService;
        this.metricsRepository = metricsRepository;
    }

    public void add(Log log) {
        if (log.getTimestamp() == null || log.getService() == null) {
            LOG.warn("Log ignored, timestamp or service missing: {}", log);
            return;
        }

        MetricsPerMinute metric = aggregateService.newMinuteBucket(log);

        map.compute(metric.getId(), (id, existing) -> {
            MetricsPerMinute bucket = (existing != null) ? existing : metric;
            // Ensure aggregateService.addLog contains internal synchronization if bucket is modified elsewhere
            aggregateService.addLog(bucket, log);
            return bucket;
        });
    }

    @Scheduled(cron = "0 * * * * *")
    public void flush() {
        flushOlderThan(Instant.now().truncatedTo(ChronoUnit.MINUTES));
    }

    public void flushOlderThan(Instant cutoff) {
        for (MetricsPerMinute metric : map.values()) {
            if (!metric.getBucketStart().isBefore(cutoff)) {
                continue;
            }

            if (!map.remove(metric.getId(), metric)) {
                continue;
            }

            try {
                MetricsPerMinute toSave = metric;
                Optional<MetricsPerMinute> old = metricsRepository.findById(metric.getId());
                if (old.isPresent()) {
                    old.get().merge(metric);
                    toSave = old.get();
                }
                metricsRepository.save(toSave);
                LOG.info("Saved minute bucket {} count={}", toSave.getId(), toSave.getReqCount());
            } catch (Exception ex) {
                LOG.error("Saving minute bucket {} failed, putting back to retry", metric.getId(), ex);

                // FIXED RETRY LOGIC: Avoid self-merging instance references
                map.merge(metric.getId(), metric, (existingInMap, failedMetric) -> {
                    if (existingInMap != failedMetric) {
                        existingInMap.merge(failedMetric);
                    }
                    return existingInMap;
                });
            }
        }
    }
}
