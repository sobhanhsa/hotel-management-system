package managers;

import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import enums.LogLevel;
import enums.ReservationStatus;
import enums.RoomStatus;
import enums.RoomType;
import exceptions.InvalidDateRangeException;
import exceptions.ReservationConflictException;
import exceptions.ReservationNotFoundException;
import exceptions.RoomNotAvailableException;
import interfaces.Notifiable;
import interfaces.RoomObserver;
import models.Guest;
import models.Invoice;
import models.Reservation;
import models.Room;
import exceptions.InvalidReservationStatusException;
import exceptions.UnpaidInvoiceException;

public class ReservationEngine implements Notifiable {

    private final ArrayList<Reservation> reservations;
    private final ArrayList<RoomObserver> observers;
    private final LogManager logManager;

    public ReservationEngine(LogManager logManager) {
        this.logManager = logManager;
        reservations = new ArrayList<>();
        observers = new ArrayList<>();
    }
    
    // helper function
    public Reservation findReservationById(String reservationId) {
        for (Reservation reservation : reservations) {
            if (reservation.getReservationId().equals(reservationId)) {
                return reservation;
            }
        }
        return null;
    }

    public List<Reservation> getReservations() {
        return new ArrayList<>(reservations);
    }

    private boolean hasConflict(Room room, ArrayList<LocalDate> dates) {
        // check for the same room in different reservation overlapping in date!
        for (Reservation reservation : reservations) {

            if (!reservation.getRoom().equals(room))
                continue;

            if (reservation.getStatus() == ReservationStatus.CANCELLED
                    || reservation.getStatus() == ReservationStatus.COMPLETED)
                continue;

            for (LocalDate date : dates) {
                if (reservation.getDates().contains(date)) {
                    return true;
                }
            }
        }

        return false;
    }

    public boolean isRoomAvailable(Room room, ArrayList<LocalDate> dates) {

        if (room.getStatus() != RoomStatus.AVAILABLE)
            return false;

        return !hasConflict(room, dates);
    }

    public ArrayList<Room> searchAvailableRooms(
        ArrayList<Room> rooms,
        ArrayList<LocalDate> dates,
        RoomType type,
        int guestCount
    ) {

        ArrayList<Room> availableRooms = new ArrayList<>();

        for (Room room : rooms) {

            if (room.getType() != type)
                continue;

            if (room.getCapacity() < guestCount)
                continue;

            if (!isRoomAvailable(room, dates))
                continue;

            availableRooms.add(room);
        }

        return availableRooms;
    }


    public Reservation createReservation(
        Guest guest,
        Room room,
        ArrayList<LocalDate> dates,
        int guestCount
    )
        throws 
        InvalidDateRangeException,
        RoomNotAvailableException,
        ReservationConflictException 
        {


        if (dates == null || dates.size() < 2) {
            throw new InvalidDateRangeException(
                "Invalid reservation dates"
            );
        }


        if (room.getStatus() != RoomStatus.AVAILABLE) {
            throw new RoomNotAvailableException(
                "Room is not available"
            );
        }


        if (hasConflict(room, dates)) {
            throw new ReservationConflictException(
                "Room already reserved for these dates"
            );
        }


        Reservation reservation =
            new Reservation(
                    guest,
                    room,
                    dates
        );


        reservation.setStatus(
                ReservationStatus.PENDING
        );


        room.setStatus(
                RoomStatus.RESERVED
        );


        reservations.add(reservation);


        guest.addReservation(reservation);

        // init invoice
        Invoice invoice = new Invoice(reservation);
        reservation.setInvoice(invoice);

        logManager.addLog(
            LogLevel.INFO,
            guest.getUsername(),
            "CREATE_RESERVATION",
            "[Reservation: " + reservation.getReservationId() + "] "
            + "[Room: " + room.getRoomNumber() + "]"
        );

        return reservation;
    }

    public void confirmReservation(
        Reservation reservation
    ) {

        if (
            reservation.getStatus()
            != ReservationStatus.PENDING
        ) {

            return;
        }


        reservation.setStatus(
            ReservationStatus.CONFIRMED
        );
    }

    public void checkIn(
            Reservation reservation
    ) {


        if (reservation.getStatus()
                != ReservationStatus.CONFIRMED) {

            return;
        }


        reservation.setStatus(
                ReservationStatus.ACTIVE
        );


        reservation.getRoom()
            .setStatus(
                RoomStatus.OCCUPIED
            );
    }

    public void checkOut(Reservation reservation)
        throws InvalidReservationStatusException,
            UnpaidInvoiceException 
        {


        if (reservation.getStatus() != ReservationStatus.ACTIVE) {
            throw new InvalidReservationStatusException(
                    "Reservation is not active"
            );
        }


        Invoice invoice = reservation.getInvoice();


        if (invoice.isFullyPaid()) {
            throw new UnpaidInvoiceException(
                    "Guest still has unpaid balance"
            );
        }


        // reservation complete
        reservation.setStatus(
                ReservationStatus.COMPLETED
        );


        // free room
        Room room = reservation.getRoom();

        room.setStatus(
                RoomStatus.AVAILABLE
        );


        // update guest history
        Guest guest = reservation.getGuest();

        guest.increaseStay();

        guest.checkUpgradeMembershipLevel();


        // notify waitlist
        notifyObservers(
                room.getRoomNumber()
        );
    }

    public double cancelReservation(String reservationId) {

        Reservation reservation = findReservationById(reservationId);

        if (reservation == null) {
            throw new ReservationNotFoundException("room not found");
        }

        if (reservation.getStatus() == ReservationStatus.CANCELLED ||
            reservation.getStatus() == ReservationStatus.COMPLETED) {
            return 0;
        }


        double penalty = calculatePenalty(reservation);

        double refund = reservation.getInvoice().getPaidAmount() - penalty;


        reservation.setStatus(ReservationStatus.CANCELLED);


        Room room = reservation.getRoom();
        room.setStatus(RoomStatus.AVAILABLE);


        notifyObservers(room.getRoomNumber());

        logManager.addLog(
            LogLevel.INFO,
            reservation.getGuest().getUsername(),
            "CANCEL_RESERVATION",
            "[Reservation: " + reservation.getReservationId() + "] "
            + "[Penalty: " + penalty + "]"
        );

        return Math.max(refund, 0);
    }

    private double calculatePenalty(Reservation reservation) {

        // calcuate days
        long daysUntilCheckIn = ChronoUnit.DAYS.between(
                LocalDate.now(),
                reservation.getStartDate()
        );

        double totalPaymentsAmount = reservation.getInvoice().getPaidAmount();

        if (daysUntilCheckIn < 1) {
            return totalPaymentsAmount;
        }

        if (daysUntilCheckIn <= 3) {
            return totalPaymentsAmount * 0.5;
        }

        return 0;
    }

    @Override
    public void registerObserver(RoomObserver observer) {
        if (!observers.contains(observer)) {
            observers.add(observer);
        }
    }

    @Override
    public void removeObserver(RoomObserver observer) {
        observers.remove(observer);
    }

    @Override
    public void notifyObservers(String roomNumber) {
        for (RoomObserver observer : observers) {
            observer.onRoomAvailable(roomNumber);
        }
    }
}