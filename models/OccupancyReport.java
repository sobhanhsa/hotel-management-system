package models;

import java.time.LocalDate;
import java.util.List;

public class OccupancyReport extends Report {

    private final List<Room> rooms;
    private final List<Reservation> reservations;
    private final LocalDate date;

    public OccupancyReport(
            List<Room> rooms,
            List<Reservation> reservations,
            LocalDate date
    ) {
        super("R02 - Daily Occupancy Report");
        this.rooms = rooms;
        this.reservations = reservations;
        this.date = date;
    }

    @Override
    public String exportToText() {

        int occupiedRooms = 0;

        for (Room room : rooms) {

            boolean occupied = false;

            for (Reservation reservation : reservations) {

                if (!reservation.getRoom().equals(room)) {
                    continue;
                }

                if (
                    (reservation.getStatus() == enums.ReservationStatus.CANCELLED) ||
                    (reservation.getStatus() == enums.ReservationStatus.COMPLETED)
                ) {
                    continue;
                }

                if (reservation.getDates().contains(date)) {
                    occupied = true;
                    break;
                }
            }

            if (occupied) {
                occupiedRooms++;
            }
        }

        int totalRooms = rooms.size();
        int availableRooms = totalRooms - occupiedRooms;

        double occupancyRate = totalRooms == 0
                ? 0
                : occupiedRooms * 100.0 / totalRooms;

        StringBuilder builder = new StringBuilder();

        builder.append("========================================\n");
        builder.append(title).append("\n");
        builder.append("Generated At: ").append(generatedAt).append("\n");
        builder.append("========================================\n\n");

        builder.append("Date: ")
                .append(date)
                .append("\n\n");

        builder.append("Occupied Rooms : ")
                .append(occupiedRooms)
                .append("\n");

        builder.append("Available Rooms : ")
                .append(availableRooms)
                .append("\n");

        builder.append(String.format(
                "Occupancy Rate : %.2f%%",
                occupancyRate
        ));

        return builder.toString();
    }
}