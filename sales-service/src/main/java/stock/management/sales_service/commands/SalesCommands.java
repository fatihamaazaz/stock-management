package stock.management.sales_service.commands;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.shell.Availability;
import org.springframework.shell.standard.ShellCommandGroup;
import org.springframework.shell.standard.ShellComponent;
import org.springframework.shell.standard.ShellMethod;
import org.springframework.shell.standard.ShellOption;
import stock.management.sales_service.components.DatabaseService;
import stock.management.sales_service.components.SalesService;
import stock.management.sales_service.entities.Sale;

@ShellComponent
@ShellCommandGroup("Sale commands")
public class SalesCommands {

    @Autowired
    private SalesService salesService;

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

    @ShellMethod(key = "addDelivery", value = "This command create a sale event.")
    public String addSaleEvent(@ShellOption(value = "--product") String product,
                                   @ShellOption(value = "--quantity") int quantity){
        try {
            salesService.addSale(new Sale(employee, product, quantity));
            return "Success: Event has been created successfuly";
        }
        catch (Exception e){
            return "Error: "+ e.getMessage();
        }
    }

    public Availability addSaleEventAvailability() {
        return connected
                ? Availability.available()
                : Availability.unavailable("Please set your username using connect command");
    }

}
