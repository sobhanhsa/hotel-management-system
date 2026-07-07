import java.util.ArrayList;
import java.util.List;

import interfaces.Billable;
import interfaces.Exportable;

public class Invoice implements Billable, Exportable {

    private static int nextInvoiceId = 1;

    private int lastCalculatedPaymentIndex = 0;

    private final int invoiceId;
    private final Reservation reservation;
    private final List<ServiceOrder> serviceOrders;
    private final List<Payment> payments;

    private double accommodationCost;
    private double serviceCost;
    private double membershipDiscountٍRate;
    private double membershipDiscount = 0;
    private double cityTax;
    private double vat;
    private double totalAmount;
    private double balance;

    public Invoice(Reservation reservation) {
        this.invoiceId = nextInvoiceId++;
        this.reservation = reservation;
        this.serviceOrders = new ArrayList<>();
        this.payments = new ArrayList<>();

        setMemberShipDiscountRate();

    }

    public void setMemberShipDiscountRate() {
        membershipDiscountٍRate = this.reservation.getGuest()
            .getMembershipLevel().getDiscountRate();
    }

    public void addService(ServiceOrder service) {
        serviceOrders.add(service);
    }

    public void addPayment(Payment payment) {
        payments.add(payment);
        updateBalance();
    }

    private void calculateAccommodationCost() {
        accommodationCost = reservation.calculateTotal();
    }

    private void calculateServiceCost() {
        serviceCost = 0;

        for (ServiceOrder service : serviceOrders) {
            serviceCost += service.getTotalPrice(this.reservation.getGuest());
        }
    }

    // useless for this architecture
    @Override
    public void applyDiscount(double percent) {
        
    }


    private void calculateCityTax(double amount) {
        cityTax = amount * 0.01;
    }

    private void calculateVat(double amount) {
        vat = amount * 0.09;
    }

    @Override
    public double calculateTotal() {

        // membership discount only applies to services and Accommodation costs

        calculateAccommodationCost();
        calculateServiceCost();

        membershipDiscount = totalAmount * membershipDiscountٍRate;

        totalAmount = (accommodationCost+serviceCost) * (1 - membershipDiscountٍRate);


        calculateCityTax(totalAmount);
        calculateVat(totalAmount);

        totalAmount += (vat + cityTax);

        updateBalance();

        return totalAmount;
    }

    private void updateBalance() {
        balance = totalAmount - getTotalPayments();
    }

    private double getTotalPayments() {
        double total = 0;

        for (int i = lastCalculatedPaymentIndex; i < payments.size(); i++) {
            total += payments.get(i).getAmount();
        }

        // so only new payments will apply
        lastCalculatedPaymentIndex = payments.size();

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

    public int getInvoiceId() {
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