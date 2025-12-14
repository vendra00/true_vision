package com.t1tanic.true_vision.model.poll;

import com.t1tanic.true_vision.model.app_user.AppUser;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.Instant;
import java.util.UUID;

@Getter
@Setter
@NoArgsConstructor
@Entity
@Table(name = "poll_votes", uniqueConstraints = {@UniqueConstraint(columnNames = {"poll_id", "app_user_id"})})
public class PollVote {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    @Column(name = "vote_timestamp", nullable = false)
    private Instant voteTimestamp = Instant.now();

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "app_user_id", nullable = false, unique = true)
    private AppUser appUser;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "poll_id", nullable = false)
    private Poll poll;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "poll_option_id", nullable = false)
    private PollOption chosenOption;

    public PollVote(AppUser appUser, Poll poll, PollOption chosenOption) {
        this.appUser = appUser;
        this.poll = poll;
        this.chosenOption = chosenOption;
        this.voteTimestamp = Instant.now();
    }
}
