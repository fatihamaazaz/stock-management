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
    @NotBlank(message = "username cannot be blank")
    private String username;
    @NotBlank(message = "password cannot be blank")
    private String password;
    @NotBlank(message = "email cannot be blank")
    @Email(message = "email needs to be in a correct format: example@domain.ext")
    private String email;
    @NotBlank(message = "phone number cannot be blank")
    private String phone;
}
