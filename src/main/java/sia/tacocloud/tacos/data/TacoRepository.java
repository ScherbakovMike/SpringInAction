package sia.tacocloud.tacos.data;

import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;
import sia.tacocloud.tacos.Taco;

@Repository
public interface TacoRepository extends PagingAndSortingRepository<Taco, String> {
}
