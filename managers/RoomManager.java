package managers;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import enums.RoomStatus;
import enums.RoomType;
import exceptions.DuplicateRoomException;
import exceptions.RoomNotFoundException;
import models.Room;

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

    public void addRoom(Room room) throws DuplicateRoomException {

        if (findRoom(room.getRoomNumber()) != null) {
            throw new DuplicateRoomException(
                    "Room " + room.getRoomNumber() + " already exists"
            );
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