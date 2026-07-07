package models;

import java.time.LocalDate;
import java.util.List;

import enums.ReservationStatus;
import enums.Season;
import interfaces.Billable;
import interfaces.Searchable;

public class Reservation implements
        Billable,
        Searchable<Reservation>     
{

    protected static int nextResevationId = 1;
    private String reservationId;
    private Guest guest;
    private Room room;
    private List<LocalDate> dates;
    private ReservationStatus status;
    private int guestCount;

    public Reservation(
        Guest guest,
        Room room,
        List<LocalDate> dates
    ) {

        this.reservationId = Integer.toString(nextResevationId++);
        this.guest = guest;
        this.room = room;
        this.dates = dates;
        this.status = ReservationStatus.PENDING;
        this.guestCount = 1;
    }

    public Reservation(
        Guest guest,
        Room room,
        List<LocalDate> dates,
        int guestCount
    ) {

        this.reservationId = Integer.toString(nextResevationId++);
        this.guest = guest;
        this.room = room;
        this.dates = dates;
        this.status = ReservationStatus.PENDING;
        this.guestCount = guestCount;
    }

    public void setStatus(ReservationStatus status) {
        this.status = status;
    }

    public String getReservationId() {
        return reservationId;
    }

    public Guest getGuest() {
        return guest;
    }

    public int getNights() {
        return dates.size();
    }

    public Room getRoom() {
        return this.room;
    }

    public ReservationStatus getStatus() {
        return status;
    }

    public List<LocalDate> getDates() {
        return dates;
    }

    public int getGuestCount() {
        return this.guestCount;
    }

    public Season getSeason() {
        LocalDate firstDate = dates.get(0);
        int month = firstDate.getMonthValue();   
    
        if (month >= 6 && month <= 8) {
        return Season.SUMMER;      
        } 
        else if (month >= 3 && month <= 5) {
            return Season.SPRING;    
        } 
        else if(month >= 9 && month <= 11) {
            return Season.FALL;
        }
        else {
            return Season.WINTER;     
        }
    }

    // AccommodationCost total price
    @Override
    public double calculateTotal() {
        return this.room.calculatePrice(getNights(), guestCount, getSeason().getRate());
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