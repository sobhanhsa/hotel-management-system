package managers;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import enums.RoomStatus;
import enums.RoomType;
import exceptions.DuplicateRoomException;
import exceptions.RoomNotFoundException;
import models.DeluxeRoom;
import models.PentHouse;
import models.Room;
import models.StandardRoom;
import models.Suite;

public class RoomManager {

    private final List<Room> rooms = new ArrayList<>();
    private final ReservationEngine reservationEngine;

    public RoomManager(ReservationEngine reservationEngine) {
        this.reservationEngine = reservationEngine;
    }

    public List<Room> searchAvailableRooms(
            RoomType type,
            LocalDate checkIn,
            LocalDate checkOut) {

        return rooms.stream()
                .filter(r -> r.getType() == type)
                .filter(r -> reservationEngine.isRoomAvailable(
                        r,
                        new ArrayList<LocalDate>(List.of(checkIn,checkOut))
                    )
                )
                .toList();
    }

    public Room findRoom(String roomNumber) {
        for (Room room : rooms) {
            if (room.getRoomNumber().equals(roomNumber)) {
                return room;
            }
        }

        return null;
    }

    public boolean removeRoom(String roomNumber) throws RoomNotFoundException {
        Room room = findRoom(roomNumber);

        if (room == null) {
            throw new RoomNotFoundException("Room " + roomNumber + " not found");
        }

        rooms.remove(room);
        return true;
    }

    public void addRoom(
        String roomNumber,
        RoomType type,
        double basePrice,
        int floorNumber,
        int capacity
    ) throws DuplicateRoomException {

        if (findRoom(roomNumber) != null) {
            throw new DuplicateRoomException(
                    "Room already exists: " + roomNumber
            );
        }

        Room room;

        switch (type) {
            case STANDARD:
                room = new StandardRoom(
                        roomNumber,
                        basePrice,
                        floorNumber,
                        capacity
                );
                break;

            case DELUXE:
                room = new DeluxeRoom(
                        roomNumber,
                        basePrice,
                        floorNumber,
                        capacity
                );
                break;

            case SUITE:
                room = new Suite(
                        roomNumber,
                        basePrice,
                        floorNumber,
                        capacity
                );
                break;

            case PENTHOUSE:
                room = new PentHouse(
                        roomNumber,
                        basePrice,
                        floorNumber,
                        capacity
                );
                break;

            default:
                throw new IllegalArgumentException("Unknown room type");
        }

        rooms.add(room);
    }

    public void changeRoomStatus(String roomNumber, RoomStatus newStatus)
        throws RoomNotFoundException {

        Room room = findRoom(roomNumber);

        if (room == null) {
            throw new RoomNotFoundException(
                    "Room with number " + roomNumber + " does not exist"
            );
        }

        room.setStatus(newStatus);
    }
}