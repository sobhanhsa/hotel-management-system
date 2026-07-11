package models;

import java.time.YearMonth;
import java.util.EnumMap;
import java.util.List;
import java.util.Map;

import enums.RoomType;

public class IncomeReport extends Report {

        private final List<Reservation> reservations;
        private final YearMonth month;


        public IncomeReport(
                List<Reservation> reservations,
                YearMonth month
        ) {
                super("R03 - Monthly Income Report");

                this.reservations = reservations;
                this.month = month;
        }


        @Override
        public String exportToText() {

        Map<RoomType, Double> incomeByType =
                new EnumMap<>(RoomType.class);


        for (RoomType type : RoomType.values()) {
                incomeByType.put(type, 0.0);
        }


        double totalIncome = 0;


        for (Reservation reservation : reservations) {

                Invoice invoice = reservation.getInvoice();

                if (invoice == null) {
                continue;
                }


                /*
                * Reservation start date determines
                * the month of the stay
                */
                YearMonth reservationMonth =
                        YearMonth.from(reservation.getStartDate());


                if (!reservationMonth.equals(month)) {
                continue;
                }


                double income =
                        invoice.getPaidAmount();


                RoomType type =
                        reservation.getRoom().getType();


                incomeByType.put(
                        type,
                        incomeByType.get(type) + income
                );


                totalIncome += income;
        }


        StringBuilder builder = new StringBuilder();


        builder.append("========================================\n");
        builder.append(title).append("\n");
        builder.append("Generated At: ")
                .append(generatedAt)
                .append("\n");
        builder.append("========================================\n\n");


        builder.append("Month: ")
                .append(month)
                .append("\n\n");


        for (RoomType type : RoomType.values()) {

                builder.append(String.format(
                        "%-15s : %.2f%n",
                        type,
                        incomeByType.get(type)
                ));
        }


        builder.append("\n-----------------------------\n");

        builder.append(String.format(
                "TOTAL INCOME : %.2f",
                totalIncome
        ));


        return builder.toString();
        }
}