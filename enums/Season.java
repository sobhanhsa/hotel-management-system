package enums;

public enum Season {

    SUMMER(1.5),
    SPRING(1.2),
    FALL(1.2),
    WINTER(0.8);

    private final double rate;

    Season(double rate) {
        this.rate = rate;
    }

    public double getRate() {
        return rate;
    }
}