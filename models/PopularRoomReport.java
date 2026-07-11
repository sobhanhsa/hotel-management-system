package models;

import enums.RoomType;

import java.util.EnumMap;
import java.util.List;
import java.util.Map;


public class PopularRoomReport extends Report {

    private final List<Reservation> reservations;


    public PopularRoomReport(List<Reservation> reservations)
    {
        super("R04 - Popular Room Report");

        this.reservations = reservations;
    }


    @Override
    public String exportToText()
    {
        Map<RoomType, Integer> roomCounter =
                new EnumMap<>(RoomType.class);


        for (RoomType type : RoomType.values())
        {
            roomCounter.put(type, 0);
        }


        for (Reservation reservation : reservations)
        {
            if (reservation.getStatus() ==
                    enums.ReservationStatus.CANCELLED)
            {
                continue;
            }


            RoomType type =
                    reservation.getRoom().getType();


            roomCounter.put(
                    type,
                    roomCounter.get(type) + 1
            );
        }


        StringBuilder builder =
                new StringBuilder();


        builder.append("========================================\n");
        builder.append(title).append("\n");
        builder.append("Generated At: ")
                .append(generatedAt)
                .append("\n");
        builder.append("========================================\n\n");


        builder.append("Room Type Reservation Count\n");
        builder.append("-----------------------------\n");


        for (RoomType type : RoomType.values())
        {
            builder.append(String.format(
                    "%-15s : %d%n",
                    type,
                    roomCounter.get(type)
            ));
        }


        return builder.toString();
    }
}