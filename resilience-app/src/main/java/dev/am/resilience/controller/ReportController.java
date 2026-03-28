package dev.am.resilience.controller;

import dev.am.resilience.service.ReportService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
class ReportController {
    private final ReportService reportService;

    @GetMapping("/report")
    String generateReport(@RequestParam(defaultValue = "sales") String reportType) {
        return reportService.generateReport(reportType);
    }
}
