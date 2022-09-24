package sia.tacocloud.tacos.data;

import org.springframework.asm.Type;
import org.springframework.jdbc.core.JdbcOperations;
import org.springframework.jdbc.core.PreparedStatementCreatorFactory;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import sia.tacocloud.tacos.Ingredient;
import sia.tacocloud.tacos.Taco;
import sia.tacocloud.tacos.TacoOrder;

import java.sql.Types;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

@Repository
public class JdbcOrderRepository implements OrderRepository {

  private final JdbcOperations jdbcOperations;

  public JdbcOrderRepository(JdbcOperations jdbcOperations) {
    this.jdbcOperations = jdbcOperations;
  }

  @Override
  @Transactional
  public TacoOrder save(TacoOrder order) {
    var pscf = new PreparedStatementCreatorFactory(
        "insert into Taco_Order "
        + "(delivery_name, delivery_street, delivery_city, "
        + "delivery_state, delivery_zip, cc_number, "
        + "cc_expiration, cc_cvv, placed_at) "
        + "values (?,?,?,?,?,?,?,?,?)",
        Types.VARCHAR,Types.VARCHAR,Types.VARCHAR,
        Types.VARCHAR,Types.VARCHAR,Types.VARCHAR,
        Types.VARCHAR,Types.VARCHAR,Types.TIMESTAMP
    );
    pscf.setReturnGeneratedKeys(true);

    order.setPlacedAt(new Date());
    var psc = pscf.newPreparedStatementCreator(
        Arrays.asList(
            order.getDeliveryName(),
            order.getDeliveryStreet(),
            order.getDeliveryCity(),
            order.getDeliveryState(),
            order.getDeliveryZip(),
            order.getCcNumber(),
            order.getCcExpiration(),
            order.getCcCVV(),
            order.getPlacedAt()));

    var keyHolder = new GeneratedKeyHolder();
    jdbcOperations.update(psc, keyHolder);
    var orderId = keyHolder.getKey().longValue();
    order.setId(orderId);

    var tacos = order.getTacos();
    var i = 0;
    for (var taco: tacos) {
      saveTaco(orderId, i++, taco);
    }

    return order;
  }

  private void saveTaco(long orderId, int orderKey, Taco taco) {
    taco.setCreatedAt(new Date());
    var pscf = new PreparedStatementCreatorFactory(
        "insert into Taco "
        + "(name, created_at, taco_order, taco_order_key) "
        + "values (?, ?, ?, ?)",
      Types.VARCHAR, Types.TIMESTAMP, Type.LONG, Type.LONG
    );
    pscf.setReturnGeneratedKeys(true);

    var psc = pscf.newPreparedStatementCreator(
        Arrays.asList(
            taco.getName(),
            taco.getCreatedAt(),
            orderId,
            orderKey
        )
    );

    var keyHolder = new GeneratedKeyHolder();
    jdbcOperations.update(psc, keyHolder);
    var tacoId = keyHolder.getKey().longValue();
    taco.setId(tacoId);

    saveIngredientRefs(tacoId, taco.getIngredients());
  }

  private void saveIngredientRefs(long tacoId, List<Ingredient> ingredients) {
    var key = 0;
    for (var ingredient: ingredients) {
      jdbcOperations.update(
        "insert into Ingredient_Ref (indgredient, taco, taco_key) "
        + "values (?, ?, ?)",
        ingredient.getId(), tacoId, key++
      );
    }
  }
}
