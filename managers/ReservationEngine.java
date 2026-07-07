package managers;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import enums.ReservationStatus;
import enums.RoomStatus;
import enums.RoomType;
import exceptions.InvalidDateRangeException;
import exceptions.ReservationConflictException;
import exceptions.RoomNotAvailableException;
import interfaces.Notifiable;
import interfaces.RoomObserver;
import models.Guest;
import models.Reservation;
import models.Room;

public class ReservationEngine implements Notifiable {

    private final ArrayList<Reservation> reservations;
    private final ArrayList<RoomObserver> observers;

    public ReservationEngine() {
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

    private boolean isRoomAvailable(Room room, ArrayList<LocalDate> dates) {

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

    public void checkOut(...);

    public double cancelReservation(...);


    private double calculatePenalty(...);

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