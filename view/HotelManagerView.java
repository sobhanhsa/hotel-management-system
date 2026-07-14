package view;

import managers.RoomManager;
import managers.UserManager;
import managers.ReservationEngine;

import java.time.LocalDate;
import java.time.YearMonth;
import java.util.List;

import enums.RoomStatus;
import enums.RoomType;
import exceptions.DuplicateRoomException;
import exceptions.RoomNotFoundException;
import managers.ReportManager;
import managers.ReservationEngine;
import models.Reservation;
import models.User;

public class HotelManagerView {

    private final User currentUser;

    private final RoomManager roomManager;
    private final UserManager userManager;
    private final ReservationEngine reservationEngine;
    private final ReportManager reportManager;


    public HotelManagerView(
            User currentUser,
            RoomManager roomManager,
            UserManager userManager,
            ReservationEngine reservationEngine,
            ReportManager reportManager
    ) {
        this.currentUser = currentUser;
        this.roomManager = roomManager;
        this.userManager = userManager;
        this.reservationEngine = reservationEngine;
        this.reportManager = reportManager;
    }


    public void start() {

        boolean running = true;

        while (running) {

            printMenu();

            int choice =
                    Console.readInt("Select option: ");


            try {

                switch (choice) {

                    case 1:
                        addRoom();
                        break;

                    case 2:
                        removeRoom();
                        break;

                    case 3:
                        createReceptionist();
                        break;

                    case 4:
                        viewReservations();
                        break;

                    case 5:
                        changeRoomStatus();
                        break;

                    case 6:
                        showReports();
                        break;

                    case 0:
                        running = false;
                        break;

                    default:
                        Console.error("Invalid option.");
                }


            } catch (RuntimeException e) {

                Console.error(e.getMessage());
            }


            if (running) {
                Console.pause();
            }
        }
    }


    private void printMenu() {

        Console.title(
                "HOTEL MANAGER PANEL - "
                + currentUser.getUsername()
        );


        Console.println("1. Add Room");
        Console.println("2. Remove Room");
        Console.println("3. Create Receptionist");
        Console.println("4. View Reservations");
        Console.println("5. Change Room Status");
        Console.println("6. Reports");
        Console.println("0. Logout");

        Console.line();
    }


    private void addRoom() {

        Console.title("Add Room");


        String roomNumber =
                Console.readLine("Room Number: ");

        int floor =
                Console.readInt("Floor: ");

        int capacity =
                Console.readInt("Capacity: ");

        double price =
                Console.readDouble("Base Price: ");

        RoomType type = null;

        String roomType = 
            Console.readLine("Room Type: ");

        switch (roomType) {
            case "STANDARD":
                type = RoomType.STANDARD;
                break;
            case "DELUXE":
                type = RoomType.DELUXE;
                break;
            case "SUITE":
                type = RoomType.SUITE;
                break;
            case "PENTHOUSE":
                type = RoomType.PENTHOUSE;
                break;
        
            default:
                break;
        }

        if (!(type instanceof RoomType)) {
            Console.error("invalid room type");
            return;
        }
        try {
            roomManager.addRoom(roomNumber, type, price, floor, capacity );
            Console.success(
                    "Room added successfully."
            );
            
        } catch (DuplicateRoomException e) {
            Console.error("room already exists");
        }
        catch(Exception e) {
            Console.error(e.getMessage());
        }


    }


    private void removeRoom() {

        Console.title("Remove Room");


        String roomNumber =
                Console.readLine(
                        "Room number: "
                );

        try {
            roomManager.removeRoom(roomNumber);
            Console.success(
                    "Room removed."
            );
            
        } catch (RoomNotFoundException e) {
            Console.error("Room not found");
        }
        catch(Exception e) {
            Console.error(e.getMessage());
        }


    }


    private void createReceptionist() {

        Console.title(
                "Create Receptionist"
        );


        String username =
                Console.readLine("Username: ");

        String password =
                Console.readLine("Password: ");

        String name =
                Console.readLine("Name: ");


        try {
            
            userManager.createReceptionist(
                    name,
                    username,
                    password,
                    "RP", LocalDate.now(), "reception"
            );
    
    
            Console.success(
                    "Receptionist created."
            );
        } catch (IllegalArgumentException e) {
            Console.error("username already exists");
        }
        catch (Exception e) {
            Console.error(e.getMessage());
        }
    }


    private void viewReservations() {

        Console.title(
                "All Reservations"
        );


        List<Reservation> reservations =
                reservationEngine.getReservations();


        for (Reservation reservation : reservations) {

            Console.println(
                    reservation.toString()
            );
        }
    }


    private void changeRoomStatus() {

        Console.title(
                "Change Room Status"
        );


        String roomNumber =
            Console.readLine(
                    "Room Number: "
            );


        RoomStatus status = null;

        String roomStatus =
            Console.readLine(
                    "New Status: "
            );

        switch (roomStatus) {
            case "AVAILABLE":
                status = RoomStatus.AVAILABLE;
                break;
            case "RESERVED":
                status = RoomStatus.RESERVED;
                break;
            case "OCCUPIED":
                status = RoomStatus.OCCUPIED;
                break;
            case "MAINTENANCE":
                status = RoomStatus.MAINTENANCE;
                break;
            case "OUT_OF_SERVICE":
                status = RoomStatus.OUT_OF_SERVICE;
                break;
            default:
                Console.error("Invalid room status.");
                return;
        }

        try {
            roomManager.changeRoomStatus(roomNumber, status);

            Console.success(
                    "Room status updated."
            );
        } catch (RoomNotFoundException e) {
            Console.error(roomNumber + " not found");
        }
        catch (Exception e) {
            Console.error(e.getMessage());
        }
        


    }


    private void showReports() {

        Console.title(
                "Reports"
        );


        Console.println(
                reportManager.roomStatusReport()
        );


        Console.println(
                reportManager.occupancyReport(LocalDate.now())
        );


        Console.println(
                reportManager.monthlyIncomeReport(YearMonth.now())
        );
    }
}
