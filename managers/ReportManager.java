package managers;

import java.time.LocalDate;
import java.time.YearMonth;
import java.util.*;

import models.DebtorReport;
import models.Guest;
import models.GuestHistoryReport;
import models.IncomeReport;
import models.OccupancyReport;
import models.PopularRoomReport;
import models.Reservation;
import models.Room;
import models.RoomStatusReport;

public class ReportManager {

    private List<Room> rooms;
    private List<Reservation> reservations;

    public ReportManager(List<Room> rooms,
        List<Reservation> reservations,
        List<Guest> guests) {

        this.rooms = rooms;
        this.reservations = reservations;
    }


    // R01 - Room Status Report
    public String roomStatusReport() {
        return new RoomStatusReport(rooms).exportToText();
    }



    // R02 - Daily Occupancy Report
    public String occupancyReport(LocalDate date) {

        return new OccupancyReport(rooms, reservations, date).exportToText();
    }



    // R03 - Monthly Income Report
    public String monthlyIncomeReport(YearMonth month) {
        return new IncomeReport(reservations, month).exportToText();
    }




    // R04 - Most Popular Rooms
    public String popularRoomsReport() {

        return new PopularRoomReport(reservations).exportToText();
    }





    // R05 - Debtors Report
    public String debtorsReport() {

        return new DebtorReport(reservations).exportToText();
    }






    // R06 - Guest History
    public String guestHistoryReport(Guest guest) {
        return new GuestHistoryReport(guest).exportToText();
    }



}