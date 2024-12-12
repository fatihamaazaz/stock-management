package stock.management.employee_and_products_management.entities;

import jakarta.validation.ConstraintViolation;
import jakarta.validation.Validation;
import jakarta.validation.Validator;
import jakarta.validation.ValidatorFactory;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.transaction.annotation.Transactional;
import stock.management.employee_and_products_management.repositories.EmployeeRepository;

import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;

@DataJpaTest
class EmployeeTest {

    private Validator validator;

    @Autowired
    private EmployeeRepository employeeRepository;

    @BeforeEach
    void setUp() {
        ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
        this.validator = factory.getValidator();
    }

    @Test
    void shouldReturnValidationErrors(){
        Employee employee = new Employee("", "", "f", 123, Role.PURCHASERESPONSABLE);
        Set<ConstraintViolation<Employee>> violations = validator.validate(employee);
        assertEquals(3, violations.size());
        assertTrue(violations.stream().anyMatch(v -> v.getMessage().contains("ne doit pas être vide")));
        assertTrue(violations.stream().anyMatch(v -> v.getMessage().contains("doit être une adresse électronique syntaxiquement correcte")));
    }


    @Test
    void shouldNotAllowSavingEmployeeWithDuplicatedUsername(){
        Employee employee1 = new Employee("fatiha", "fmpass", "f@gmail.com",
                1234, Role.ADMIN);
        employeeRepository.save(employee1);
        Employee employee2 = new Employee("fatiha", "fmpass", "fati@gmail.com",
                123, Role.ADMIN);
        assertThrows(DataIntegrityViolationException.class,() -> employeeRepository.saveAndFlush(employee2));
    }

}