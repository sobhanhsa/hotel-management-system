import java.time.LocalDate;


import enums.ReservationStatus;
import interfaces.Billable;

public class Reservation implements Billable {

    private String reservationId;
    private Guest guest;
    private Room room;


    private ReservationStatus status;


    @Override
    public double calculateTotal() {
        return 0; 
    }

    @Override
    public void applyDiscount(double percent) {
    }
}