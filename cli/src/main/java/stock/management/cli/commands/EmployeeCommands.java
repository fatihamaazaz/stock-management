package stock.management.cli.commands;


import jakarta.annotation.PostConstruct;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.shell.standard.ShellCommandGroup;
import org.springframework.shell.standard.ShellComponent;
import org.springframework.shell.standard.ShellMethod;
import org.springframework.shell.standard.ShellOption;
import stock.management.employee_and_products_management.components.EmployeeService;
import stock.management.employee_and_products_management.dto.EmployeeDTO;
import stock.management.employee_and_products_management.entities.Employee;
import stock.management.employee_and_products_management.entities.Role;

import java.util.HashMap;
import java.util.Map;
import java.util.function.Consumer;

@ShellComponent
@ShellCommandGroup("Employee management commands")
public class EmployeeCommands {

    @Autowired
    private EmployeeService employeeService;


    private Map<Role, Consumer<EmployeeDTO>> rolesMapping;

    @PostConstruct
    void init(){
        rolesMapping = new HashMap<>();
        rolesMapping.put(Role.STOCKCONTROLLERS, employeeService::addStockController);
        rolesMapping.put(Role.PURCHASERESPONSABLE, employeeService::addPurchaseResponsible);
    }

    @ShellMethod(key = "addEmployee", value = "This command create an employee account.")
    public String addEmployee(@ShellOption(value = { "--role" }) Role role,
                                                   @ShellOption(value = { "--username" }) String username,
                                                   @ShellOption(value = { "--password" }) String password,
                                                   @ShellOption(value = { "--email" }) String email,
                                                   @ShellOption(value = { "--phone" }) int phone){
        try{
            EmployeeDTO employeeDTO = new EmployeeDTO(username, password, email, phone);
            if(rolesMapping.containsKey(role)){
                rolesMapping.get(role).accept(employeeDTO);
                return "Success: employee has been added successfuly";
            }
            else{
                return "Error: role should be STOCKCONTROLLERS or PURCHASERESPONSABLE";
            }
        }
        catch (Exception e){
            return "Error: "+ e.getMessage();
        }
    }

    @ShellMethod(key = "getEmployee", value = "this command returns employee informations.")
    public String getEmployee(@ShellOption(value = { "--username" }) String username){
        try{
            return employeeService.getEmployeeByUsername(username).toString();
        }
        catch (Exception e){
            return "Error: "+ e.getMessage();
        }
    }

    @ShellMethod(key = "deleteEmployee", value = "this command delete employee account.")
    public String deleteEmployee(@ShellOption(value = { "--username" }) String username){
        try {
            String confirmation = GeneralCommands.getConfirmationFromUser("delete user of username " + username);
            if ("y".equalsIgnoreCase(confirmation)){
                employeeService.deleteEmployeeByUsername(username);
                return String.format("Success: employee of username %s has been deleted successfuly", username);
            }
            else {
                return "Failure: deletion operation has been aborted";
            }
        }
        catch (Exception e){
            return "Error: "+ e.getMessage();
        }

    }

    @ShellMethod(key = "updateEmployee", value = "this command update employee account.")
    public String updateEmployee(@ShellOption(value = { "--username" }) String username,
                                 @ShellOption(value = { "--password" }, defaultValue = "") String password,
                                 @ShellOption(value = { "--email" }, defaultValue = "") String email,
                                 @ShellOption(value = { "--phone" }, defaultValue = "0") String phone){
        try{
            String confirmation = GeneralCommands.getConfirmationFromUser("update user of username " + username);
            if ("y".equalsIgnoreCase(confirmation)){
                Employee employee = employeeService.getEmployeeByUsername(username);
                employee.setPassword(password.isEmpty() ? password : employee.getPassword());
                employee.setEmail(email.isEmpty() ? email : employee.getEmail());
                employee.setPhone(Integer.parseInt(phone) != 0 ? Integer.parseInt(phone) : employee.getPhone());
                employeeService.updateEmployee(employee);
                return "Success: employee has been updated successfuly";
            }
            else {
                return "Failure: deletion operation has been aborted";
            }
        }
        catch (Exception e){
            return "Error: "+ e.getMessage();
        }
    }
}