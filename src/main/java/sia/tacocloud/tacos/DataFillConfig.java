package sia.tacocloud.tacos;

import org.springframework.boot.ApplicationRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import sia.tacocloud.tacos.Ingredient.Type;
import sia.tacocloud.tacos.data.IngredientRepository;
import sia.tacocloud.tacos.data.TacoRepository;

import java.util.List;

@Configuration
public class DataFillConfig {
  @Bean
  public ApplicationRunner dataLoader(IngredientRepository ingredientRepo, TacoRepository tacoRepo) {
    return args -> {
      var ingredients = List.of(
          new Ingredient("FLTO", "Flour Tortilla", Type.WRAP),
          new Ingredient("COTO", "Corn Tortilla", Type.WRAP),
          new Ingredient("GRBF", "Ground Beef", Type.PROTEIN),
          new Ingredient("TMTO", "Diced Tomatoes", Type.VEGGIES),
          new Ingredient("CARN", "Carnitas", Type.PROTEIN),
          new Ingredient("TMTO", "Diced Tomatoes", Type.VEGGIES),
          new Ingredient("LETC", "Lettuce", Type.VEGGIES),
          new Ingredient("CHED", "Cheddar", Type.CHEESE),
          new Ingredient("JACK", "Monterrey Jack", Type.CHEESE),
          new Ingredient("SLSA", "Salsa", Type.SAUCE),
          new Ingredient("SRCR", "Sour Cream", Type.SAUCE)
      );

      ingredientRepo.saveAll(ingredients);

      tacoRepo.save(new Taco("Super taco", ingredients));
      tacoRepo.save(new Taco("Mega taco", ingredients));
      tacoRepo.save(new Taco("Extra taco", ingredients));
    };
  }

}
