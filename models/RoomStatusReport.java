package models;

import enums.RoomStatus;
import enums.RoomType;
import models.Room;

import java.util.EnumMap;
import java.util.List;
import java.util.Map;

public class RoomStatusReport extends Report {

    private final List<Room> rooms;

    public RoomStatusReport(List<Room> rooms) {
        super("R01 - Room Status Report");
        this.rooms = rooms;
    }

    @Override
    public String exportToText() {

        StringBuilder builder = new StringBuilder();

        builder.append("========================================\n");
        builder.append(title).append("\n");
        builder.append("Generated At: ").append(generatedAt).append("\n");
        builder.append("========================================\n\n");

        for (RoomType type : RoomType.values()) {

            Map<RoomStatus, Integer> counter =
                    new EnumMap<>(RoomStatus.class);

            for (RoomStatus status : RoomStatus.values()) {
                counter.put(status, 0);
            }

            for (Room room : rooms) {

                if (room.getType() != type) {
                    continue;
                }

                RoomStatus status = room.getStatus();
                counter.put(status, counter.get(status) + 1);
            }

            builder.append(type).append("\n");
            builder.append("-----------------------------\n");

            for (RoomStatus status : RoomStatus.values()) {
                builder.append(String.format(
                        "%-18s : %d%n",
                        status,
                        counter.get(status)
                ));
            }

            builder.append("\n");
        }

        builder.append("Total Rooms : ")
                .append(rooms.size())
                .append("\n");

        return builder.toString();
    }
}