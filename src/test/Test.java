package test;

import model.Event;
import service.CalendarService;

import java.time.LocalDate;
import java.time.LocalTime;
import java.time.Duration;
import java.util.List;
import java.util.Objects;

public class Test {

    private static int passed = 0;
    private static int failed = 0;

    private static void expectEquals(Object expected, Object actual, String testName) {
        if (Objects.equals(expected, actual)) {
            System.out.println(testName + ": [PASSED]");
            passed++;
        } else {
            System.out.println("[FAILED] " + testName + " | Expected: " + expected + " Actual: " + actual);
            failed++;
        }
    }

    public static void main(String[] args) {
        LocalDate date =  LocalDate.now();

        CalendarService service = new CalendarService();

        //Test: Create event
        String r1 = service.createEvent("A", date, LocalTime.of(9, 0), LocalTime.of(10, 0));
        expectEquals("Event created.", r1, "Create Event 1");

        String r2 = service.createEvent("B", date, LocalTime.of(10, 30), LocalTime.of(11, 0));
        expectEquals("Event created.", r2, "Create Event 2"); //Non-overlapping event

        String r3 = service.createEvent("Overlap", date, LocalTime.of(9, 30), LocalTime.of(10, 15));
        // Test: Overlapping event should fail
        expectEquals(
                "Sorry, you can't create a new event. Try another time or date.",
                r3,
                "Overlap Detection"
        );


        List<Event> events = service.listEventsByDate(date);
        expectEquals(2, events.size(), "List Events Count");

        // Test: Remaining events after a certain time
        List<Event> remaining = service.listRemainingEvents(LocalTime.of(9, 45));
        expectEquals(1, remaining.size(), "Remaining Events");

        // Test: Find next available slot Test
        LocalTime slot = service.findNextAvailableSlot(date, Duration.ofMinutes(45));
        expectEquals(LocalTime.of(0, 0), slot, "Next Available Slot");

        System.out.println("\n=========================");
        System.out.println("      TEST RESULTS");
        System.out.println("=========================");
        System.out.println("PASSED: " + passed);
        System.out.println("FAILED: " + failed);
        System.out.println("=========================\n");
    }

}
