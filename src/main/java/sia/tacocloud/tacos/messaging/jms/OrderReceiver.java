package sia.tacocloud.tacos.messaging.jms;

import sia.tacocloud.tacos.TacoOrder;

import javax.jms.JMSException;

public interface OrderReceiver {

  TacoOrder receiveOrder() throws JMSException;
}
