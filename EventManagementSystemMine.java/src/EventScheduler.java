import java.time.format.DateTimeFormatter;
import java.util.*;
import java.time.*;

public class EventScheduler {
    private static final LocalTime START_TIME = LocalTime.of(9, 0);
    private static final LocalTime LUNCH_START = LocalTime.of(12, 0);
    private static final LocalTime LUNCH_END = LocalTime.of(13, 0);
    private static final LocalTime NETWORKING_EARLIEST   = LocalTime.of(16, 0);
    private static final LocalTime NETWORKING_LATEST = LocalTime.of(17, 0);
    final DateTimeFormatter TIME_FORMATTER = DateTimeFormatter.ofPattern("hh:mm: a");


    // Lists to store events on each day
    private List<Event> day1Events;
    private List<Event> day2Events;

    public EventScheduler() {
        day1Events = new ArrayList<>();
        day2Events = new ArrayList<>();
    }

    public void scheduleEvents(List<Event> events) {
        LocalTime currentTime = START_TIME;
        boolean isDay1 = true;
        List<Event> remainingEvents = new ArrayList<>(events);

        Event networkingEvent = new Event("Networking Hands On", 60);
        while (!remainingEvents.isEmpty()) {
            LocalTime dayEndTime = isDay1 ? NETWORKING_EARLIEST: NETWORKING_LATEST;
            List<Event> currentDayEvents = isDay1? day1Events: day2Events;
            currentTime = START_TIME;

            while (currentTime.isBefore(dayEndTime) && !remainingEvents.isEmpty()) {
                if (currentTime.equals(LUNCH_START)) {
                    currentTime = LUNCH_END;
                    continue;
                }

                Event nextEvent = findNextSuitableEvent(remainingEvents, currentTime, dayEndTime);
                if (nextEvent == null) break;

                nextEvent.setScheduledTime(currentTime.format(TIME_FORMATTER));
                currentDayEvents.add(nextEvent);
                remainingEvents.remove(nextEvent);

                currentTime = currentTime.plusMinutes(nextEvent.getDuration()); //Move current time forward by event duration

            }

            if (isDay1) {
                networkingEvent.setScheduledTime(currentTime.format(TIME_FORMATTER));
                day1Events.add(networkingEvent);
            }
            else {
                networkingEvent.setScheduledTime("4:00 PM");
                day2Events.add(networkingEvent);
            }

            isDay1 = false;
        }
    }

    private Event findNextSuitableEvent(List<Event> events, LocalTime currentTime, LocalTime endTime) {
        for (Event event: events) {
            LocalTime eventEndTime = currentTime.plusMinutes(event.getDuration());
            if (eventEndTime.isBefore(LUNCH_START) || (eventEndTime.isAfter(LUNCH_END) && eventEndTime.isBefore(endTime))) {
                return  event;
            }
        }

        return  null;
    }

    public void printSchedule() {
        System.out.println("Schedule for Day 1");
        for (Event event: day1Events) {
            System.out.println(event);
        }

        System.out.println("\nSchedule for Day 1");
        for (Event event: day2Events) {
            System.out.println(event);
        }


    }


}
