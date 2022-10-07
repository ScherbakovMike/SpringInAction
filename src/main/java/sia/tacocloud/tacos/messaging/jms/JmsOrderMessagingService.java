package sia.tacocloud.tacos.messaging.jms;

import lombok.RequiredArgsConstructor;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.stereotype.Service;
import sia.tacocloud.tacos.Taco;
import sia.tacocloud.tacos.TacoOrder;
import sia.tacocloud.tacos.messaging.OrderMessagingService;

import javax.jms.Destination;
import javax.jms.JMSException;
import javax.jms.Message;

@Service
@RequiredArgsConstructor
public class JmsOrderMessagingService implements OrderMessagingService {

  private final JmsTemplate jms;
  private final Destination tacoQueue;
  private final Destination orderQueue;

  @Override
  public void sendOrder(TacoOrder order) {
    jms.convertAndSend(order, this::addOrderSource);
  }

  private Message addOrderSource(Message message) throws JMSException {
    message.setStringProperty("X_ORDER_SOURCE", "WEB");
    return message;
  }

  @Override
  public void sendTaco(Taco taco) {
    jms.send(
        tacoQueue,
        session -> session.createObjectMessage(taco)
    );
  }
}
