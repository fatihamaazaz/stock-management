package stock.management.employee_and_products_management.mappers;

import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.modelmapper.convention.MatchingStrategies;
import stock.management.employee_and_products_management.entities.Employee;

@RequiredArgsConstructor
public class EmployeeMapping {

    private final ModelMapper modelMapper;

    public <T> Employee mapToEmployee(T employee){
        modelMapper.getConfiguration().setMatchingStrategy(MatchingStrategies.STRICT);
        return modelMapper.map(employee, Employee.class);
    }

    public <T> T mapFromEmployee(Employee employee, Class<T> targetClass) {
        modelMapper.getConfiguration().setMatchingStrategy(MatchingStrategies.STRICT);
        return modelMapper.map(employee, targetClass);
    }
}
