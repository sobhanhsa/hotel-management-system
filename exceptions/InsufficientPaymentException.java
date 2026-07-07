package exceptions;

public class InsufficientPaymentException extends HotelException {
    public InsufficientPaymentException(String message) {
        super(message);
    }
}