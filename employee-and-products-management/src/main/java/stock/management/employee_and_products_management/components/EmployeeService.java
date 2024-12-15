package stock.management.employee_and_products_management.components;


import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.validation.annotation.Validated;
import stock.management.employee_and_products_management.dto.AuthDTO;
import stock.management.employee_and_products_management.dto.EmployeeDTO;
import stock.management.employee_and_products_management.entities.Employee;
import stock.management.employee_and_products_management.entities.Role;
import stock.management.employee_and_products_management.exceptions.EmployeeNotFoundException;
import stock.management.employee_and_products_management.exceptions.WrongPasswordException;
import stock.management.employee_and_products_management.mappers.EmployeeMapping;
import stock.management.employee_and_products_management.repositories.EmployeeRepository;

@Validated
@Service
public class EmployeeService {
    @Autowired
    private EmployeeRepository employeeRepository;

    @Autowired
    private EmployeeMapping employeeMapping;

    public Employee addStockController(@Valid EmployeeDTO employeeDTO){
        Employee employee = employeeMapping.mapToEmployee(employeeDTO);
        employee.setRole(Role.STOCKCONTROLLERS);
        return employeeRepository.save(employee);
    }

    public Employee addPurchaseResponsible(@Valid EmployeeDTO employeeDTO){
        Employee employee = employeeMapping.mapToEmployee(employeeDTO);
        employee.setRole(Role.PURCHASERESPONSABLE);
        return employeeRepository.save(employee);
    }

    public void deleteEmployeeByUsername(String username){
        Employee employee = employeeRepository.findEmployeeByUsername(username).orElseThrow(() ->
                new EmployeeNotFoundException("Employee of username " + username + " not found"));
        employeeRepository.delete(employee);
    }

    public Employee getEmployeeByUsername(String username){
        return employeeRepository.findEmployeeByUsername(username).orElseThrow(() ->
                new EmployeeNotFoundException("Employee of username " + username + " not found"));
    }

    boolean connect(@Valid AuthDTO auth){
        Employee employee = employeeRepository.findEmployeeByUsername(auth.getUsername()).orElseThrow(() ->
                new EmployeeNotFoundException("Employee of username " + auth.getUsername() + " not found"));
        if(auth.getPassword().equals(employee.getPassword())){
            return true;
        }
        else{
            throw new WrongPasswordException("The given password is not the correct one for the username "
                    + auth.getUsername());
        }
    }


}
