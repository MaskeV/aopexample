package com.netraa.aopexample.entity;

import com.netraa.aopexample.entity.ServiceInfo;

import java.time.Instant;

public class Log {


    private Instant timestamp;
    private String level;
    private ServiceInfo service;
    private Integer responseTimeMs;
    private Integer statusCode;



    public Instant getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(Instant timestamp) {
        this.timestamp = timestamp;
    }

    public String getLevel() {
        return level;
    }

    public void setLevel(String level) {
        this.level = level;
    }

    public ServiceInfo getService() {
        return service;
    }

    public void setService(ServiceInfo service) {
        this.service = service;
    }

    public Integer getResponseTimeMs() {
        return responseTimeMs;
    }

    public void setResponseTimeMs(Integer responseTimeMs) {
        this.responseTimeMs = responseTimeMs;
    }

    public Integer getStatusCode() {
        return statusCode;
    }

    public void setStatusCode(Integer statusCode) {
        this.statusCode = statusCode;
    }

    @Override
    public String toString() {
        return "Log{" +
                ", timestamp=" + timestamp +
                ", level='" + level + '\'' +
                ", service=" + service +
                ", responseTime=" + responseTimeMs +
                ", statusCode=" + statusCode +
                '}';
    }


}
