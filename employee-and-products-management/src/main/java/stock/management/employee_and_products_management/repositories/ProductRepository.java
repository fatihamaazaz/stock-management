package stock.management.employee_and_products_management.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import stock.management.employee_and_products_management.entities.Product;

import java.util.Optional;


@Repository
public interface ProductRepository extends JpaRepository<Product, Long> {

    Optional<Product> findProductByCodeBar(String codeBar);

    void deleteProductByCodeBar(String codeBar);
}