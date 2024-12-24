package stock.management.employee_and_products_management.components;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;
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
class EmployeeServiceTest {

    @Autowired
    private EmployeeService underTestService;

    @Autowired
    private EmployeeRepository employeeRepository;

    private Employee employee;

    @BeforeEach
    void setUp() {
        employee = employeeRepository.save(new Employee("fatiha", "password",
                "f@gmail.com", "1234", Role.STOCKCONTROLLERS));
    }

    @AfterEach
    void tearDown() {
        employeeRepository.deleteAll();
    }

    @Test
    void shouldAddStockController() {
        employeeRepository.deleteAll();
        assertEquals(0, employeeRepository.findAll().size());
        EmployeeDTO newEmployee = new EmployeeDTO("fatiha", "password",
                "f@gmail.com", "1234");
        underTestService.addStockController(newEmployee);
        assertEquals(1, employeeRepository.findAll().size());
        assertEquals(Role.STOCKCONTROLLERS, employeeRepository.findAll().getFirst().getRole());
    }

    @Test
    void shouldAddPurchaseResponsible() {
        employeeRepository.deleteAll();
        assertEquals(0, employeeRepository.findAll().size());
        EmployeeDTO newEmployee = new EmployeeDTO("fatiha", "password",
                "f@gmail.com", "1234");
        underTestService.addPurchaseResponsible(newEmployee);
        assertEquals(1, employeeRepository.findAll().size());
        assertEquals(Role.PURCHASERESPONSABLE, employeeRepository.findAll().getFirst().getRole());
    }

    @Test
    void shouldDeleteExistingEmployeeByUsername() {
        assertEquals(1, employeeRepository.findAll().size());
        underTestService.deleteEmployeeByUsername(employee.getUsername());
        assertTrue(employeeRepository.findAll().isEmpty());
    }

    @Test
    void shouldReturnErrorForDeletingInexistingEmployeeUsername() {
        assertEquals(1, employeeRepository.findAll().size());
        String userName = "helloWorld";
        assertNotEquals(employee.getUsername(), userName);
        assertThrows(EmployeeNotFoundException.class, () -> underTestService.deleteEmployeeByUsername(userName));
    }

    @Test
    void shouldConnectForCorrectCredentials() {
        AuthDTO credentials = new AuthDTO(employee.getUsername(), employee.getPassword());
        assertTrue(underTestService.connect(credentials, false));
    }

    @Test
    void shouldReturnWrongPasswordExceptionForTryingToConnectWithWrongPassword() {
        String wrongPassword = "wrongPass";
        assertNotEquals(employee.getPassword(), wrongPassword);
        AuthDTO credentials = new AuthDTO(employee.getUsername(), wrongPassword);
        assertThrows(WrongPasswordException.class, () -> underTestService.connect(credentials, false));
    }

}