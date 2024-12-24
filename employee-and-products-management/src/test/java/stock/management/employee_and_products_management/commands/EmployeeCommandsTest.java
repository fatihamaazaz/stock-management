package stock.management.employee_and_products_management.commands;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.TestComponent;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import stock.management.employee_and_products_management.components.EmployeeService;
import stock.management.employee_and_products_management.dto.AuthDTO;
import stock.management.employee_and_products_management.dto.EmployeeDTO;
import stock.management.employee_and_products_management.entities.Employee;
import stock.management.employee_and_products_management.entities.Role;
import stock.management.employee_and_products_management.exceptions.EmployeeNotFoundException;
import stock.management.employee_and_products_management.exceptions.WrongPasswordException;
import stock.management.employee_and_products_management.repositories.EmployeeRepository;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@ExtendWith(SpringExtension.class)
@TestComponent
class EmployeeCommandsTest {

    @Autowired
    private EmployeeService employeeService;

    @Autowired
    private EmployeeCommands underTestCommands;

    @Autowired
    private EmployeeRepository employeeRepository;

    @Autowired
    private GeneralCommands generalCommands;

    private Employee employee;
    private EmployeeDTO employeeDTO;

    @BeforeEach
    void setUp(){
        employeeDTO = new EmployeeDTO("fm", "pass", "fa@gmail.com", "123");
        employee = employeeRepository.save(new Employee("fati", "password", "fat@gmail.com", "0255", Role.STOCKCONTROLLERS));
    }

    @AfterEach
    void tearUp(){
        employeeRepository.deleteAll();
    }

    @Test
    void shouldAddEmployeeOfRoleStockController(){
        String result = underTestCommands.addEmployee(Role.STOCKCONTROLLERS.toString(), employeeDTO.getUsername(), employeeDTO.getPassword(),
                employeeDTO.getEmail(), employeeDTO.getPhone());
        assertEquals("Success: employee has been added successfuly", result);
    }

    @Test
    void shouldReturnErrorMessageForRoleOtherThenStockControllerAndPurchaseResponsible(){
        String result = underTestCommands.addEmployee(Role.ADMIN.toString(), employeeDTO.getUsername(), employeeDTO.getPassword(),
                employeeDTO.getEmail(), employeeDTO.getPhone());
        assertEquals("Error: role should be STOCKCONTROLLERS or PURCHASERESPONSABLE", result);
    }

    @Test
    void shouldReturnErrorForInvalidEmployeeData(){
        String result = underTestCommands.addEmployee(Role.STOCKCONTROLLERS.toString(), "", employeeDTO.getPassword(),
                employeeDTO.getEmail(), employeeDTO.getPhone());
        assertTrue(result.contains("username cannot be blank"));
    }

    @Test
    void shouldReturnEmployeeData(){
        String result = underTestCommands.getEmployee(employee.getUsername());
        assertEquals(employee.toString(), result);
    }

    @Test
    void shouldReturnErrorForInexistantEmployee(){
        String wrongUsername = "fff";
        assertNotEquals(employee.getUsername(), wrongUsername);
        assertEquals(1, employeeRepository.findAll().size());
        String result = underTestCommands.getEmployee(wrongUsername);
        assertEquals(String.format("Error: Employee of username %s not found", wrongUsername), result);
    }

}