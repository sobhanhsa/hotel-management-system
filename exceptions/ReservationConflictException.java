package exceptions;

public class ReservationConflictException extends HotelException {
    public ReservationConflictException(String message) {
        super(message);
    }
}