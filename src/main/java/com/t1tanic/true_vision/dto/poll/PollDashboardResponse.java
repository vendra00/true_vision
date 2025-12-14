package com.t1tanic.true_vision.dto.poll;

import com.t1tanic.true_vision.enums.AgeRange;
import com.t1tanic.true_vision.enums.CityDistrict;
import java.util.Map;
import java.util.UUID;

public record PollDashboardResponse(
        UUID pollId,
        String pollTitle,
        long totalGlobalVotes,
        Map<CityDistrict, Long> votesByDistrict,
        Map<AgeRange, Long> votesByAgeGroup
) {}
