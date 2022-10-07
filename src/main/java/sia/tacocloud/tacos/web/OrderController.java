package sia.tacocloud.tacos.web;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.SessionAttributes;
import org.springframework.web.bind.support.SessionStatus;
import sia.tacocloud.tacos.TacoOrder;
import sia.tacocloud.tacos.data.OrderRepository;
import sia.tacocloud.tacos.messaging.OrderMessagingService;

import javax.validation.Valid;

@Controller
@RequestMapping("/orders")
@SessionAttributes("tacoOrder")
@RequiredArgsConstructor
public class OrderController {

  private final OrderRepository orderRepository;
  private final OrderMessagingService messageService;

  @GetMapping("/current")
  public String orderForm() {
    return "orderForm";
  }

  @PostMapping
  public String processOrder(
      @Valid TacoOrder order,
      Errors errors,
      SessionStatus sessionStatus) {
    if (errors.hasErrors()) {
      return "orderForm";
    }
    messageService.sendOrder(order);
    orderRepository.save(order);
    sessionStatus.setComplete();

    return "redirect:/";
  }
}
