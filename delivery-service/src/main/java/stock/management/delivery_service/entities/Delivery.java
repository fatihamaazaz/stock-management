package stock.management.delivery_service.entities;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Data
public class Delivery {
    private String employee;
    private String product;
    private int quantity;
}
