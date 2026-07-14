package view;

import enums.ReservationStatus;
import enums.RoomType;
import exceptions.HotelException;
import managers.ReservationEngine;
import managers.RoomManager;
import models.Guest;
import models.Reservation;
import models.Room;
import models.User;

import java.time.LocalDate;
import java.util.List;

public class GuestView {

    private final User currentUser;
    private final RoomManager roomManager;
    private final ReservationEngine reservationEngine;

    public GuestView(
            User currentUser,
            RoomManager roomManager,
            ReservationEngine reservationEngine
    ) {
        this.currentUser = currentUser;
        this.roomManager = roomManager;
        this.reservationEngine = reservationEngine;
    }

    public void start() {
        boolean running = true;

        while (running) {
            printMenu();

            int choice = Console.readInt("Select option: ");

            try {
                switch (choice) {
                    case 1:
                        searchAvailableRooms();
                        break;
                    case 2:
                        showMyReservations();
                        break;
                    case 3:
                        cancelReservation();
                        break;
                    case 4:
                        showStayHistory();
                        break;
                    case 5:
                        showMembershipInformation();
                        break;
                    case 0:
                        running = false;
                        break;
                    default:
                        Console.error("Invalid option.");
                }
            } catch (HotelException | IllegalArgumentException e) {
                Console.error(e.getMessage());
            }

            if (running) {
                Console.pause();
            }
        }
    }

    private void printMenu() {
        Console.title(
                "HOTEL GRAND PERSIA\n"
                + "Logged in as: GUEST | " + currentUser.getUsername()
        );

        Console.println("1. Search Available Rooms");
        Console.println("2. My Reservations");
        Console.println("3. Cancel Reservation");
        Console.println("4. Stay History");
        Console.println("5. Membership Information");
        Console.println("0. Logout");
        Console.line();
    }

    private void searchAvailableRooms() {
        Console.title("Search Available Rooms");

        RoomType roomType = readRoomType();
        LocalDate checkIn = LocalDate.parse(
                Console.readLine("Check-in date (YYYY-MM-DD): ")
        );
        LocalDate checkOut = LocalDate.parse(
                Console.readLine("Check-out date (YYYY-MM-DD): ")
        );

        if (!checkOut.isAfter(checkIn)) {
            Console.error("Check-out date must be after check-in date.");
            return;
        }

        List<Room> rooms = roomManager.searchAvailableRooms(
                roomType,
                checkIn,
                checkOut
        );

        if (rooms.isEmpty()) {
            Console.warning("No available rooms found.");
            return;
        }

        Console.println("Available rooms:");
        for (Room room : rooms) {
            Console.println(
                    "Room " + room.getRoomNumber()
                            + " | Type: " + room.getType()
                            + " | Capacity: " + room.getCapacity()
                            + " | Status: " + room.getStatus()
            );
        }
    }

    private RoomType readRoomType() {
        String input = Console.readLine(
                "Room type (STANDARD, DELUXE, SUITE, PENTHOUSE): "
        );

        try {
            return RoomType.valueOf(input.toUpperCase());
        } catch (IllegalArgumentException e) {
            throw new IllegalArgumentException("Invalid room type.");
        }
    }

    private void showMyReservations() {
        Console.title("My Reservations");

        List<Reservation> reservations = guest().getReservationHistory().stream()
                .filter(reservation -> reservation.getStatus() != ReservationStatus.COMPLETED)
                .filter(reservation -> reservation.getStatus() != ReservationStatus.CANCELLED)
                .toList();

        if (reservations.isEmpty()) {
            Console.warning("You have no current reservations.");
            return;
        }

        printReservations(reservations);
    }

    private void cancelReservation() {
        Console.title("Cancel Reservation");

        String reservationId = Console.readLine("Reservation ID: ");
        Reservation reservation = reservationEngine.findReservationById(reservationId);

        if (reservation.getGuest() != guest()) {
            Console.error("You can only cancel your own reservations.");
            return;
        }

        if (reservation.getStatus() == ReservationStatus.CANCELLED
                || reservation.getStatus() == ReservationStatus.COMPLETED) {
            Console.warning("This reservation cannot be cancelled.");
            return;
        }

        double penalty = reservationEngine.calculatePenalty(reservation);
        Console.println("Cancellation penalty: " + penalty);

        if (!Console.confirm("Cancel reservation " + reservationId + "?")) {
            Console.println("Cancellation cancelled. Enjoy your stay !");
            return;
        }

        reservationEngine.cancelReservation(reservationId);
        Console.success("Reservation cancelled.");
    }

    private void showStayHistory() {
        Console.title("Stay History");

        List<Reservation> completedStays = guest().getReservationHistory().stream()
                .filter(reservation -> reservation.getStatus() == ReservationStatus.COMPLETED)
                .toList();

        if (completedStays.isEmpty()) {
            Console.warning("No completed stays found.");
            return;
        }

        printReservations(completedStays);
    }

    private void showMembershipInformation() {
        Console.title("Membership Information");

        Guest guest = guest();
        Console.println("Membership level: " + guest.getMembershipLevel());
        Console.println("Completed stays: " + guest.getTotalStays());
        Console.println("Discount rate: "
                + (guest.getMembershipLevel().getDiscountRate() * 100) + "%");
    }

    private void printReservations(List<Reservation> reservations) {
        for (Reservation reservation : reservations) {
            Console.println(
                    "Reservation " + reservation.getReservationId()
                            + " | Room: " + reservation.getRoom().getRoomNumber()
                            + " | Status: " + reservation.getStatus()
                            + " | Dates: " + reservation.getDates()
            );
        }
    }

    private Guest guest() {
        if (!(currentUser instanceof Guest)) {
            throw new IllegalStateException("GuestView requires a guest user.");
        }

        return (Guest) currentUser;
    }
}
