package stock.management.employee_and_products_management.components;


import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@EnableScheduling
@Component
public class ScheduledTask {

    @Scheduled(fixedRate = 5000)
    public void task() {

    }
}
