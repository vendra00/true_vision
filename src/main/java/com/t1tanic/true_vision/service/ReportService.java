package com.t1tanic.true_vision.service;

import java.io.ByteArrayInputStream;
import java.util.UUID;

public interface ReportService {
    ByteArrayInputStream generatePollCsv(UUID pollId);
    ByteArrayInputStream generatePollExcel(UUID pollId);
}
