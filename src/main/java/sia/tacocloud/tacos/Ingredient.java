package sia.tacocloud.tacos;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.io.Serializable;

@Data
@Document(collection = "ingredient")
@RequiredArgsConstructor
@AllArgsConstructor
//@NoArgsConstructor(access = AccessLevel.PUBLIC, force = true)
public class Ingredient implements Serializable {

  @Id
  private String id;
  private String name;
  private Type type;

  public enum Type {
    WRAP, PROTEIN, VEGGIES, CHEESE, SAUCE
  }
}
