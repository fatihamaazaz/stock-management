package stock.management.employee_and_products_management.entities;


import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.*;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Entity
public class Employee {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    @NotBlank
    @Column(unique = true)
    private String username;
    @NotBlank
    private String password;
    @NotBlank
    @Email
    @Column(unique = true)
    private String email;
    @Column(unique = true)
    private int phone;
    @Enumerated(EnumType.STRING)
    private Role role;

    public Employee(String username, String password, String email, int phone, Role role){
        this.username = username;
        this.password = password;
        this.email = email;
        this.phone = phone;
        this.role = role;
    }
}
