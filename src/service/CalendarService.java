package service;
import lombok.NonNull;
import domain.CalendarDay;
import model.Event;
import java.time.Duration;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/*
Coordinates multiples days.
 */
public class CalendarService {

    private final Map<LocalDate, CalendarDay> days;

    public CalendarService() {
        this.days = new HashMap<>();
    }

    public String createEvent(
            @NonNull String title,
            @NonNull LocalDate date,
            @NonNull LocalTime start,
            @NonNull LocalTime end
    ) {
        CalendarDay day = days.computeIfAbsent(date, CalendarDay::new);
        Event event = new Event(title, date, start, end);

        return day.addEvent(event)
                ? "Event created."
                : "Sorry, you can't create a new event. Try another time or date.";
    }


    public List<Event> listAllEventsToday() {
        LocalDate today = LocalDate.now();
        CalendarDay day = days.get(today);
        return (day == null) ? new ArrayList<>() : day.getEvents();
    }


    public List<Event> listEventsByDate(@NonNull LocalDate date) {
        CalendarDay day = days.get(date);
        return (day == null) ? new ArrayList<>() : day.getEvents();
    }

    public List<Event> listRemainingEvents(@NonNull LocalTime currentTime) {
        CalendarDay day = days.get(LocalDate.now());
        return (day == null) ? new ArrayList<>() : day.getRemainingEvents(currentTime);
    }

    public LocalTime findNextAvailableSlot(@NonNull LocalDate date, @NonNull Duration duration) {
        CalendarDay day = days.get(date);
        if (day == null) {
            return LocalTime.MIN;
        }
        return day.findNextAvailableSlot(duration);
    }
}
