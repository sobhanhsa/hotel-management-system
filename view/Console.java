package view;

import java.util.Scanner;

public final class Console {

    private static final Scanner scanner = new Scanner(System.in);

    private Console() {
    }

    public static void title(String title) {
        line();
        System.out.println(title);
        line();
    }

    public static void line() {
        System.out.println("--------------------------------------------------");
    }

    public static void print(String text) {
        System.out.print(text);
    }

    public static void println(String text) {
        System.out.println(text);
    }

    public static void success(String message) {
        System.out.println("[SUCCESS] " + message);
    }

    public static void error(String message) {
        System.out.println("[ERROR] " + message);
    }

    public static void warning(String message) {
        System.out.println("[WARNING] " + message);
    }

    public static String readLine(String prompt) {
        System.out.print(prompt);
        return scanner.nextLine().trim();
    }

    public static int readInt(String prompt) {
        while (true) {
            System.out.print(prompt);

            try {
                return Integer.parseInt(scanner.nextLine().trim());
            } catch (NumberFormatException e) {
                error("Please enter a valid integer.");
            }
        }
    }

    public static double readDouble(String prompt) {
        while (true) {
            System.out.print(prompt);

            try {
                return Double.parseDouble(scanner.nextLine().trim());
            } catch (NumberFormatException e) {
                error("Please enter a valid number.");
            }
        }
    }

    public static boolean confirm(String prompt) {
        while (true) {
            System.out.print(prompt + "\n" + " (y/n): ");

            String input = scanner.nextLine().trim().toLowerCase();

            switch (input) {
                case "y":
                case "yes":
                    return true;

                case "n":
                case "no":
                    return false;

                default:
                    warning("Please enter y or n.");
            }
        }
    }

    public static void pause() {
        System.out.print("Press Enter to continue...");
        scanner.nextLine();
    }
}