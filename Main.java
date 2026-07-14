import enums.RoomType;
import exceptions.DuplicateRoomException;
import managers.FinancialManager;
import managers.LogManager;
import managers.ReportManager;
import managers.ReservationEngine;
import managers.RoomManager;
import managers.ServiceManager;
import managers.UserManager;
import models.User;
import view.Console;
import view.GuestView;
import view.HotelManagerView;
import view.LoginView;
import view.ReceptionistView;
import view.SuperAdminView;

public class Main {

    public static void main(String[] args) {

        // Managers
        LogManager logManager = new LogManager();
        UserManager userManager = new UserManager(logManager);
        FinancialManager financialManager = new FinancialManager(logManager);
        ReservationEngine reservationEngine = new ReservationEngine(logManager, financialManager);
        RoomManager roomManager = new RoomManager(reservationEngine);
        ReportManager reportManager = new ReportManager(
            roomManager,
            reservationEngine
        );
        ServiceManager serviceManager = new ServiceManager();

        // Initial data
        seedSystem(
                userManager,
                roomManager,
                serviceManager
        );

        LoginView loginView =
                new LoginView(userManager);

        while (true) {

            User user = loginView.start();

            if (user == null)
                break;

            switch (user.getRole()) {

                case SUPER_ADMIN:
                    new SuperAdminView(
                            user,
                            userManager,
                            logManager,
                            reportManager
                    ).start();
                    break;

                case HOTEL_MANAGER:
                    new HotelManagerView(
                            user,
                            roomManager,
                            userManager,
                            reservationEngine,
                            reportManager
                    ).start();
                    break;

                case RECEPTIONIST:
                    new ReceptionistView(
                            user,
                            roomManager,
                            reservationEngine,
                            userManager,
                            financialManager,
                            serviceManager
                    ).start();
                    break;

                case GUEST:
                    new GuestView(
                            user,
                            roomManager,
                            reservationEngine
                    ).start();
                    break;
                default:
                    Console.error("system error");
                    return;
            }
        }

        Console.success("Hope to see you again !");
    }

    private static void seedSystem(
            UserManager userManager,
            RoomManager roomManager,
            ServiceManager serviceManager
    ) {
        // sample rooms
        try {
            roomManager.addRoom("101", RoomType.STANDARD, 1_200_000, 1, 2);
            roomManager.addRoom("102", RoomType.STANDARD, 1_200_000, 1, 2);
            roomManager.addRoom("103", RoomType.STANDARD, 1_400_000, 1, 3);

            roomManager.addRoom("201", RoomType.DELUXE, 1_800_000, 2, 2);
            roomManager.addRoom("202", RoomType.DELUXE, 2_000_000, 2, 3);
            roomManager.addRoom("203", RoomType.DELUXE, 2_000_000, 2, 3);

            roomManager.addRoom("301", RoomType.SUITE, 3_000_000, 3, 4);
            roomManager.addRoom("302", RoomType.SUITE, 3_200_000, 3, 4);

            roomManager.addRoom("401", RoomType.PENTHOUSE, 6_000_000, 4, 5);
            roomManager.addRoom("402", RoomType.PENTHOUSE, 7_000_000, 4, 6);
        } catch (DuplicateRoomException e) {
            throw new IllegalStateException("Could not create sample rooms.", e);
        }
    }
}
