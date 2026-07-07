package models;

import enums.RoomStatus;
import enums.RoomType;

public class StandardRoom extends Room {

    public StandardRoom(String roomNumber, double basePrice, int floorNumber, int capacity) {
        super(roomNumber, RoomType.STANDARD, RoomStatus.AVAILABLE,
                basePrice, floorNumber, capacity);
    }

    @Override
    public double calculatePrice(
            int nights, int guestCount,
            double seasonFactor
        ) {

        double guestFactor = calcGuestFactor(guestCount);

        return basePrice * 1.0 * seasonFactor * guestFactor * nights;
    }
}