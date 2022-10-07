package sia.tacocloud.tacos.messaging;

import sia.tacocloud.tacos.Taco;
import sia.tacocloud.tacos.TacoOrder;

public interface OrderMessagingService {
  void sendOrder(TacoOrder order);
  void sendTaco(Taco taco);
}
