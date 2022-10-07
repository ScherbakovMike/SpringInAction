package sia.tacocloud.tacos.messaging.jms;

import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Profile;
import org.springframework.jms.annotation.JmsListener;
import org.springframework.stereotype.Component;
import sia.tacocloud.tacos.KitchenUI;
import sia.tacocloud.tacos.TacoOrder;

@Profile("jms-listener")
@RequiredArgsConstructor
@Component
public class OrderListener {
  private final KitchenUI ui;

  @JmsListener(destination = "tacocloud.order.queue")
  public void receiveOrder(TacoOrder order) {
    ui.displayOrder(order);
  }
}