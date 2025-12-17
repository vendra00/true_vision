package com.t1tanic.true_vision.controller;

import com.t1tanic.true_vision.service.ReportService;
import lombok.RequiredArgsConstructor;
import org.springframework.core.io.InputStreamResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.UUID;

/**
 * Controller for exporting poll reports in CSV and Excel formats.
 */
@RestController
@RequestMapping("/api/v1/reports")
@RequiredArgsConstructor
public class ReportController {

    private final ReportService reportService;

    /**
     * Exports the poll report in CSV format.
     *
     * @param pollId the ID of the poll
     * @return the CSV file as a downloadable resource
     */
    @GetMapping("/polls/{pollId}/csv")
    public ResponseEntity<InputStreamResource> exportCsv(@PathVariable UUID pollId) {
        String filename = "poll_report_" + pollId + ".csv";
        InputStreamResource file = new InputStreamResource(reportService.generatePollCsv(pollId));

        return ResponseEntity.ok()
                .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=" + filename)
                .contentType(MediaType.parseMediaType("text/csv"))
                .body(file);
    }

    /**
     * Exports the poll report in Excel format.
     *
     * @param pollId the ID of the poll
     * @return the Excel file as a downloadable response
     */
    @GetMapping("/polls/{pollId}/excel")
    public ResponseEntity<InputStreamResource> exportExcel(@PathVariable UUID pollId) {
        String filename = "poll_report_" + pollId + ".xlsx";
        InputStreamResource file = new InputStreamResource(reportService.generatePollExcel(pollId));

        return ResponseEntity.ok()
                .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=" + filename)
                .contentType(MediaType.parseMediaType("application/vnd.openxmlformats-officedocument.spreadsheetml.sheet"))
                .body(file);
    }
}
