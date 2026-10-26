package com.netraa.aopexample.service;

import com.netraa.aopexample.dto.LogReqDto;
import com.netraa.aopexample.entity.Log;
import com.netraa.aopexample.entity.ServiceInfo;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class LogService {

    private final ServiceInfoService serviceInfoService;
    private final MinuteAggregatorService minuteAggregatorService;

    public LogService(ServiceInfoService serviceInfoService, MinuteAggregatorService minuteAggregatorService) {
        this.serviceInfoService = serviceInfoService;
        this.minuteAggregatorService = minuteAggregatorService;
    }

    public void saveLog(LogReqDto logReqDto){
        minuteAggregatorService.add(convertToLog(logReqDto));
    }

    public void saveLogs(List<LogReqDto> logs){
        for(LogReqDto log:logs){
            saveLog(log);
        }
    }

    private Log convertToLog(LogReqDto dto) {
        Log log = new Log();
        log.setTimestamp(dto.getTimestamp());
        log.setResponseTimeMs(dto.getResponseTimeMs());
        log.setLevel(dto.getLevel());
        ServiceInfo service = serviceInfoService.saveService(dto.getService());
        log.setService(service);
        log.setStatusCode(dto.getStatusCode());
        return log;
    }


}
