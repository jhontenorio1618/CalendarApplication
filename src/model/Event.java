package model;

import lombok.*;

import java.time.Duration;
import java.time.LocalDate;
import java.time.LocalTime;

/*
Defines an event
 */
@Getter
public class Event {

    @NonNull
    private String title;
    @NonNull
    private LocalDate date;
    @NonNull
    private LocalTime start;
    @NonNull
    private LocalTime end;

    public Event(@NonNull String title, @NonNull LocalDate date, @NonNull LocalTime start, @NonNull LocalTime end) {
        if (end.isBefore(start)) {
            throw new IllegalArgumentException("End time must be after start time");
        }
        this.title = title;
        this.date = date;
        this.start = start;
        this.end = end;

    }

    //Check for conflict in overlapping events
    public boolean overlaps(@NonNull Event other) {
        return this.end.isAfter(other.getStart()) && this.start.isBefore(other.getEnd());
    }


    @Override
    public String toString() {
        return String.format(
                "Event:\n  Title: %s\n  Date: %s\n  Start: %s\n  End: %s",
                title, date, start, end
        );
    }

}
