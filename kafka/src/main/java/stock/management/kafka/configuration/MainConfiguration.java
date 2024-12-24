package stock.management.kafka.configuration;


import org.apache.kafka.clients.admin.NewTopic;
import org.springframework.beans.factory.annotation.Configurable;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.config.TopicBuilder;

@Configuration
public class MainConfiguration {

    //topics
    @Bean
    public NewTopic eventsTopic(){
        return TopicBuilder.name("events").partitions(3)
                .replicas(1)
                .build();
    }


}
