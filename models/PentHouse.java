import enums.RoomStatus;
import enums.RoomType;

public class PentHouse extends Room {

    public PentHouse(String roomNumber, double basePrice, int floorNumber, int capacity) {
        super(roomNumber, RoomType.PENTHOUSE, RoomStatus.AVAILABLE,
                basePrice, floorNumber, capacity);
    }

    @Override
    public double calculatePrice(
    int nights, int guestCount,
        double seasonFactor,
        double membershipDiscount
    ) {

        double guestFactor = calcGuestFactor(guestCount);
        
        return basePrice * 5.0 * seasonFactor * guestFactor * nights
                * (1 - membershipDiscount);
    }
}