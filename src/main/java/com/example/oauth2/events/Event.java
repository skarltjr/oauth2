package com.example.oauth2.events;

import com.example.oauth2.account.Account;
import lombok.*;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity @Builder
@Getter @Setter
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(of = "id")
public class Event {
    @Id
    @GeneratedValue
    private Integer id;
    private String name;
    private String description;
    private LocalDateTime beginEnrollmentDateTime;
    private LocalDateTime closeEnrollmentDateTime;
    private LocalDateTime beginEventDateTime;
    private LocalDateTime endEventDateTime;
    private String location;
    private int basePrice;
    private int maxPrice;
    private int limitOfEnrollment;
    private boolean offline;
    private boolean free;

    @Enumerated(EnumType.STRING)
    private EventStatus eventStatus = EventStatus.DRAFT;

    @ManyToOne(fetch = FetchType.LAZY)
    private Account manager;

    public void update() {
        if (basePrice == 0 && maxPrice == 0) {
            this.free = true;
        }
        if (location == null || location.isBlank()) {
            offline = false;
        } else {
            offline = true;
        }
    }
}
