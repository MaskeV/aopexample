package com.netraa.aopexample.document;


import org.springframework.data.annotation.Id;
import org.springframework.data.elasticsearch.annotations.DateFormat;
import org.springframework.data.elasticsearch.annotations.Document;
import org.springframework.data.elasticsearch.annotations.Field;
import org.springframework.data.elasticsearch.annotations.FieldType;

import java.time.Instant;
import java.time.format.DateTimeFormatter;
import java.util.UUID;

@Document(indexName = "metricsperhour")
public class MetricsPerHour {

    @Id
    private String id; // granularity_serviceId_epochSeconds

    @Field(type = FieldType.Long)
    private Long serviceId;

    @Field(type = FieldType.Keyword)
    private String serviceName;

    @Field(type = FieldType.Date, format = DateFormat.date_time)
    private Instant bucketStart;

    private long reqCount;
    private long totalResponseTimeMs;
    private double avgResponseTimeMs;
    private int minResponseTimeMs = Integer.MAX_VALUE;
    private int maxResponseTimeMs = Integer.MIN_VALUE;
    private long errorCount;

    public static String idFor(Long serviceId, Instant bucketStart) {
        return   "minute_" + serviceId + "_" + DateTimeFormatter.ofPattern("yyyyMMddHHmm").format(bucketStart);
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public Long getServiceId() {
        return serviceId;
    }

    public void setServiceId(Long serviceId) {
        this.serviceId = serviceId;
    }

    public String getServiceName() {
        return serviceName;
    }

    public void setServiceName(String serviceName) {
        this.serviceName = serviceName;
    }

    public Instant getBucketStart() {
        return bucketStart;
    }

    public void setBucketStart(Instant bucketStart) {
        this.bucketStart = bucketStart;
    }

    public long getReqCount() {
        return reqCount;
    }

    public void setReqCount(long reqCount) {
        this.reqCount = reqCount;
    }

    public long getTotalResponseTimeMs() {
        return totalResponseTimeMs;
    }

    public void setTotalResponseTimeMs(long totalResponseTimeMs) {
        this.totalResponseTimeMs = totalResponseTimeMs;
    }

    public double getAvgResponseTimeMs() {
        return avgResponseTimeMs;
    }

    public void setAvgResponseTimeMs(double avgResponseTimeMs) {
        this.avgResponseTimeMs = avgResponseTimeMs;
    }

    public int getMinResponseTimeMs() {
        return minResponseTimeMs;
    }

    public void setMinResponseTimeMs(int minResponseTimeMs) {
        this.minResponseTimeMs = minResponseTimeMs;
    }

    public int getMaxResponseTimeMs() {
        return maxResponseTimeMs;
    }

    public void setMaxResponseTimeMs(int maxResponseTimeMs) {
        this.maxResponseTimeMs = maxResponseTimeMs;
    }

    public long getErrorCount() {
        return errorCount;
    }

    public void setErrorCount(long errorCount) {
        this.errorCount = errorCount;
    }
}
