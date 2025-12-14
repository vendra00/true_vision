package com.t1tanic.true_vision.model.poll;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.UUID;

@Getter
@Setter
@NoArgsConstructor
@Entity
@Table(name = "poll_options")
public class PollOption {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    @Column(name = "option_text", nullable = false)
    private String optionText;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "poll_id", nullable = false)
    private Poll poll;

    public PollOption(String optionText, Poll poll) {
        this.optionText = optionText;
        this.poll = poll;
    }
}