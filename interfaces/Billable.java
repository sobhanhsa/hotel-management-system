package interfaces;

public interface Billable {
    double calculateTotal();
    void applyDiscount(double percent);
}