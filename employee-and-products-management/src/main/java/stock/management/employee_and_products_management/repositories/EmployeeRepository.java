package stock.management.employee_and_products_management.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import stock.management.employee_and_products_management.entities.Employee;

import java.util.Optional;

@Repository
public interface EmployeeRepository extends JpaRepository<Employee, Long> {

  Optional<Employee> findEmployeeByUsername(String username);
}