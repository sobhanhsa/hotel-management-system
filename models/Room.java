import enums.RoomStatus;
import enums.RoomType;

public abstract class Room {

    protected String roomNumber;
    protected RoomType type;
    protected RoomStatus status;
    protected double basePrice;
    protected int floorNumber;
    protected int capacity;

    public Room(
            String roomNumber, RoomType type, RoomStatus status,
            double basePrice, int floorNumber, int capacity
    ) {
        this.roomNumber = roomNumber;
        this.type = type;
        this.status = status;
        this.basePrice = basePrice;
        this.floorNumber = floorNumber;
        this.capacity = capacity;
    }

    public String getRoomNumber() {
        return roomNumber;
    }

    public RoomStatus getStatus() {
        return status;
    }

    public void setStatus(RoomStatus status) {
        this.status = status;
    }

    public int getCapacity() {
        return capacity;
    }

    public RoomType getType() {
        return type;
    }

    public double calcGuestFactor(double guestCount) {
        return (guestCount == 1) ? 1.0 :
            (guestCount == 2) ? 1.2 : 1.4;
    }

    public abstract double calculatePrice(
        int nights,
        int guestCount,
        double seasonFactor
    );
}