package com.t1tanic.true_vision.util;

import com.t1tanic.true_vision.dto.poll.PollDashboardResponse;
import com.t1tanic.true_vision.dto.poll.PollResultResponse;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVPrinter;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.stereotype.Component;

import java.io.*;
import java.util.Map;
import java.util.stream.Collectors;

@Slf4j
@Component
public class ReportUtil {

    /**
     * Generates a CSV Byte Stream from poll data.
     */
    public ByteArrayInputStream buildCsv(PollResultResponse globalData,
                                         PollDashboardResponse dashboard,
                                         Map<Integer, Long> heatmap) {
        try (ByteArrayOutputStream out = new ByteArrayOutputStream();
             CSVPrinter csvPrinter = new CSVPrinter(new PrintWriter(out), CSVFormat.DEFAULT)) {

            writeCsvSection(csvPrinter, "--- GLOBAL RESULTS ---", "Option", "Votes",
                    globalData.results().stream().collect(Collectors.toMap(PollResultResponse.OptionCount::optionText, PollResultResponse.OptionCount::count)));

            writeCsvSection(csvPrinter, "--- DISTRICT BREAKDOWN ---", "District", "Votes",
                    dashboard.votesByDistrict().entrySet().stream().collect(Collectors.toMap(e -> e.getKey().name(), Map.Entry::getValue)));

            writeCsvSection(csvPrinter, "--- AGE BREAKDOWN ---", "Age Range", "Votes",
                    dashboard.votesByAgeGroup().entrySet().stream().collect(Collectors.toMap(e -> e.getKey().name(), Map.Entry::getValue)));

            csvPrinter.flush();
            return new ByteArrayInputStream(out.toByteArray());
        } catch (IOException e) {
            log.error("CSV Generation Error", e);
            throw new UncheckedIOException(e);
        }
    }

    /**
     * Generates an Excel (XLSX) Byte Stream with multiple tabs.
     */
    public ByteArrayInputStream buildExcel(PollResultResponse globalData,
                                           PollDashboardResponse dashboard,
                                           Map<Integer, Long> heatmap) {
        try (Workbook workbook = new XSSFWorkbook();
             ByteArrayOutputStream out = new ByteArrayOutputStream()) {

            fillExcelSheet(workbook.createSheet("Global"), "Option", "Votes",
                    globalData.results().stream().collect(Collectors.toMap(PollResultResponse.OptionCount::optionText, PollResultResponse.OptionCount::count)));

            fillExcelSheet(workbook.createSheet("Districts"), "District", "Votes",
                    dashboard.votesByDistrict().entrySet().stream().collect(Collectors.toMap(e -> e.getKey().name(), Map.Entry::getValue)));

            fillExcelSheet(workbook.createSheet("Age Groups"), "Age Range", "Votes",
                    dashboard.votesByAgeGroup().entrySet().stream().collect(Collectors.toMap(e -> e.getKey().name(), Map.Entry::getValue)));

            workbook.write(out);
            return new ByteArrayInputStream(out.toByteArray());
        } catch (IOException e) {
            log.error("Excel Generation Error", e);
            throw new UncheckedIOException(e);
        }
    }

    private void writeCsvSection(CSVPrinter printer, String title, String h1, String h2, Map<String, Long> data) throws IOException {
        printer.printRecord(title);
        printer.printRecord(h1, h2);
        for (var entry : data.entrySet()) {
            printer.printRecord(entry.getKey(), entry.getValue());
        }
        printer.println();
    }

    private void fillExcelSheet(Sheet sheet, String col1, String col2, Map<String, Long> data) {
        Row header = sheet.createRow(0);
        header.createCell(0).setCellValue(col1);
        header.createCell(1).setCellValue(col2);
        int rowIdx = 1;
        for (var entry : data.entrySet()) {
            Row row = sheet.createRow(rowIdx++);
            row.createCell(0).setCellValue(entry.getKey());
            row.createCell(1).setCellValue(entry.getValue());
        }
        sheet.autoSizeColumn(0);
        sheet.autoSizeColumn(1);
    }
}
