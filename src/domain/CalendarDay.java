package domain;
import lombok.Getter;
import model.Event;

import javax.swing.border.BevelBorder;
import java.time.Duration;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.Iterator;
import java.util.List;

// Executes day-level operations
public class CalendarDay {

    private final LocalDate date;

    @Getter
    private final List<Event> events;

    public CalendarDay(LocalDate date) {
        this.date = date;
        this.events = new ArrayList<>();
    }

    public boolean addEvent(Event event) {

        for (Event existing : events) {
            if (existing.overlaps(event)) {
                return false;
            }
        }
        events.add(event);
        //keeps events sorted by start time.
        events.sort(Comparator.comparing(Event::getStart));
        return true;
    }


    public List<Event> getRemainingEvents(LocalTime currentTime) {
        List<Event> remaining = new ArrayList<>();
        for (Event event : events) {
            if (event.getStart().isAfter(currentTime)) {
                remaining.add(event);
            }
        }
        return remaining;
    }

    public LocalTime findNextAvailableSlot(Duration duration) {

        if (events.isEmpty()) {
            return LocalTime.MIN;
        }

        LocalTime current = LocalTime.MIN;

        for (Event event : events) {
            LocalTime candidateEnd = current.plus(duration);

            // If the candidate EVENT fits before the next event
            if (!candidateEnd.isAfter(event.getStart())) {
                return current;
            }

            current = event.getEnd();
        }

        // Check the gap between last event and end of day
        LocalTime candidateEnd = current.plus(duration);
        if (!candidateEnd.isAfter(LocalTime.MAX)) {
            return current;
        }

        return null;
    }




}
