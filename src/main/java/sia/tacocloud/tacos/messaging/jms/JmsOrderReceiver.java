package sia.tacocloud.tacos.messaging.jms;

import lombok.RequiredArgsConstructor;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.jms.support.converter.MessageConverter;
import org.springframework.stereotype.Component;
import sia.tacocloud.tacos.TacoOrder;

import javax.jms.JMSException;

@Component
@RequiredArgsConstructor
public class JmsOrderReceiver implements OrderReceiver {

  private final JmsTemplate jms;
  private final MessageConverter converter;

  @Override
  public TacoOrder receiveOrder() throws JMSException {
    var message = jms.receive("tacocloud.order.queue");
    return (TacoOrder) converter.fromMessage(message);
  }
}
