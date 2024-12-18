package stock.management.employee_and_products_management.components;

import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;
import org.springframework.validation.annotation.Validated;
import stock.management.employee_and_products_management.dto.ProductDTO;
import stock.management.employee_and_products_management.entities.Product;
import stock.management.employee_and_products_management.exceptions.ProductNotFoundException;
import stock.management.employee_and_products_management.mappers.ProductMapping;
import stock.management.employee_and_products_management.repositories.ProductRepository;

import java.util.List;

@Validated
@Component
public class ProductService {

    @Autowired
    private ProductRepository productRepository;

    @Autowired
    private ProductMapping productMapping;

    public Product addProduct(@Valid ProductDTO product){
        return productRepository.save(productMapping.mapToProduct(product));
    }

    public void deleteProductBycodeBar(String codeBar){
        Product product = productRepository.findProductByCodeBar(codeBar).orElseThrow(() ->
                new ProductNotFoundException("product of codeBar" + codeBar + "not found"));
        productRepository.delete(product);
    }

    public int getProductQuantityByCodeBar(String codeBar){
        Product product = productRepository.findProductByCodeBar(codeBar).orElseThrow(() ->
                new ProductNotFoundException("product of codeBar" + codeBar + " not found"));
        return product.getQuantity();
    }

    public List<Product> ListAllProducts(){
        return productRepository.findAll();
    }

    public Product updateproductQuantity(String codeBar, int newQuantity){
        Product product = productRepository.findProductByCodeBar(codeBar).orElseThrow(() ->
                new ProductNotFoundException("product of codeBar" + codeBar + "not found"));

        product.setQuantity(newQuantity);
        return productRepository.save(product);
    }

}
