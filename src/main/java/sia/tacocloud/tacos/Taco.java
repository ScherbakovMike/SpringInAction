package sia.tacocloud.tacos;

import com.datastax.oss.driver.api.core.uuid.Uuids;
import lombok.Data;
import org.springframework.data.cassandra.core.cql.Ordering;
import org.springframework.data.cassandra.core.cql.PrimaryKeyType;
import org.springframework.data.cassandra.core.mapping.Column;
import org.springframework.data.cassandra.core.mapping.PrimaryKeyColumn;
import org.springframework.data.cassandra.core.mapping.Table;

import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.util.Date;
import java.util.List;
import java.util.UUID;

@Data
@Table("tacos")
public class Taco {

  @PrimaryKeyColumn(type = PrimaryKeyType.PARTITIONED)
  @GeneratedValue(strategy = GenerationType.AUTO)
  private UUID id = Uuids.timeBased();

  @PrimaryKeyColumn(type = PrimaryKeyType.CLUSTERED, ordering = Ordering.DESCENDING)
  private Date createdAt = new Date();

  @NotBlank
  @Size(min = 5, message = "Name must be at least 5 characters long")
  private String name;

  @NotNull
  @Size(min = 1, message = "You must choose at least 1 ingredient")
  @Column("ingredients")
  private List<IngredientUDT> ingredients;

  public void addIngredient(Ingredient ingredient) {
    this.ingredients.add(TacoUDRUtils.toIngredientUDT(ingredient));
  }

  private static class TacoUDRUtils {
    public static IngredientUDT toIngredientUDT(Ingredient ingredient) {
      return new IngredientUDT(ingredient.getName(), ingredient.getType());
    }
  }
}
