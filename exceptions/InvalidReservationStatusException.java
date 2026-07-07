package exceptions;

public class InvalidReservationStatusException extends HotelException {
    public InvalidReservationStatusException(String message) {
        super(message);
    }
}