package stock.management.employee_and_products_management.commands;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.shell.Availability;
import org.springframework.shell.standard.*;
import org.springframework.stereotype.Component;
import stock.management.employee_and_products_management.commands.GeneralCommands;
import stock.management.employee_and_products_management.components.EmployeeService;
import stock.management.employee_and_products_management.components.ProductService;
import stock.management.employee_and_products_management.dto.AuthDTO;
import stock.management.employee_and_products_management.dto.ProductDTO;


@ShellComponent
@ShellCommandGroup("Products management commands")
@Component
public class ProductCommands {

    @Autowired
    private ProductService productService;

    @Autowired
    private EmployeeService employeeService;

    @Autowired
    private GeneralCommands generalCommands;

    private boolean connected;

    @ShellMethod(key = "connect", value = "This command allow you to connect.")
    public String connect(@ShellOption(value = "--username") String username,
                          @ShellOption(value = "--password") String password) {
        connected = employeeService.connect(new AuthDTO(username, password), false);
        return "Connected successfully as " + username;
    }

    public Availability isAvailable() {
        return connected
                ? Availability.available()
                : Availability.unavailable("Please set your username and password using connect command");
    }

    @ShellMethod(key = "addProduct", value = "This command add new product to database.")
    @ShellMethodAvailability("isAvailable")
    public String addProduct(@ShellOption(value = { "--codeBar" }) String codeBar,
                             @ShellOption(value = { "--quantity" }) int quantity){
        try{
            ProductDTO productDTO = new ProductDTO(codeBar, quantity);
            productService.addProduct(productDTO);
            return "Success: product has been added successfuly";
        }
        catch (Exception e){
            return "Error: "+ e.getMessage();
        }
    }

    @ShellMethod(key = "deleteProduct", value = "this command delete product.")
    @ShellMethodAvailability("isAvailable")
    public String deleteProduct(@ShellOption(value = { "--codeBar" }) String codeBar){
        try {
            String confirmation = generalCommands.getConfirmationFromUser("delete product of codeBar " + codeBar);
            if ("y".equalsIgnoreCase(confirmation)){
                productService.deleteProductBycodeBar(codeBar);
                return String.format("Success: product of codeBar %s has been deleted successfuly", codeBar);
            }
            else {
                return "Failure: deletion operation has been aborted";
            }
        }
        catch (Exception e){
            return "Error: "+ e.getMessage();
        }

    }

    @ShellMethod(key = "getProductQuantity", value = "this command returns product quantity.")
    @ShellMethodAvailability("isAvailable")
    public String getProductQuantity(@ShellOption(value = { "--codeBar" }) String codeBar){
        try{
            return String.valueOf(productService.getProductQuantityByCodeBar(codeBar));
        }
        catch (Exception e){
            return "Error: "+ e.getMessage();
        }
    }

    @ShellMethod(key = "getAllProducts", value = "this command returns all products.")
    @ShellMethodAvailability("isAvailable")
    public String getAllProducts(){
        try{
            StringBuilder sb = new StringBuilder();
            String format = "%-5s %-25s %-8s%n";
            sb.append(String.format(format,"ID", "CodeBar", "Quantity"));
            productService.ListAllProducts().forEach(p -> sb.append(String.format(format, p.getId(), p.getCodeBar(), p.getQuantity())));
            return sb.toString();
        }
        catch (Exception e){
            return "Error: "+ e.getMessage();
        }
    }

    @ShellMethod(key = "updateProductQuantity", value = "this command updates product quantity.")
    @ShellMethodAvailability("isAvailable")
    public String updateProductQuantity(@ShellOption(value = { "--codeBar" }) String codeBar,
                                        @ShellOption(value = { "--quantity" }) int quantity){
        try{
            productService.updateproductQuantity(codeBar, quantity);
            return "Sucess: quantity has been updated successfuly";
        }
        catch (Exception e){
            return "Error: "+ e.getMessage();
        }
    }

}
