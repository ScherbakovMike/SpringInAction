package sia.tacocloud.tacos;

import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.rest.core.annotation.RestResource;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.io.Serializable;
import java.util.Date;
import java.util.List;

@Data
@NoArgsConstructor
@RestResource(rel="tacos", path="tacos")
public class Taco implements Serializable {

  @Id
  String id;

  public Taco(String name, List<Ingredient> ingredients) {
    this.name = name;
    this.ingredients = ingredients;
  }

  private Date createdAt = new Date();

  @NotBlank
  @Size(min = 5, message = "Name must be at least 5 characters long")
  private String name;

  @NotNull
  @Size(min = 1, message = "You must choose at least 1 ingredient")
  private List<Ingredient> ingredients;

  public void addIngredient(Ingredient ingredient) {
    this.ingredients.add(ingredient);
  }
}
