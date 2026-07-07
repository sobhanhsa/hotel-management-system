package enums;

public enum MembershipLevel {

    BRONZE(0),
    SILVER(0.05),
    GOLD(0.10),
    PLATINUM(0.15);

    private final double discountRate;

    MembershipLevel(double discountRate) {
        this.discountRate = discountRate;
    }

    public double getDiscountRate() {
        return discountRate;
    }
}