package moj.project.domain;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.time.OffsetDateTime;
@Getter
@AllArgsConstructor
public class DateTimePeriod {
    private final OffsetDateTime startTimeSlot;
    private final OffsetDateTime endTimeSlot;
}
