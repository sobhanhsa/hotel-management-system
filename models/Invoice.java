import java.util.ArrayList;
import java.util.List;

import interfaces.Billable;
import interfaces.Exportable;

public class Invoice implements Billable, Exportable {

    private static int nextInvoiceId = 1;

    private final String invoiceId;
    private final Reservation reservation;
    private final List<ServiceOrder> serviceOrders;
    private final List<Payment> payments;

    private double accommodationCost;
    private double serviceCost;
    private double membershipDiscount;
    private double cityTax;
    private double vat;
    private double totalAmount;
    private double balance;

    public Invoice(Reservation reservation) {
        this.invoiceId = "I" + nextInvoiceId++;
        this.reservation = reservation;
        this.serviceOrders = new ArrayList<>();
        this.payments = new ArrayList<>();
    }

    public void addService(ServiceOrder service) {
        serviceOrders.add(service);
    }

    public void addPayment(Payment payment) {
        payments.add(payment);
        updateBalance();
    }

    private void calculateAccommodationCost() {
        accommodationCost = reservation.getRoom().calculatePrice(
                reservation.getDates(),
                reservation.getGuest()
        );
    }

    private void calculateServiceCost() {
        serviceCost = 0;

        for (ServiceOrder service : serviceOrders) {
            serviceCost += service.getPrice();
        }
    }

    @Override
    public void applyDiscount() {
        membershipDiscount =
                (accommodationCost + serviceCost)
                        * reservation.getGuest()
                        .getMembershipLevel()
                        .getDiscountRate();
    }

    private void calculateCityTax() {
        cityTax = (accommodationCost + serviceCost - membershipDiscount) * 0.01;
    }

    private void calculateVat() {
        vat = (accommodationCost + serviceCost - membershipDiscount) * 0.09;
    }

    @Override
    public double calculateTotal() {

        calculateAccommodationCost();
        calculateServiceCost();
        applyDiscount();
        calculateCityTax();
        calculateVat();

        totalAmount =
                accommodationCost
                        + serviceCost
                        - membershipDiscount
                        + cityTax
                        + vat;

        updateBalance();

        return totalAmount;
    }

    private void updateBalance() {
        balance = totalAmount - getTotalPayments();
    }

    private double getTotalPayments() {
        double total = 0;

        for (Payment payment : payments) {
            total += payment.getAmount();
        }

        return total;
    }

    public boolean isPaid() {
        return balance <= 0;
    }

    @Override
    public String exportToText() {
        return """
                Invoice ID: %s
                Reservation: %s
                Accommodation: %.2f
                Services: %.2f
                Discount: %.2f
                City Tax: %.2f
                VAT: %.2f
                Total: %.2f
                Paid: %.2f
                Balance: %.2f
                """.formatted(
                invoiceId,
                reservation.getReservationId(),
                accommodationCost,
                serviceCost,
                membershipDiscount,
                cityTax,
                vat,
                totalAmount,
                getTotalPayments(),
                balance
        );
    }

    public String getInvoiceId() {
        return invoiceId;
    }

    public Reservation getReservation() {
        return reservation;
    }

    public List<ServiceOrder> getServiceOrders() {
        return serviceOrders;
    }

    public List<Payment> getPayments() {
        return payments;
    }

    public double getAccommodationCost() {
        return accommodationCost;
    }

    public double getServiceCost() {
        return serviceCost;
    }

    public double getMembershipDiscount() {
        return membershipDiscount;
    }

    public double getCityTax() {
        return cityTax;
    }

    public double getVat() {
        return vat;
    }

    public double getTotalAmount() {
        return totalAmount;
    }

    public double getBalance() {
        return balance;
    }
}