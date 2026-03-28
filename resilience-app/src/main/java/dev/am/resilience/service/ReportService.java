package dev.am.resilience.service;

import lombok.extern.slf4j.Slf4j;
import org.springframework.resilience.annotation.ConcurrencyLimit;
import org.springframework.stereotype.Service;

@Service
@Slf4j
public class ReportService {

    @ConcurrencyLimit(2)
    public String generateReport(String reportType) {
        log.info("Starting report generation");

        try {
            Thread.sleep(10000);    // simulate a long-running task
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }

        log.info("Report generation completed");
        return "Report generated: " + reportType;
    }
}
