import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import enums.ReservationStatus;
import interfaces.Billable;
import interfaces.Notifiable;
import interfaces.RoomObserver;

public class Reservation implements Billable {

    private String reservationId;
    private Guest guest;
    private Room room;

    private LocalDate checkInDate;
    private LocalDate checkOutDate;

    private ReservationStatus status;

    private int numberOfGuests;

    private List<Service> services = new ArrayList<>();
    private List<Payment> payments = new ArrayList<>();

    private List<RoomObserver> observers = new ArrayList<>();

    @Override
    public double calculateTotal() {
        return 0; 
    }

    @Override
    public void applyDiscount(double percent) {
    }

    public void confirm() { }

    public void checkIn() { }

    public void checkOut() { }

    public void cancel() { }

    
}