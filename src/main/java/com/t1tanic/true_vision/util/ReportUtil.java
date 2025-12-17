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
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Map;
import java.util.UUID;
import java.util.stream.Collectors;

/**
 * Utility class for generating reports in CSV and Excel formats.
 *
 * @author Gabriel Vendramini
 */
@Slf4j
@Component
public class ReportUtil {

    private static final DateTimeFormatter DATE_FORMATTER = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");

    /**
     * Generates a CSV Byte Stream from poll data.
     */
    public ByteArrayInputStream buildCsv(PollResultResponse globalData,
                                         PollDashboardResponse dashboard,
                                         Map<Integer, Long> heatmap) {
        try (ByteArrayOutputStream out = new ByteArrayOutputStream();
             CSVPrinter csvPrinter = new CSVPrinter(new PrintWriter(out), CSVFormat.DEFAULT)) {

            // --- CORPORATE HEADER ---
            writeHeader(csvPrinter, globalData.pollTitle(), globalData.pollId());

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

            CellStyle headerStyle = createHeaderStyle(workbook);
            Sheet sheet = workbook.createSheet("Official Report");

            // --- CORPORATE HEADER (Rows 0-3) ---
            int currentRow = 0;
            currentRow = addExcelHeader(sheet, currentRow, "TRUE VISION - BARCELONA GOVERNANCE PLATFORM", headerStyle);
            currentRow = addExcelHeader(sheet, currentRow, "Poll Title: " + globalData.pollTitle(), null);
            currentRow = addExcelHeader(sheet, currentRow, "Poll ID: " + globalData.pollId(), null);
            currentRow = addExcelHeader(sheet, currentRow, "Export Date: " + LocalDateTime.now().format(DATE_FORMATTER), null);
            currentRow++; // Blank spacer row

            // --- DATA TABLE (Starts after the header) ---
            // Explicitly casting or using the specific type in the collector solves the "Object" resolution error
            Map<String, Long> resultsMap = globalData.results().stream()
                    .collect(java.util.stream.Collectors.toMap(
                            PollResultResponse.OptionCount::optionText, // Explicitly recognized as OptionCount record
                            PollResultResponse.OptionCount::count
                    ));

            fillExcelData(sheet, currentRow, "Option", "Votes", resultsMap);

            // --- ADDITIONAL SHEETS (Optional) ---
            // You can use the same logic for Districts and Age groups in new tabs

            workbook.write(out);
            return new ByteArrayInputStream(out.toByteArray());
        } catch (IOException e) {
            log.error("Excel Generation Error for poll: {}", globalData.pollId(), e);
            throw new java.io.UncheckedIOException(e);
        }
    }

    private void writeHeader(CSVPrinter printer, String title, UUID id) throws IOException {
        printer.printRecord("TRUE VISION - BARCELONA GOVERNANCE PLATFORM");
        printer.printRecord("Poll Title: " + title);
        printer.printRecord("Poll ID: " + id);
        printer.printRecord("Export Date: " + LocalDateTime.now().format(DATE_FORMATTER));
        printer.println();
    }

    private int addExcelHeader(Sheet sheet, int rowIdx, String value, CellStyle style) {
        Row row = sheet.createRow(rowIdx);
        Cell cell = row.createCell(0);
        cell.setCellValue(value);
        if (style != null) cell.setCellStyle(style);
        return rowIdx + 1;
    }

    private CellStyle createHeaderStyle(Workbook workbook) {
        CellStyle style = workbook.createCellStyle();
        Font font = workbook.createFont();
        font.setBold(true);
        font.setFontHeightInPoints((short) 14);
        style.setFont(font);
        return style;
    }

    private void writeCsvSection(CSVPrinter printer, String title, String h1, String h2, Map<String, Long> data) throws IOException {
        printer.printRecord(title);
        printer.printRecord(h1, h2);
        for (var entry : data.entrySet()) {
            printer.printRecord(entry.getKey(), entry.getValue());
        }
        printer.println();
    }

    /**
     * Updated to accept a starting row index so it doesn't overwrite the header.
     */
    private void fillExcelData(Sheet sheet, int startRow, String col1, String col2, Map<String, Long> data) {
        Row header = sheet.createRow(startRow);
        header.createCell(0).setCellValue(col1);
        header.createCell(1).setCellValue(col2);

        int rowIdx = startRow + 1;
        for (var entry : data.entrySet()) {
            Row row = sheet.createRow(rowIdx++);
            row.createCell(0).setCellValue(entry.getKey());
            row.createCell(1).setCellValue(entry.getValue());
        }
        sheet.autoSizeColumn(0);
        sheet.autoSizeColumn(1);
    }
}
