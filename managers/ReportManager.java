package managers;

import java.time.LocalDate;
import java.time.YearMonth;

import models.DebtorReport;
import models.Guest;
import models.GuestHistoryReport;
import models.IncomeReport;
import models.OccupancyReport;
import models.PopularRoomReport;
import models.RoomStatusReport;

public class ReportManager {

    private final RoomManager roomManager;
    private final ReservationEngine reservationEngine;

    public ReportManager(
        RoomManager roomManager,
        ReservationEngine reservationEngine
    ) {
        this.roomManager = roomManager;
        this.reservationEngine = reservationEngine;
    }


    // R01 - Room Status Report
    public String roomStatusReport() {
        return new RoomStatusReport(roomManager.getRooms()).exportToText();
    }



    // R02 - Daily Occupancy Report
    public String occupancyReport(LocalDate date) {
        return new OccupancyReport(
                roomManager.getRooms(),
                reservationEngine.getReservations(),
                date
        ).exportToText();
    }


    // R03 - Monthly Income Report
    public String monthlyIncomeReport(YearMonth month) {
        return new IncomeReport(
                reservationEngine.getReservations(),
                month
        ).exportToText();
    }


    // R04 - Most Popular Rooms
    public String popularRoomsReport() {
        return new PopularRoomReport(
                reservationEngine.getReservations()
        ).exportToText();
    }


    // R05 - Debtors Report
    public String debtorsReport() {
        return new DebtorReport(
                reservationEngine.getReservations()
        ).exportToText();
    }






    // R06 - Guest History
    public String guestHistoryReport(Guest guest) {
        return new GuestHistoryReport(guest).exportToText();
    }



}