import java.time.LocalDate;
import java.util.List;

import enums.ReservationStatus;
import interfaces.Billable;
import interfaces.Filterable;
import interfaces.Searchable;

public class Reservation implements
        Billable,
        Searchable<Reservation>,
        Filterable<Reservation>     {

    private String reservationId;
    private Guest guest;
    private Room room;
    private LocalDate checkInDate;
    private LocalDate checkOutDate;
    private ReservationStatus status;

    @Override
    public double calculateTotal() {
        return 0;
    }

    @Override
    public void applyDiscount(double percent) {
    }

    @Override
    public List<Reservation> search(String query) {
        return null;
    }

    @Override
    public List<Reservation> filter(java.util.function.Predicate<Reservation> predicate) {
        return null;
    }
}