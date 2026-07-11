package models;

import java.util.List;

public class GuestHistoryReport extends Report {

        private final Guest guest;

        public GuestHistoryReport(Guest guest) {
                super("R06 - Guest History Report");
                this.guest = guest;
        }

        @Override
        public String exportToText() {
                StringBuilder builder = new StringBuilder();

                builder.append("========================================\n");
                builder.append(title).append("\n");
                builder.append("Generated At: ")
                        .append(generatedAt)
                        .append("\n");
                builder.append("========================================\n\n");

                if (guest == null) {
                        builder.append("Guest not found.\n");
                        return builder.toString();
                }

                builder.append("Guest Name: ")
                        .append(guest.getName())
                        .append("\n");

                builder.append("Membership Level: ")
                        .append(guest.getMembershipLevel())
                        .append("\n");

                builder.append("Total Stays: ")
                        .append(guest.getTotalStays())
                        .append("\n\n");

                builder.append("Reservation History\n");
                builder.append("-----------------------------\n");

                List<Reservation> history = guest.getReservationHistory();

                if (history.isEmpty()) {
                        builder.append("No reservation history.\n");

                        return builder.toString();
                }

                for (Reservation reservation : history) {
                        builder.append("Reservation ID: ")
                                .append(reservation.getReservationId())
                                .append("\n");

                        builder.append("Room Number: ")
                                .append(reservation.getRoom().getRoomNumber())
                                .append("\n");

                        builder.append("Room Type: ")
                                .append(reservation.getRoom().getType())
                                .append("\n");

                        builder.append("Status: ")
                                .append(reservation.getStatus())
                                .append("\n");

                        builder.append("Stay Dates: ")
                                .append(reservation.getDates().toString())
                                .append("\n");

                        builder.append("-----------------------------\n");
                }

                return builder.toString();
        }
}
