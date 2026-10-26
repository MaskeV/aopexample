package com.netraa.aopexample.service;

import com.netraa.aopexample.document.MetricsPerMinute;
import com.netraa.aopexample.entity.Log;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.time.temporal.ChronoUnit;

@Service
public class AggregateService {

    public Instant minuteOf(Instant ts){
        return ts.truncatedTo(ChronoUnit.MINUTES) ;
    }

    public MetricsPerMinute newMinuteBucket(Log log){
        Instant start = minuteOf(log.getTimestamp());
        MetricsPerMinute metric = new MetricsPerMinute();
        metric.setId(MetricsPerMinute.idFor(log.getService().getId(),start));
        metric.setServiceId(log.getService().getId());
        metric.setServiceName(log.getService().getName());
        metric.setBucketStart(start);
        return metric;
    }

    public void addLog(MetricsPerMinute metric, Log log){
        int rt = log.getResponseTimeMs()== null ? 0 : log.getResponseTimeMs();
        metric.setReqCount(metric.getReqCount()+1);
        metric.setTotalResponseTimeMs(metric.getTotalResponseTimeMs()+rt);
        metric.setMinResponseTimeMs(Math.min(metric.getMinResponseTimeMs(),rt));
        metric.setMaxResponseTimeMs(Math.max(metric.getMaxResponseTimeMs(),rt));
        if(log.getStatusCode()!=null && log.getStatusCode()>=500){
            metric.setErrorCount(metric.getErrorCount()+1);
        }
       metric.recomputeAvg();
    }




}
