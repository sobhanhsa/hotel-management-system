package view;

import managers.UserManager;

import java.time.LocalDate;
import java.time.YearMonth;

import managers.LogManager;
import managers.ReportManager;
import models.User;

public class SuperAdminView {

    private final UserManager userManager;
    private final LogManager logManager;
    private final ReportManager reportManager;

    private final User currentUser;


    public SuperAdminView(
            User currentUser,
            UserManager userManager,
            LogManager logManager,
            ReportManager reportManager
    ) {
        this.currentUser = currentUser;
        this.userManager = userManager;
        this.logManager = logManager;
        this.reportManager = reportManager;
    }


    public void start() {

        boolean running = true;

        while (running) {

            printMenu();

            int choice = Console.readInt("Select option: ");

            try {

                switch (choice) {

                    case 1:
                        createHotelManager();
                        break;

                    case 2:
                        systemConfiguration();
                        break;

                    case 3:
                        showFinancialReports();
                        break;

                    case 4:
                        disableUser();
                        break;

                    case 5:
                        showLogs();
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
                "SUPER ADMIN PANEL\n" +
                "User: " + currentUser.getUsername()
        );

        Console.println("1. Create Hotel Manager");
        Console.println("2. System Configuration");
        Console.println("3. Financial Reports");
        Console.println("4. Disable User");
        Console.println("5. View System Logs");
        Console.println("0. Logout");

        Console.line();
    }


    private void createHotelManager() {

        Console.title("Create Hotel Manager");


        String username =
                Console.readLine("Username: ");

        String password =
                Console.readLine("Password: ");

        String name =
                Console.readLine("Full name: ");


        userManager.createHotelManager(
                name,
                username,
                password,
                "HM",
                LocalDate.now(),
                "management"
        );


        Console.success(
                "Hotel Manager created successfully."
        );
    }


    private void systemConfiguration() {

        Console.title("System Configuration");


        Console.println(
                "Current configuration:"
        );


        // my configurations are static :(


        Console.warning(
                "Configuration menu not connected yet."
        );
    }


    private void showFinancialReports() {

        Console.title("Financial Reports");


        String report = reportManager.monthlyIncomeReport(YearMonth.now());


        Console.println(report);
    }


    private void disableUser() {

        Console.title("Disable User");


        String username =
                Console.readLine(
                        "Username to disable: "
                );


        userManager.disableUser(username);


        Console.success(
                "User disabled successfully."
        );
    }


    private void showLogs() {

        Console.title("System Logs");

        var logs = logManager.getLogs();

        if (logs.isEmpty()) {
            Console.warning("No logs found.");
            return;
        }

        for (var log : logs) {
            Console.println(log.toString());
        }
    }   
}