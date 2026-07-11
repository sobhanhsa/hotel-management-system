package models;

import java.util.List;


public class DebtorReport extends Report {

        private final List<Reservation> reservations;


        public DebtorReport(List<Reservation> reservations) {

        super("R05 - Debtor Report");

        this.reservations = reservations;
        }


        @Override
        public String exportToText() {

        StringBuilder builder =
                new StringBuilder();


        builder.append("========================================\n");
        builder.append(title).append("\n");
        builder.append("Generated At: ")
                .append(generatedAt)
                .append("\n");
        builder.append("========================================\n\n");


        boolean hasDebtors = false;


        for (Reservation reservation : reservations) {

                Invoice invoice =
                        reservation.getInvoice();


                if (invoice == null) {
                continue;
                }


                double balance =
                        invoice.getBalance();


                if (balance <= 0) {
                continue;
                }


                hasDebtors = true;


                Guest guest =
                        reservation.getGuest();


                builder.append("Guest Name: ")
                        .append(guest.getName())
                        .append("\n");


                builder.append("Reservation ID: ")
                        .append(reservation.getReservationId())
                        .append("\n");


                builder.append("Room: ")
                        .append(reservation.getRoom().getRoomNumber())
                        .append("\n");


                builder.append(String.format(
                        "Remaining Balance: %.2f%n",
                        balance
                ));


                builder.append("-----------------------------\n");
        }


        if (!hasDebtors) {

                builder.append(
                        "No unpaid balances found.\n"
                );
        }


        return builder.toString();
        }
}