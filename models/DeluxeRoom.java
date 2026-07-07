import enums.RoomStatus;
import enums.RoomType;

public class DeluxeRoom extends Room {

    public DeluxeRoom(String roomNumber, double basePrice, int floorNumber, int capacity) {
        super(roomNumber, RoomType.DELUXE, RoomStatus.AVAILABLE,
                basePrice, floorNumber, capacity);
    }

    @Override
    public double calculatePrice(int nights, int guestCount,
        double seasonFactor
    ) {

        double guestFactor = calcGuestFactor(guestCount);

        return basePrice * 1.5 * seasonFactor * guestFactor * nights;
    }
}