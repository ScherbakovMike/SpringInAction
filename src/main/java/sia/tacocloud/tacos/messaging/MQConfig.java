package sia.tacocloud.tacos.messaging;

import org.apache.activemq.artemis.jms.client.ActiveMQQueue;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.jms.support.converter.MappingJackson2MessageConverter;
import sia.tacocloud.tacos.TacoOrder;

import javax.jms.Destination;
import java.util.HashMap;
import java.util.Map;

@Configuration
public class MQConfig {

  @Bean
  public Destination orderQueue() {
    return new ActiveMQQueue("tacocloud.order.queue");
  }

  @Bean
  public Destination tacoQueue() {
    return new ActiveMQQueue("tacocloud.taco.queue");
  }

  @Bean
  public MappingJackson2MessageConverter messageConverter() {

    var messageConverter = new MappingJackson2MessageConverter();
    messageConverter.setTypeIdPropertyName("_typeId");
    Map<String, Class<?>> typeIdMappings = new HashMap<>();
    typeIdMappings.put("order", TacoOrder.class);
    messageConverter.setTypeIdMappings(typeIdMappings);
    return messageConverter;
  }
}
