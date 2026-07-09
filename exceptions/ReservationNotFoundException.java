package exceptions;

public class ReservationNotFoundException extends HotelException {
    public ReservationNotFoundException(String message) {
        super(message);
    }
}