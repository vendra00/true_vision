package com.t1tanic.true_vision.controller;

import com.t1tanic.true_vision.dto.poll.PollDashboardResponse;
import com.t1tanic.true_vision.dto.poll.PollResultResponse;
import com.t1tanic.true_vision.enums.AgeRange;
import com.t1tanic.true_vision.enums.CityDistrict;
import com.t1tanic.true_vision.service.AnalysisService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;
import java.util.UUID;

@Slf4j
@RestController
@RequestMapping("/api/v1/analysis")
@RequiredArgsConstructor
public class AnalysisController {

    private final AnalysisService analysisService;

    /**
     * Generates a full dashboard summary including all districts and age ranges.
     * GET /api/v1/analysis/polls/{pollId}/dashboard
     */
    @GetMapping("/polls/{pollId}/dashboard")
    public ResponseEntity<PollDashboardResponse> getDashboard(@PathVariable UUID pollId) {
        log.info("API Request: Comprehensive dashboard for poll {}", pollId);
        return ResponseEntity.ok(analysisService.getPollDashboard(pollId));
    }

    /**
     * Gets the current global standings for a poll (Total tally).
     * GET /api/v1/analysis/polls/{pollId}/global
     */
    @GetMapping("/polls/{pollId}/global")
    public ResponseEntity<PollResultResponse> getGlobalResults(@PathVariable UUID pollId) {
        log.info("API Request: Global results for poll {}", pollId);
        return ResponseEntity.ok(analysisService.getGlobalResults(pollId));
    }

    /**
     * Gets poll results filtered by a specific Barcelona district.
     * GET /api/v1/analysis/polls/{pollId}/district?district=EIXAMPLE
     */
    @GetMapping("/polls/{pollId}/district")
    public ResponseEntity<PollResultResponse> getByDistrict(@PathVariable UUID pollId, @RequestParam CityDistrict district) {
        log.info("API Request: Results for poll {} in district {}", pollId, district);
        return ResponseEntity.ok(analysisService.getResultsByDistrict(pollId, district));
    }

    /**
     * Gets poll results filtered by age range.
     * GET /api/v1/analysis/polls/{pollId}/age-range?ageRange=B_18_25
     */
    @GetMapping("/polls/{pollId}/age-range")
    public ResponseEntity<PollResultResponse> getByAgeRange(@PathVariable UUID pollId, @RequestParam AgeRange ageRange) {
        log.info("API Request: Results for poll {} for age range {}", pollId, ageRange);
        return ResponseEntity.ok(analysisService.getResultsByAgeRange(pollId, ageRange));
    }

    /**
     * Returns a map of participation counts for each hour of the day (0-23).
     * Useful for visualizing peak engagement times.
     */
    @GetMapping("/polls/{pollId}/heatmap")
    public ResponseEntity<Map<Integer, Long>> getHeatmap(@PathVariable UUID pollId) {
        log.info("API Request: Hourly heatmap for poll {}", pollId);
        return ResponseEntity.ok(analysisService.getParticipationHeatmap(pollId));
    }
}
