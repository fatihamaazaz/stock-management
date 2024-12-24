package stock.management.delivery_service.commands;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.shell.Availability;
import org.springframework.shell.standard.*;
import org.springframework.stereotype.Component;
import stock.management.delivery_service.components.DatabaseService;
import stock.management.delivery_service.components.DeliveryService;
import stock.management.delivery_service.entities.Delivery;

@ShellComponent
@ShellCommandGroup("Delivery commands")
@Component
public class DeliveryCommands {

    @Autowired
    private DeliveryService deliveryService;

    @Autowired
    private DatabaseService databaseService;

    private boolean connected;
    private String employee;

    @ShellMethod(key = "connect", value = "This command allow you to connect.")
    public String connect(@ShellOption(value = "--username") String username,
                          @ShellOption(value = "--password") String password) {
        connected = databaseService.checkUserExitence(username, password);
        employee = username;
        return connected ? "Connected successfully as " + username : "Could not connect";
    }

    @ShellMethod(key = "addDelivery", value = "This command create a delivery event.")
    public String addDeliveryEvent(@ShellOption(value = "--product") String product,
                                   @ShellOption(value = "--quantity") int quantity){
        try {
            deliveryService.addDelivery(new Delivery(employee, product, quantity));
            return "Success: Event has been created successfuly";
        }
        catch (Exception e){
            return "Error: "+ e.getMessage();
        }
    }

    @ShellMethodAvailability("addDeliveryEvent")
    public Availability addDeliveryEventAvailability() {
        return connected
                ? Availability.available()
                : Availability.unavailable("Please set your username using connect command");
    }

}
