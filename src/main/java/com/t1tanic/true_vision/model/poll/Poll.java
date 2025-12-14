package com.t1tanic.true_vision.model.poll;

import com.t1tanic.true_vision.enums.PollStatus;
import com.t1tanic.true_vision.model.BaseEntity;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.Instant;
import java.util.List;
import java.util.UUID;

@Getter
@Setter
@NoArgsConstructor
@Entity
@Table(name = "polls")
public class Poll extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    @Column(name = "title", nullable = false)
    private String title;

    @Column(name = "question", nullable = false, columnDefinition = "TEXT")
    private String question;

    @Enumerated(EnumType.STRING)
    @Column(name = "status", nullable = false)
    private PollStatus status;

    @Column(name = "start_date")
    private Instant startDate;

    @Column(name = "end_date")
    private Instant endDate;

    @Column(name = "is_active", nullable = false)
    private boolean isActive = false;

    @OneToMany(mappedBy = "poll", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<PollOption> options;

    @OneToMany(mappedBy = "poll", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<PollVote> votes;

    public Poll(String title, String question, Instant startDate, Instant endDate) {
        this.title = title;
        this.question = question;
        this.startDate = startDate;
        this.endDate = endDate;
        this.status = PollStatus.DRAFT;
    }
}