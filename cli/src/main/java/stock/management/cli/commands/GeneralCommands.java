package stock.management.cli.commands;

import java.util.Scanner;

public class GeneralCommands {

    public static String getConfirmationFromUser(String message) {
        System.out.printf("Are you sure you want to %s ? (y/n)", message);
        Scanner scanner = new Scanner(System.in);
        return scanner.nextLine();
    }
}
