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
import stock.management.employee_and_products_management.repositories.EmployeeRepository;
import stock.management.employee_and_products_management.repositories.ProductRepository;

import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;

@DataJpaTest
class ProductTest {

    private Validator validator;

    @Autowired
    private ProductRepository productRepository;

    @BeforeEach
    void setUp() {
        ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
        this.validator = factory.getValidator();
    }

    @Test
    void shouldReturnValidationErrorForBlankCodeBar(){
        Product product = new Product("", 0);
        Set<ConstraintViolation<Product>> violations = validator.validate(product);
        assertEquals(1, violations.size());
        assertTrue(violations.stream().anyMatch(v -> v.getMessage().contains("codeBar cannot be blank")));
    }

    @Test
    void shouldReturnDataViolationErrorForAddingDuplicatedCodeBar(){
        Product product1 = new Product("h223", 3);
        productRepository.saveAndFlush(product1);
        Product product2 = new Product("h223", 3);
        assertThrows(DataIntegrityViolationException.class, () -> productRepository.saveAndFlush(product2));
    }

}