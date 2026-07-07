package models;

import java.time.LocalDateTime;

import enums.PaymentMethod;
import interfaces.Exportable;

public class Payment implements Exportable {

    private static int nextPaymentId = 1;

    private final String paymentId;
    private final double amount;
    private final LocalDateTime paymentDateTime;
    private final PaymentMethod paymentMethod;

    public Payment(double amount, PaymentMethod paymentMethod) {
        this.paymentId = "P" + nextPaymentId++;
        this.amount = amount;
        this.paymentMethod = paymentMethod;
        this.paymentDateTime = LocalDateTime.now();
    }

    public String getPaymentId() {
        return paymentId;
    }

    public double getAmount() {
        return amount;
    }

    public LocalDateTime getPaymentDateTime() {
        return paymentDateTime;
    }

    public PaymentMethod getPaymentMethod() {
        return paymentMethod;
    }

    @Override
    public String exportToText() {
        return "Payment{" +
                "paymentId='" + paymentId + '\'' +
                ", amount=" + amount +
                ", paymentDateTime=" + paymentDateTime +
                ", paymentMethod=" + paymentMethod +
                '}';
    }
}