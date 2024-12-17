package stock.management.cli.commands;

import org.springframework.stereotype.Component;

import java.util.Scanner;

@Component
public class GeneralCommands {

    public String getConfirmationFromUser(String message) {
        System.out.printf("Are you sure you want to %s ? (y/n)", message);
        Scanner scanner = new Scanner(System.in);
        return scanner.nextLine();
    }
}
