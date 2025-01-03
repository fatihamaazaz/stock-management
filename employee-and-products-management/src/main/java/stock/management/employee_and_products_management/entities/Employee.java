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
    @NotBlank(message = "username cannot be blank")
    @Column(unique = true)
    private String username;
    @NotBlank(message = "password cannot be blank")
    private String password;
    @NotBlank(message = "email cannot be blank")
    @Email(message = "email needs to be in a correct format: example@domain.ext")
    @Column(unique = true)
    private String email;
    @NotBlank(message = "phone number cannot be blank")
    @Column(unique = true)
    private String phone;
    @Enumerated(EnumType.STRING)
    private Role role;

    public Employee(String username, String password, String email, String phone, Role role){
        this.username = username;
        this.password = password;
        this.email = email;
        this.phone = phone;
        this.role = role;
    }

    @Override
    public String toString(){
        return String.format("\n\t - username : %s \n\t - password : %s \n\t " +
                "- email : %s \n\t - phone : %s \n\t - role : %s \n", this.username, this.password, this.email,
                this.phone, this.role);
    }
}
