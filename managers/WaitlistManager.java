package managers;

import java.time.LocalDate;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.Map;
import java.util.Queue;

import interfaces.RoomObserver;
import models.Guest;

class WaitlistEntry {

    private final Guest guest;
    private final LocalDate checkIn;
    private final LocalDate checkOut;

    public WaitlistEntry(
            Guest guest,
            LocalDate checkIn,
            LocalDate checkOut
    ) {

        this.guest = guest;
        this.checkIn = checkIn;
        this.checkOut = checkOut;
    }

    // getters
    public Guest getGuest() {
        return guest;
    }

    public LocalDate getCheckInDate() {
        return checkIn;
    }

    public LocalDate getCheckOutDate() {
        return checkOut;
    }
}

public class WaitlistManager implements RoomObserver {

    private final Map<String, Queue<WaitlistEntry>> waitlists = new HashMap<>();

    public void addGuest(
            String roomNumber,
            Guest guest,
            LocalDate checkIn,
            LocalDate checkOut
    ) {

        waitlists
                .computeIfAbsent(roomNumber, k -> new LinkedList<>())
                .offer(new WaitlistEntry(
                        guest,
                        checkIn,
                        checkOut));
    }

    public void removeGuest(String roomNumber, Guest guest) {

        Queue<WaitlistEntry> queue = waitlists.get(roomNumber);

        if (queue == null)
            return;

        queue.removeIf(e -> e.getGuest().equals(guest));
    }

    @Override
    public void onRoomAvailable(String roomNumber) {

        Queue<WaitlistEntry> queue = waitlists.get(roomNumber);

        if (queue == null || queue.isEmpty())
            return;

        WaitlistEntry entry = queue.poll();

        System.out.println(
                "Notification sent to "
                        + entry.getGuest().getUsername()
                        + ": Room "
                        + roomNumber
                        + " is now available.");
    }
}