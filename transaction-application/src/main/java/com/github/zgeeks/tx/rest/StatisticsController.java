package com.github.zgeeks.tx.rest;

import com.github.zgeeks.tx.monitoring.MonitorService;
import com.github.zgeeks.tx.monitoring.Statistics;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class StatisticsController {

    @Autowired
    private MonitorService monitorService;

    @GetMapping("/transactions/statistics")
    public ResponseEntity<StatisticsDto> transactions() {
        Statistics statistics = monitorService.getStatistics();
        return ResponseEntity.status(HttpStatus.OK)
            .contentType(MediaType.APPLICATION_JSON)
            .body(new StatisticsDto(statistics.sum(), statistics.max(), statistics.min(), statistics.avg(), statistics.count()));
    }
}
