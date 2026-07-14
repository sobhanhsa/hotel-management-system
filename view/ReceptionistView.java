package view;

import models.User;
import managers.FinancialManager;
import managers.ReservationEngine;
import managers.RoomManager;
import managers.ServiceManager;
import managers.UserManager;
import models.Guest;
import models.Invoice;
import models.Receptionist;
import models.Reservation;
import models.Service;
import models.Room;
import enums.RoomType;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import exceptions.GuestNotFoundException;
import exceptions.HotelException;
import exceptions.InvalidDateRangeException;
import exceptions.ReservationConflictException;
import exceptions.ReservationNotFoundException;
import exceptions.RoomNotAvailableException;
import exceptions.RoomNotFoundException;

public class ReceptionistView {

    private final User currentUser;

    private final RoomManager roomManager;
    private final ReservationEngine reservationEngine;
    private final UserManager userManager;
    private final FinancialManager financialManager;
    private final ServiceManager serviceManager;


    public ReceptionistView(
            User currentUser,
            RoomManager roomManager,
            ReservationEngine reservationEngine,
            UserManager userManager,
            FinancialManager financialManager,
            ServiceManager serviceManager
    ) {
        this.currentUser = currentUser;
        this.roomManager = roomManager;
        this.reservationEngine = reservationEngine;
        this.userManager = userManager;
        this.financialManager = financialManager;
        this.serviceManager = serviceManager;
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
                        searchRooms();
                        break;

                    case 2:
                        createReservation();
                        break;

                    case 3:
                        checkIn();
                        break;

                    case 4:
                        checkOut();
                        break;

                    case 5:
                        addService();
                        break;

                    case 6:
                        guestHistory();
                        break;

                    case 0:
                        running = false;
                        break;

                    default:
                        Console.error("Invalid option.");
                }


            } catch (HotelException | IllegalArgumentException e) {

                Console.error(e.getMessage());

            }


            if (running) {
                Console.pause();
            }
        }
    }


    private void printMenu() {

        Console.title(
                "HOTEL GRAND PERSIA\n" +
                "Logged in as: RECEPTIONIST | "
                + currentUser.getUsername()
        );


        Console.println("1. Search Available Rooms");
        Console.println("2. New Reservation");
        Console.println("3. Check-In");
        Console.println("4. Check-Out");
        Console.println("5. Guest Services");
        Console.println("6. Guest History");
        Console.println("0. Logout");

        Console.line();
    }



    private void searchRooms() {

        Console.title(
                "Search Available Rooms"
        );


        String roomType =
            Console.readLine(
                    "Room type: "
            );

        

        RoomType type;

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
                Console.error("Invalid room type.");
                return;
        }

        LocalDate checkIn = LocalDate.parse(
                Console.readLine("Check-in date (YYYY-MM-DD): ")
        );

        LocalDate checkOut = LocalDate.parse(
                Console.readLine("Check-out date (YYYY-MM-DD): ")
        );

        List<Room> rooms =
            roomManager.searchAvailableRooms(
                type,
                checkIn,
                checkOut
            );


        if (rooms.isEmpty()) {
            Console.warning("No available rooms found.");
            return;
        }

        Console.println("Available rooms:");

        // its better to have exportToString

        for (Room room : rooms) {
            Console.println(
                    "Room " + room.getRoomNumber()
                    + " | Type: " + room.getType()
                    + " | Capacity: " + room.getCapacity()
                    + " | Status: " + room.getStatus()
            );
        }
    }



    private void createReservation() {

        Console.title(
                "Create Reservation"
        );


        String nationalId =
            Console.readLine(
                    "Guest national ID: "
            );

        String roomNumber =
            Console.readLine(
                    "Room number: "
            );

            
        LocalDate checkIn = LocalDate.parse(
            Console.readLine("Check-in: ")
        );
        
        LocalDate checkOut = LocalDate.parse(
                Console.readLine("Check-out: ")
        );

        ArrayList<LocalDate> dates = new ArrayList<>();
        
        for (LocalDate d = checkIn; d.isBefore(checkOut); d = d.plusDays(1)) {
            dates.add(d);
        }

        dates.add(checkOut);

        int guestsCount =
            Console.readInt(
                "Number of guests: "
            );
            
            
            
        try {
            Guest guest = userManager.findGuestByNationalId(nationalId);

            Room room = roomManager.findRoom(roomNumber);

            reservationEngine.createReservation(
                guest,
                room,
                dates,
                guestsCount
            );
        
    
    
            Console.success(
                "Reservation created."
            );
            
        } catch (InvalidDateRangeException e) {
            Console.error(e.getMessage());
        }
        catch (RoomNotAvailableException e) {
            Console.error(e.getMessage());
        }
        catch (ReservationConflictException e) {
            Console.error(e.getMessage());
        }
        catch (RoomNotFoundException e) {
            Console.error(e.getMessage());
        }
        catch (GuestNotFoundException e) {
            Console.error(e.getMessage());
        }
    }



    private void checkIn() {

        Console.title(
                "Check-In"
        );


        String reservationId =
                Console.readLine(
                        "Reservation ID: "
                );


        try {
            Reservation reservation = reservationEngine.findReservationById(reservationId);

            reservationEngine.checkIn(
                    reservation,
                    ((Receptionist) currentUser)
            );

            Console.success(
                    "Guest checked in."
            );
        } catch (ReservationNotFoundException e) {
            Console.error(e.getMessage());
        }
    }



    private void checkOut() {

        Console.title(
                "Check-Out"
        );


        String reservationId =
                Console.readLine(
                        "Reservation ID: "
                );


        try {
            Reservation reservation = reservationEngine.findReservationById(reservationId);

            reservationEngine.checkOut(
                    reservation,
                    ((Receptionist) currentUser)
            );

            Console.success(
                    "Guest checked out."
            );
        } catch (ReservationNotFoundException e) {
            Console.error(e.getMessage());
        }
    }



    private void addService() {

        Console.title(
                "Guest Services"
        );


        String reservationId =
                Console.readLine(
                        "Reservation ID: "
                );


        List<Service> services =
                serviceManager.getAvailableServices();


        for (int i = 0; i < services.size(); i++) {

            Console.println(
                    (i + 1) +
                    ": " +
                    services.get(i).getName() + "\t" +
                    "( price : " + String.valueOf(services.get(i).getPrice())+ " )"
            );
        }


        int choice =
                Console.readInt(
                        "Choose service: "
                );


        Service service =
                services.get(choice - 1);
        try {
            Invoice invoice =
                    reservationEngine
                    .getInvoiceByReservationId(reservationId);
    
    
            serviceManager.addServiceToInvoice(
                    invoice,
                    service
            );
    
    
            Console.success(
                    "Service added."
            );
            
        } catch (ReservationNotFoundException e) {
            Console.error(e.getMessage());
        }
    }



    private void guestHistory() {

        Console.title(
                "Guest History"
        );


        String nationalId =
                Console.readLine(
                        "Guest national ID: "
                );

        try {
            
            Guest guest = userManager.findGuestByNationalId(nationalId);
    
            List<Reservation> reservations = guest.getReservationHistory();
    
            if (reservations.isEmpty()) {
                Console.warning("No reservation history found.");
                return;
            }
    
            Console.println("Reservation history for " + guest.getName() + ":");
    
            for (Reservation reservation : reservations) {
                Console.println(
                        "Reservation " + reservation.getReservationId()
                        + " | Room: " + reservation.getRoom().getRoomNumber()
                        + " | Status: " + reservation.getStatus()
                        + " | Dates: " + reservation.getDates()
                );
            }

        } catch (GuestNotFoundException e) {
            Console.error(e.getMessage());
        }


    }
}
