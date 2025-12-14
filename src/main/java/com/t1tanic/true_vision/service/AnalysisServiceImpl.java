package com.t1tanic.true_vision.service;

import com.t1tanic.true_vision.dto.poll.PollDashboardResponse;
import com.t1tanic.true_vision.dto.poll.PollResultResponse;
import com.t1tanic.true_vision.enums.AgeRange;
import com.t1tanic.true_vision.enums.CityDistrict;
import com.t1tanic.true_vision.model.poll.Poll;
import com.t1tanic.true_vision.repository.PollRepository;
import com.t1tanic.true_vision.repository.PollVoteRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Map;
import java.util.UUID;
import java.util.stream.Collectors;

@Slf4j
@Service
@RequiredArgsConstructor
public class AnalysisServiceImpl implements AnalysisService {

    private final PollRepository pollRepository;
    private final PollVoteRepository voteRepository;

    @Override
    @Transactional(readOnly = true)
    public PollResultResponse getResultsByDistrict(UUID pollId, CityDistrict district) {
        log.info("Generating demographic report for District: {}", district);

        Poll poll = pollRepository.findById(pollId)
                .orElseThrow(() -> new IllegalStateException("Poll not found."));

        List<PollResultResponse.OptionCount> optionCounts = poll.getOptions().stream()
                .map(option -> new PollResultResponse.OptionCount(
                        option.getId(),
                        option.getOptionText(),
                        voteRepository.countByOptionAndDistrict(option.getId(), district)
                ))
                .toList();

        return new PollResultResponse(
                poll.getId(),
                poll.getTitle() + " - " + district,
                optionCounts,
                optionCounts.stream().mapToLong(PollResultResponse.OptionCount::count).sum()
        );
    }

    @Override
    @Transactional(readOnly = true)
    public PollResultResponse getResultsByAgeRange(UUID pollId, AgeRange ageRange) {
        log.info("Analysis - Generating demographic report for Age Range: {}", ageRange);

        Poll poll = pollRepository.findById(pollId)
                .orElseThrow(() -> new IllegalStateException("Poll not found."));

        // Map each option to its count, filtered by age range
        List<PollResultResponse.OptionCount> optionCounts = poll.getOptions().stream()
                .map(option -> new PollResultResponse.OptionCount(
                        option.getId(),
                        option.getOptionText(),
                        voteRepository.countByOptionAndAgeRange(option.getId(), ageRange)
                ))
                .toList();

        return new PollResultResponse(
                poll.getId(),
                poll.getTitle() + " (Age Analysis: " + ageRange + ")",
                optionCounts,
                optionCounts.stream().mapToLong(PollResultResponse.OptionCount::count).sum()
        );
    }

    @Override
    @Transactional(readOnly = true)
    public PollResultResponse getGlobalResults(UUID pollId) {
        log.info("Analysis - Generating global summary for Poll: {}", pollId);

        Poll poll = pollRepository.findById(pollId)
                .orElseThrow(() -> new IllegalStateException("Poll not found."));

        // Standard total tally across all demographics
        List<PollResultResponse.OptionCount> optionCounts = poll.getOptions().stream()
                .map(option -> new PollResultResponse.OptionCount(
                        option.getId(),
                        option.getOptionText(),
                        voteRepository.countByChosenOptionId(option.getId())
                ))
                .toList();

        return new PollResultResponse(
                poll.getId(),
                poll.getTitle() + " (Global Summary)",
                optionCounts,
                optionCounts.stream().mapToLong(PollResultResponse.OptionCount::count).sum()
        );
    }

    @Override
    @Transactional(readOnly = true)
    public PollDashboardResponse getPollDashboard(UUID pollId) {
        Poll poll = pollRepository.findById(pollId)
                .orElseThrow(() -> new IllegalStateException("Poll not found."));

        // Convert List<Object[]> from repository into clean Maps
        Map<CityDistrict, Long> districtMap = voteRepository.countTotalVotesByDistrict(pollId)
                .stream().collect(Collectors.toMap(obj -> (CityDistrict)obj[0], obj -> (Long)obj[1]));

        Map<AgeRange, Long> ageMap = voteRepository.countTotalVotesByAgeRange(pollId)
                .stream().collect(Collectors.toMap(obj -> (AgeRange)obj[0], obj -> (Long)obj[1]));

        return new PollDashboardResponse(
                poll.getId(),
                poll.getTitle(),
                voteRepository.countByPollId(pollId), // You'll need this simple count method too
                districtMap,
                ageMap
        );
    }

}
