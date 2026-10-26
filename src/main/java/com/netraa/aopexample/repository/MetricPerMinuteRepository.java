package com.netraa.aopexample.repository;

import com.netraa.aopexample.document.MetricsPerMinute;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MetricPerMinuteRepository extends ElasticsearchRepository<MetricsPerMinute,String> {
}
