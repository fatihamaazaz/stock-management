package stock.management.employee_and_products_management.mappers;

import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.modelmapper.convention.MatchingStrategies;
import stock.management.employee_and_products_management.entities.Product;

@RequiredArgsConstructor
public class ProductMapping {

    private final ModelMapper modelMapper;

    public <T> Product mapToProduct(T product){
        modelMapper.getConfiguration().setMatchingStrategy(MatchingStrategies.STRICT);
        return modelMapper.map(product, Product.class);
    }

    public <T> T mapFromProduct(Product product, Class<T> targetClass) {
        modelMapper.getConfiguration().setMatchingStrategy(MatchingStrategies.STRICT);
        return modelMapper.map(product, targetClass);
    }
}
