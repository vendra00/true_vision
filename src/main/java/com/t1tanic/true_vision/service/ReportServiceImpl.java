package com.t1tanic.true_vision.service;

import com.t1tanic.true_vision.util.ReportUtil;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.poi.ss.usermodel.*;
import org.springframework.stereotype.Service;

import java.io.*;
import java.util.UUID;

@Slf4j
@Service
@RequiredArgsConstructor
public class ReportServiceImpl implements ReportService {

    private final AnalysisService analysisService;
    private final ReportUtil reportUtil; // Inject the utility

    @Override
    public ByteArrayInputStream generatePollCsv(UUID pollId) {
        return reportUtil.buildCsv(
                analysisService.getGlobalResults(pollId),
                analysisService.getPollDashboard(pollId),
                analysisService.getParticipationHeatmap(pollId)
        );
    }

    @Override
    public ByteArrayInputStream generatePollExcel(UUID pollId) {
        return reportUtil.buildExcel(
                analysisService.getGlobalResults(pollId),
                analysisService.getPollDashboard(pollId),
                analysisService.getParticipationHeatmap(pollId)
        );
    }
}
