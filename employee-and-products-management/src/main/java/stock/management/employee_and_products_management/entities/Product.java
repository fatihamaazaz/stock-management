package stock.management.employee_and_products_management.entities;


import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Entity
@Data
public class Product {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    @NotBlank(message = "codeBar cannot be blank")
    @Column(unique = true)
    private String codeBar;
    private int quantity;

    public Product(String codeBar, int quantity){
        this.codeBar = codeBar;
        this.quantity = quantity;
    }
}
