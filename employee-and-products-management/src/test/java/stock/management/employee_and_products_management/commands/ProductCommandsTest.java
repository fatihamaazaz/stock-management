package stock.management.employee_and_products_management.commands;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.TestComponent;
import stock.management.employee_and_products_management.components.ProductService;
import stock.management.employee_and_products_management.dto.ProductDTO;
import stock.management.employee_and_products_management.entities.Product;
import stock.management.employee_and_products_management.repositories.ProductRepository;

import static org.junit.jupiter.api.Assertions.*;


@SpringBootTest
@TestComponent
class ProductCommandsTest {

    @Autowired
    private ProductCommands underTestCommands;

    @Autowired
    private ProductService productService;

    @Autowired
    private ProductRepository productRepository;

    private ProductDTO productDTO;
    private Product product;

    @BeforeEach
    void setUp(){
        productDTO = new ProductDTO("ha2155", 2);
        product = productRepository.save(new Product("RA1234", 3));
    }

    @AfterEach
    void tearDown(){
        productRepository.deleteAll();
    }


    @Test
    void shouldAddProduct(){
        assertEquals(1, productRepository.findAll().size());
        String result = underTestCommands.addProduct(productDTO.getCodeBar(), productDTO.getQuantity());
        assertEquals(2, productRepository.findAll().size());
        assertEquals("Success: product has been added successfuly", result);
    }

    @Test
    void shouldReturnErrorForInvalidProductDTOData(){
        ProductDTO product2 = new ProductDTO("", 6);
        String result = underTestCommands.addProduct(product2.getCodeBar(), product2.getQuantity());
        assertTrue(result.contains("codeBar cannot be blank"));
    }

    @Test
    void shouldReturnProductQuantity(){
         assertEquals(1, productRepository.findAll().size());
         String result = underTestCommands.getProductQuantity(product.getCodeBar());
         String expected = String.valueOf(product.getQuantity());
         assertEquals(expected, result);
    }

    @Test
    void shouldUpdateProductQuantity(){
        int newQuantity = 10;
        assertNotEquals(newQuantity, product.getQuantity());
        String result = underTestCommands.updateProductQuantity(product.getCodeBar(), newQuantity);
        assertEquals("Sucess: quantity has been updated successfuly", result);
        assertEquals(1, productRepository.findAll().size());
        assertEquals(newQuantity, productRepository.findProductByCodeBar(product.getCodeBar()).get().getQuantity());
    }

}