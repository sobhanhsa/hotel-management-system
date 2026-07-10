package managers;

import enums.LogLevel;
import enums.PaymentMethod;
import managers.LogManager;
import models.Guest;
import models.Invoice;
import models.Payment;
import exceptions.InsufficientPaymentException;

public class FinancialManager {

    private final LogManager logManager;


    public FinancialManager(LogManager logManager) {
        this.logManager = logManager;
    }


    /*
     * Add a payment to an invoice.
     * Supports partial payments.
     */
    public void makePayment(
            Invoice invoice,
            Guest guest,
            double amount) {


        if (amount <= 0) {
            throw new InsufficientPaymentException(
                    "Payment amount must be positive"
            );
        }


        Payment payment = new Payment(amount,PaymentMethod.ONLINE);


        invoice.addPayment(payment);


        logManager.addLog(
                LogLevel.INFO,
                guest.getUsername(),
                "PAYMENT",
                "[Invoice: " + invoice.getInvoiceId() + "] "
                + "[Amount: " + amount + "]"
        );
    }



    /*
     * Returns remaining debt.
     */
    public double calculateDebt(Invoice invoice) {

        return invoice.getBalance();
    }



    /*
     * Checks if guest has unpaid debt.
     */
    public boolean hasDebt(Invoice invoice) {

        return invoice.getBalance() > 0;
    }



    /*
     * Refund money after cancellation.
     */
    public double refund(
            Invoice invoice,
            Guest guest,
            double amount) {


        if (amount <= 0) {
            return 0;
        }


        Payment refundPayment =
                new Payment(-amount,PaymentMethod.ONLINE);


        invoice.addPayment(refundPayment);


        logManager.addLog(
                LogLevel.INFO,
                guest.getUsername(),
                "REFUND",
                "[Invoice: " + invoice.getInvoiceId() + "] "
                + "[Amount: " + amount + "]"
        );


        return amount;
    }
}