package exceptions;

public class GuestNotFoundException extends HotelException {
    public GuestNotFoundException(String message) {
        super(message);
    }
}