package sia.tacocloud.tacos.web.api;

import lombok.RequiredArgsConstructor;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import sia.tacocloud.tacos.TacoOrder;
import sia.tacocloud.tacos.data.OrderRepository;
import sia.tacocloud.tacos.messaging.OrderMessagingService;

@RestController
@RequestMapping(path = "/api/orders",
    produces = "application/json")
@RequiredArgsConstructor
@CrossOrigin(origins = "http://localhost:8080")
public class OrderApiController {

  private final OrderRepository repo;
  private final OrderMessagingService messageService;

  @PostMapping(consumes = "application/json")
  @ResponseStatus(HttpStatus.CREATED)
  public TacoOrder postOrder(@RequestBody TacoOrder order) {
    messageService.sendOrder(order);
    return repo.save(order);
  }

  @PutMapping(path = "/{orderId}", consumes = "application/json")
  public TacoOrder putOrder(
      @PathVariable("orderId") String orderId,
      @RequestBody TacoOrder order) {
    order.setId(orderId);
    return repo.save(order);
  }

  @PatchMapping(path = "/{orderId}", consumes = "application/json")
  public TacoOrder patchOrder(@PathVariable("orderId") String orderId,
                              @RequestBody TacoOrder patch) {
    TacoOrder order = repo.findById(orderId).get();
    if (patch.getDeliveryName() != null) {
      order.setDeliveryName(patch.getDeliveryName());
    }
    if (patch.getDeliveryStreet() != null) {
      order.setDeliveryStreet(patch.getDeliveryStreet());
    }
    if (patch.getDeliveryCity() != null) {
      order.setDeliveryCity(patch.getDeliveryCity());
    }
    if (patch.getDeliveryState() != null) {
      order.setDeliveryState(patch.getDeliveryState());
    }
    if (patch.getDeliveryZip() != null) {
      order.setDeliveryZip(patch.getDeliveryZip());
    }
    if (patch.getCcNumber() != null) {
      order.setCcNumber(patch.getCcNumber());
    }
    if (patch.getCcExpiration() != null) {
      order.setCcExpiration(patch.getCcExpiration());
    }
    if (patch.getCcCVV() != null) {
      order.setCcCVV(patch.getCcCVV());
    }
    return repo.save(order);
  }

  @DeleteMapping("/{orderId}")
  @ResponseStatus(HttpStatus.NO_CONTENT)
  public void deleteOrder(@PathVariable("orderId") String orderId) {
    try {
      repo.deleteById(orderId);
    } catch (EmptyResultDataAccessException e) {
    }
  }
}
