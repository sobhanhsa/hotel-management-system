package managers;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import enums.RoomType;
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
}