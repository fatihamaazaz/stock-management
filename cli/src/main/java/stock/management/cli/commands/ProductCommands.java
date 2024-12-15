package stock.management.cli.commands;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.shell.standard.ShellCommandGroup;
import org.springframework.shell.standard.ShellComponent;
import org.springframework.shell.standard.ShellMethod;
import org.springframework.shell.standard.ShellOption;
import stock.management.employee_and_products_management.components.ProductService;
import stock.management.employee_and_products_management.dto.ProductDTO;
import stock.management.employee_and_products_management.entities.Role;

@ShellComponent
@ShellCommandGroup("Products management commands")
public class ProductCommands {

    @Autowired
    private ProductService productService;

    @ShellMethod(key = "addProduct", value = "This command add new product to database.")
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
    public String deleteProduct(@ShellOption(value = { "--codeBar" }) String codeBar){
        try {
            String confirmation = GeneralCommands.getConfirmationFromUser("delete product of codeBar " + codeBar);
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
    public String getProductQuantity(@ShellOption(value = { "--codeBar" }) String codeBar){
        try{
            return String.valueOf(productService.getProductQuantityByCodeBar(codeBar));
        }
        catch (Exception e){
            return "Error: "+ e.getMessage();
        }
    }

    @ShellMethod(key = "getAllProducts", value = "this command returns all products.")
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
