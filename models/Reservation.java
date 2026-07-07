import java.time.LocalDate;
import java.util.List;

import enums.ReservationStatus;
import interfaces.Billable;
import interfaces.Searchable;

public class Reservation implements
        Billable,
        Searchable<Reservation>     {

    protected static int nextResevationId = 1;
    private int reservationId;
    private Guest guest;
    private Room room;
    private List<LocalDate> dates;
    private ReservationStatus status;

    public Reservation(
        Guest guest,
        Room room,
        List<LocalDate> dates
    ) {

        this.reservationId = nextResevationId++;
        this.guest = guest;
        this.room = room;
        this.dates = dates;
        this.status = ReservationStatus.PENDING;
    }

    public int getReservationId() {
        return reservationId;
    }

    public Guest getGuest() {
        return guest;
    }


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