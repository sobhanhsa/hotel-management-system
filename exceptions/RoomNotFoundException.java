package exceptions;

public class RoomNotFoundException extends HotelException {
    public RoomNotFoundException(String message) {
        super(message);
    }
}