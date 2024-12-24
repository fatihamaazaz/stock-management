package stock.management.employee_and_products_management.converter;

import stock.management.employee_and_products_management.entities.Role;
import stock.management.employee_and_products_management.exceptions.RoleNotFound;

import javax.management.relation.RoleNotFoundException;

public class RoleConverter {

    public static String convertRoleToString(Role role){
        return role.toString();
    }

    public static Role convertStringToRole(String role) {
        try {
            return Role.valueOf(role.toUpperCase());
        }
        catch (IllegalArgumentException e) {
            throw new RoleNotFound("role can only be STOCKCONTROLLERS, PURCHASERESPONSABLE, CASHIER or ADMIN");
        }
    }
}
