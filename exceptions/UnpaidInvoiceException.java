package exceptions;

public class UnpaidInvoiceException extends HotelException {
    public UnpaidInvoiceException(String message) {
        super(message);
    }
}