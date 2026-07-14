package exceptions;

public class OutsideWorkingHoursException extends HotelException {
    public OutsideWorkingHoursException(String message) {
        super(message);
    }
}