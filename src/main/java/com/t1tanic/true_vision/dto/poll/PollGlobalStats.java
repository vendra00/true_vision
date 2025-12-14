package com.t1tanic.true_vision.dto.poll;

import com.t1tanic.true_vision.enums.AgeRange;
import com.t1tanic.true_vision.enums.CityDistrict;
import java.util.UUID;

public record PollGlobalStats(
        UUID pollId,
        String pollTitle,
        String winningOption,
        CityDistrict mostActiveDistrict,
        AgeRange mostActiveAgeGroup,
        int peakParticipationHour,
        long totalVotes
) {}
