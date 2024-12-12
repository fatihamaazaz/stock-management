package stock.management.employee_and_products_management.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class EmployeeDTO {
    @NotBlank
    private String username;
    @NotBlank
    private String password;
    @NotBlank
    @Email
    private String email;
    private int phone;
}
