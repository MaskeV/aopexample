package com.netraa.aopexample.controller;




import com.netraa.aopexample.dto.LogReqDto;
import com.netraa.aopexample.service.LogService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/logs")
public class LogController {

    private LogService logService;

    public LogController(LogService logService) {
        this.logService = logService;
    }

    @PostMapping()
    public ResponseEntity<String> saveLog(@RequestBody LogReqDto logReqDto){
        logService.saveLog(logReqDto);
        return ResponseEntity.status(HttpStatus.CREATED).body("Log saved successfully");

    }

    @PostMapping("/addAll")
    public ResponseEntity<String> saveLog(@RequestBody List<LogReqDto> logs){
        logService.saveLogs(logs);
        return ResponseEntity.status(HttpStatus.CREATED).body("Log saved successfully");

    }


}
