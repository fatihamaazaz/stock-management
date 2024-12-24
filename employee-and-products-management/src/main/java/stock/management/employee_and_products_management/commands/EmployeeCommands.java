package stock.management.employee_and_products_management.commands;


import jakarta.annotation.PostConstruct;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.shell.Availability;
import org.springframework.shell.standard.*;
import org.springframework.stereotype.Component;
import stock.management.employee_and_products_management.components.EmployeeService;
import stock.management.employee_and_products_management.converter.RoleConverter;
import stock.management.employee_and_products_management.dto.AuthDTO;
import stock.management.employee_and_products_management.dto.EmployeeDTO;
import stock.management.employee_and_products_management.entities.Employee;
import stock.management.employee_and_products_management.entities.Role;

import java.util.HashMap;
import java.util.Map;
import java.util.function.Consumer;

@ShellComponent
@ShellCommandGroup("Employee management commands")
@Component
public class EmployeeCommands {

    @Autowired
    private EmployeeService employeeService;

    @Autowired
    private GeneralCommands generalCommands;

    private boolean connected;

    private Map<Role, Consumer<EmployeeDTO>> rolesMapping;

    @PostConstruct
    void init(){
        rolesMapping = new HashMap<>();
        rolesMapping.put(Role.STOCKCONTROLLERS, employeeService::addStockController);
        rolesMapping.put(Role.PURCHASERESPONSABLE, employeeService::addPurchaseResponsible);
    }

    @ShellMethod(key = "connect", value = "This command allow you to connect.")
    public String connect(@ShellOption(value = "--username") String username,
                          @ShellOption(value = "--password") String password) {
        connected = employeeService.connect(new AuthDTO(username, password), true);
        return "Connected successfully as " + username;
    }

    public Availability isAvailable() {
        return connected
                ? Availability.available()
                : Availability.unavailable("Please set your username and password using connect command");
    }

    @ShellMethod(key = "addEmployee", value = "This command create an employee account.")
    @ShellMethodAvailability("isAvailable")
    public String addEmployee(@ShellOption(value = { "--role" }) String roleString,
                                                   @ShellOption(value = { "--username" }) String username,
                                                   @ShellOption(value = { "--password" }) String password,
                                                   @ShellOption(value = { "--email" }) String email,
                                                   @ShellOption(value = { "--phone" }) String phone){
        try{
            this.init();
            EmployeeDTO employeeDTO = new EmployeeDTO(username, password, email, phone);
            Role role = RoleConverter.convertStringToRole(roleString);
            if(role.equals(Role.PURCHASERESPONSABLE) || role.equals(Role.STOCKCONTROLLERS)){
                rolesMapping.get(role).accept(employeeDTO);
                return "Success: employee has been added successfuly";
            }
            else{
                return "Error: role should be STOCKCONTROLLERS or PURCHASERESPONSABLE";
            }
        }
        catch (DataIntegrityViolationException e){
            return "Error: employee exists already";
        }
        catch (Exception e){
            return "Error: "+ e.getMessage();
        }
    }

    @ShellMethod(key = "getEmployee", value = "this command returns employee informations.")
    @ShellMethodAvailability("isAvailable")
    public String getEmployee(@ShellOption(value = { "--username" }) String username){
        try{
            return employeeService.getEmployeeByUsername(username).toString();
        }
        catch (Exception e){
            return "Error: "+ e.getMessage();
        }
    }

    @ShellMethod(key = "deleteEmployee", value = "this command delete employee account.")
    @ShellMethodAvailability("isAvailable")
    public String deleteEmployee(@ShellOption(value = { "--username" }) String username){
        try {
            String confirmation = generalCommands.getConfirmationFromUser("delete user of username " + username);
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
    @ShellMethodAvailability("isAvailable")
    public String updateEmployee(@ShellOption(value = { "--username" }) String username,
                                 @ShellOption(value = { "--password" }, defaultValue = "") String password,
                                 @ShellOption(value = { "--email" }, defaultValue = "") String email,
                                 @ShellOption(value = { "--phone" }, defaultValue = "0") String phone){
        try{
            String confirmation = generalCommands.getConfirmationFromUser("update user of username " + username);
            if ("y".equalsIgnoreCase(confirmation)){
                Employee employee = employeeService.getEmployeeByUsername(username);
                employee.setPassword(password.isEmpty() ? employee.getPassword() : password);
                employee.setEmail(email.isEmpty() ? employee.getEmail() : email);
                employee.setPhone(phone.isEmpty() ?  employee.getPhone() : phone);
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
