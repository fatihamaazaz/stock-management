package stock.management.employee_and_products_management.components;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import stock.management.employee_and_products_management.dto.ProductDTO;
import stock.management.employee_and_products_management.entities.Product;
import stock.management.employee_and_products_management.exceptions.ProductNotFoundException;
import stock.management.employee_and_products_management.repositories.ProductRepository;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class ProductServiceTest {

    @Autowired
    private ProductService underTestService;

    @Autowired
    private ProductRepository productRepository;

    private Product product;

    @BeforeEach
    void setUp() {
        product = productRepository.save(new Product("h22f", 2));
    }

    @AfterEach
    void tearDown() {
        productRepository.deleteAll();
    }

    @Test
    void shouldAddProduct() {
        productRepository.deleteAll();
        ProductDTO productDTO = new ProductDTO("uy550", 3);
        Product newProduct = underTestService.addProduct(productDTO);
        assertEquals(1, productRepository.findAll().size());
        assertEquals(productDTO.getCodeBar(), newProduct.getCodeBar());
        assertEquals(productDTO.getQuantity(), newProduct.getQuantity());
    }

    @Test
    void shouldDeleteExistingProductBycodeBar() {
        assertEquals(1, productRepository.findAll().size());
        underTestService.deleteProductBycodeBar(product.getCodeBar());
        assertTrue(productRepository.findAll().isEmpty());
    }

    @Test
    void shouldReturnErrorForNotDeletingUnexistingProduct() {
        String wrongCodeBar = "wrong123";
        assertEquals(1, productRepository.findAll().size());
        assertNotEquals(product.getCodeBar(), wrongCodeBar);
        assertThrows(ProductNotFoundException.class, () -> underTestService.deleteProductBycodeBar(wrongCodeBar));
    }

    @Test
    void shouldReturnProductQuantityByCodeBar() {
        assertEquals(product.getQuantity(), underTestService.getProductQuantityByCodeBar(product.getCodeBar()));
    }

    @Test
    void shouldListAllProducts() {
        List<Product> expectedResult = List.of(product);
        List<Product> result = underTestService.ListAllProducts();
        assertEquals(1, result.size());
        assertEquals(expectedResult, result);
    }

    @Test
    void shouldUpdateproductQuantity() {
        int newQuantity = 0;
        assertNotEquals(product.getQuantity(), newQuantity);
        Product updatedProduct = underTestService.updateproductQuantity(product.getCodeBar(), newQuantity);
        assertEquals(updatedProduct.getId(), product.getId());
        assertEquals(newQuantity, updatedProduct.getQuantity());
    }
}