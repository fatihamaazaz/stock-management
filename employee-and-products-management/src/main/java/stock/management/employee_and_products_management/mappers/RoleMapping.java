package stock.management.employee_and_products_management.mappers;

import stock.management.employee_and_products_management.entities.Role;
import stock.management.employee_and_products_management.exceptions.RoleNotFound;

public class RoleMapping {
    public static Role toRole(String role){
        switch (role){
            case "PURCHASERESPONSABLE":
                return Role.PURCHASERESPONSABLE;
            case "STOCKCONTROLLERS":
                return Role.STOCKCONTROLLERS;
            default:
                throw new RoleNotFound("role can only be STOCKCONTROLLERS or PURCHASERESPONSABLE");
        }
    }
}
