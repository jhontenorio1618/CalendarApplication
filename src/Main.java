import model.Event;
import service.CalendarService;

import java.time.Duration;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;
import java.util.Scanner;

public class Main {

    private static final CalendarService service = new CalendarService();
    private static final Scanner scanner = new Scanner(System.in);

    public static void main(String[] args) {

        boolean running = true;
        System.out.println("CALENDAR APPLICATION");
        SeedDataLoader.load(service);

        while (running) {
            printMenu();

            int choice;
            try {
                choice = Integer.parseInt(scanner.nextLine());
            } catch (Exception e) {
                System.out.println("Invalid input. Please enter a number.");
                continue;
            }

            switch (choice) {
                case 1 -> handleCreateEvent();
                case 2 -> handleListEventsByDate();
                case 3 -> handleListRemainingEventsToday();
                case 4 -> handleFindNextAvailableSlot();
                case 5 -> handleListAllEventsToday();
                case 6 -> {
                    running = false;
                    System.out.println("Goodbye!");
                }
                default -> System.out.println("Invalid choice.");
            }
        }
    }


    private static void printMenu() {
        System.out.println("\nChoose an option:");
        System.out.println("1. Create Event");
        System.out.println("2. List Events by Date");
        System.out.println("3. List Remaining Events Today");
        System.out.println("4. Find Next Available Slot");
        System.out.println("5. List All Events Today");
        System.out.println("6. To Exit");
        System.out.print("Your choice: ");
    }
    /*
    CLI Validation might be out of the scope of this assignment,
    but I'll be including it to prevent disruptions from invalid input .
     */
    private static void handleCreateEvent() {
        try {
            System.out.print("Enter title: ");
            String title = scanner.nextLine();

            System.out.print("Enter date (YYYY-MM-DD): ");
            LocalDate date = LocalDate.parse(scanner.nextLine());

            System.out.print("Enter start time (HH:MM): ");
            LocalTime start = LocalTime.parse(scanner.nextLine());

            System.out.print("Enter end time (HH:MM): ");
            LocalTime end = LocalTime.parse(scanner.nextLine());

            String result = service.createEvent(title, date, start, end);
            System.out.println(result);

        } catch (Exception e) {
            System.out.println("Invalid input. Please try again.");
        }
    }

    private static void handleListEventsByDate() {
        try {
            System.out.print("Enter date (YYYY-MM-DD): ");
            LocalDate date = LocalDate.parse(scanner.nextLine());

            List<Event> events = service.listEventsByDate(date);
            if (events == null || events.isEmpty()) {
                System.out.println("No events for this date.");
                return;
            }

            System.out.println("Events on " + date + ":");
            events.forEach(System.out::println);

        } catch (Exception e) {
            System.out.println("Invalid date format.");
        }
    }

    public static void handleListAllEventsToday() {
        List<Event> events = service.listAllEventsToday();

        if (events == null || events.isEmpty()) {
            System.out.println("No events today.");
            return;
        }

        System.out.println("Events today:");
        events.forEach(System.out::println);
    }


    private static void handleListRemainingEventsToday() {
        try {
            System.out.print("Enter current time (HH:MM): ");
            LocalTime now = LocalTime.parse(scanner.nextLine());

            List<Event> remaining = service.listRemainingEvents(now);
            System.out.println("Remaining events:");
            remaining.forEach(System.out::println);
        } catch (Exception e) {
            System.out.println("Invalid time format.");
        }
    }

    private static void handleFindNextAvailableSlot() {
        try {
            System.out.print("Enter date (YYYY-MM-DD): ");
            LocalDate date = LocalDate.parse(scanner.nextLine());

            System.out.print("Enter duration in minutes: ");
            Duration duration = Duration.ofMinutes(Long.parseLong(scanner.nextLine()));

            LocalTime slot = service.findNextAvailableSlot(date, duration);

            if (slot == null) {
                System.out.println("Sorry! No available times this day.");
                return;
            }
            System.out.println("Next available time: " + slot);

        } catch (Exception e) {
            System.out.println("Invalid input. Please try again.");
        }
    }

}
