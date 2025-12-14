package com.t1tanic.true_vision.service;


import com.t1tanic.true_vision.dto.poll.PollDashboardResponse;
import com.t1tanic.true_vision.dto.poll.PollResultResponse;
import com.t1tanic.true_vision.enums.AgeRange;
import com.t1tanic.true_vision.enums.CityDistrict;

import java.util.UUID;

/**
 * Service dedicated to data intelligence and demographic analysis.
 * <p>
 * This service provides non-aggregated and filtered views of voting data
 * to generate high-integrity reports for governmental decision-making.
 * It ensures that while identities are validated, results remain statistical.
 */
public interface AnalysisService {

    /**
     * Calculates participation and results per Barcelona City District.
     * @param pollId The unique identifier of the poll.
     * @param district The target district (e.g., EIXAMPLE, GRACIA).
     * @return Detailed results filtered by geographic location.
     */
    PollResultResponse getResultsByDistrict(UUID pollId, CityDistrict district);

    /**
     * Calculates participation and results per defined age ranges.
     * @param pollId The unique identifier of the poll.
     * @param ageRange The target age range (e.g., AGE_18_25, AGE_26_40).
     * @return Detailed results filtered by age demographics.
     */
    PollResultResponse getResultsByAgeRange(UUID pollId, AgeRange ageRange);

    /**
     * Provides overall results for a poll without demographic filters.
     * @param pollId The unique identifier of the poll.
     * @return Comprehensive results across all demographics.
     */
    PollResultResponse getGlobalResults(UUID pollId);

    /**
     * Provides a comprehensive view of all participation metrics.
     * Maps global totals, district-based engagement, and age-group distribution.
     * @param pollId The unique identifier of the poll.
     * @return A complete dashboard object for data visualization.
     */
    PollDashboardResponse getPollDashboard(UUID pollId);
}
