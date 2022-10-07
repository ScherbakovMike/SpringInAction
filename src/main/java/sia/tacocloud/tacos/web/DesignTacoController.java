package sia.tacocloud.tacos.web;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.SessionAttributes;
import sia.tacocloud.tacos.Ingredient;
import sia.tacocloud.tacos.Ingredient.Type;
import sia.tacocloud.tacos.Taco;
import sia.tacocloud.tacos.TacoOrder;
import sia.tacocloud.tacos.data.IngredientRepository;
import sia.tacocloud.tacos.messaging.OrderMessagingService;

import javax.validation.Valid;
import java.util.List;

@Slf4j
@Controller
@RequestMapping("/design")
@SessionAttributes("tacoOrder")
@RequiredArgsConstructor
public class DesignTacoController {

  private final IngredientRepository ingredientRepository;
  private final OrderMessagingService messageService;

  @ModelAttribute
  public void addIngredientsToModel(Model model) {
    var ingredients = ingredientRepository.findAll();

    var types = Ingredient.Type.values();
    for (var type : types) {
      model.addAttribute(type.toString().toLowerCase(),
          filterByType((List<Ingredient>) ingredients, type));
    }
  }

  @ModelAttribute(name = "tacoOrder")
  public TacoOrder order() {
    return new TacoOrder();
  }

  @ModelAttribute(name = "taco")
  public Taco taco() {
    return new Taco();
  }

  @GetMapping
  public String showDesignForm() {
    return "design";
  }

  @PostMapping
  public String processTaco(
      @Valid Taco taco,
      Errors errors,
      @ModelAttribute TacoOrder tacoOrder,
      @AuthenticationPrincipal UserDetails userDetails
  ) {
    System.out.println(userDetails);
    if(errors.hasErrors()) {
      return "design";
    }

    messageService.sendTaco(taco);
    tacoOrder.addTaco(taco);
    log.info("Processing taco: {}", taco);

    return "redirect:/orders/current";
  }

  private Iterable<Ingredient> filterByType(List<Ingredient> ingredients, Type type) {
    return ingredients
        .stream()
        .filter(x -> x.getType().equals(type))
        .toList();
  }
}
