package stock.management.kafka.configuration;


import org.apache.kafka.clients.admin.NewTopic;
import org.springframework.beans.factory.annotation.Configurable;
import org.springframework.context.annotation.Bean;
import org.springframework.kafka.config.TopicBuilder;

@Configurable
public class MainConfiguration {

    //topics
    @Bean
    public NewTopic transactionsTopic(){
        return TopicBuilder.name("transactions").build();
    }

    @Bean
    public NewTopic pipelineTopic(){
        return TopicBuilder.name("pipeline").build();
    }

}
