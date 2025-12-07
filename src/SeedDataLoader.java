import service.CalendarService;

import java.time.LocalDate;
import java.time.LocalTime;

public class SeedDataLoader {

    public static void load(CalendarService service) {
        LocalDate today = LocalDate.now();

        service.createEvent(
                "Team Standup",
                today,
                LocalTime.of(9, 0),
                LocalTime.of(9, 30)
        );

        service.createEvent(
                "Project Work Session",
                today,
                LocalTime.of(10, 0),
                LocalTime.of(11, 0)
        );

        service.createEvent(
                "Lunch Break",
                today,
                LocalTime.of(12, 0),
                LocalTime.of(13, 0)
        );

        service.createEvent(
                "Client Call",
                today,
                LocalTime.of(15, 0),
                LocalTime.of(16, 0)
        );
    }
}
