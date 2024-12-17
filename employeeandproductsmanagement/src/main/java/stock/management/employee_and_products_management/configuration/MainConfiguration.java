package stock.management.employee_and_products_management.configuration;

import org.modelmapper.ModelMapper;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import stock.management.employee_and_products_management.mappers.EmployeeMapping;
import stock.management.employee_and_products_management.mappers.ProductMapping;

@Configuration
public class MainConfiguration {
    @Bean
    public ModelMapper modelMapper() {
        return new ModelMapper();
    }

    @Bean
    public EmployeeMapping employeeMapping(){
        return new EmployeeMapping(modelMapper());
    }

    @Bean
    public ProductMapping productMapping(){
        return new ProductMapping(modelMapper());
    }
}
