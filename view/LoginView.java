package view;

import managers.UserManager;
import models.User;

public class LoginView {

    private final UserManager userManager;

    public LoginView(UserManager userManager) {
        this.userManager = userManager;
    }


    public User start() {

        while (true) {

            printMenu();

            int choice = Console.readInt("Select option: ");

            switch (choice) {

                case 1:
                    User user = login();

                    if (user != null) {
                        return user;
                    }

                    break;


                case 2:
                    registerGuest();
                    break;


                case 0:
                    return null;


                default:
                    Console.error("Invalid option.");
            }

            Console.pause();
        }
    }


    private void printMenu() {

        Console.title("HOTEL GRAND PERSIA");

        Console.println("1. Login");
        Console.println("2. Register as Guest");
        Console.println("0. Exit");

        Console.line();
    }


    private User login() {

        String username =
                Console.readLine("Username: ");

        String password =
                Console.readLine("Password: ");


        User user =
                userManager.login(username, password);


        if (user == null) {
            Console.error("Invalid username or password.");
            return null;
        }


        Console.success(
                "Welcome " + user.getUsername()
        );

        return user;
    }


    private void registerGuest() {

        try {

            String username =
                    Console.readLine("Username: ");

            String password =
                    Console.readLine("Password: ");

            String name =
                    Console.readLine("Full name: ");

            String nationalId =
                    Console.readLine("National ID: ");

            String phone =
                    Console.readLine("Phone: ");


            userManager.registerGuest(
                    username,
                    password,
                    name,
                    nationalId,
                    phone
            );


            Console.success(
                    "Account created."
            );


        } catch (RuntimeException e) {

            Console.error(e.getMessage());

        }
    }
}