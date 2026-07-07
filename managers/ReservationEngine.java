package managers;

import java.util.ArrayList;

import interfaces.Notifiable;
import interfaces.RoomObserver;

public class ReservationEngine implements Notifiable {

    private final ArrayList<Reservation> reservations;
    private final ArrayList<RoomObserver> observers;

    public ReservationEngine() { }

    public ArrayList<Room> searchAvailableRooms(...);

    public Reservation createReservation(...);

    public void confirmReservation(...);

    public void checkIn(...);

    public void checkOut(...);

    public double cancelReservation(...);

    private boolean hasConflict(...);

    private double calculatePenalty(...);

    @Override
    public void registerObserver(RoomObserver observer) {

    }

    @Override
    public void removeObserver(RoomObserver observer){
        
    }

    @Override
    public void notifyObservers(String roomNumber){

    }
}