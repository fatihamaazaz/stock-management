package stock.management.employee_and_products_management;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.scheduling.annotation.EnableScheduling;

@EnableScheduling
@SpringBootApplication
public class EmployeeAndProductsManagementApplication {

	public static void main(String[] args) {
		SpringApplication.run(EmployeeAndProductsManagementApplication.class, args);
	}

}
