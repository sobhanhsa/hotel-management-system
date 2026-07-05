import enums.RoomStatus;
import enums.RoomType;

public class Suite extends Room {

    public Suite(String roomNumber, double basePrice, int floorNumber, int capacity) {
        super(roomNumber, RoomType.SUITE, RoomStatus.AVAILABLE,
                basePrice, floorNumber, capacity);
    }

    @Override
    public double calculatePrice(
        int nights, int guestCount,
        double seasonFactor,
        double membershipDiscount
    ) {

        double guestFactor = calcGuestFactor(guestCount);

        return basePrice * 2.5 * seasonFactor * guestFactor * nights
                * (1 - membershipDiscount);
    }
}