package reactor;

import org.junit.jupiter.api.Test;
import reactor.core.publisher.Flux;
import reactor.test.StepVerifier;
import reactor.util.function.Tuple2;

import java.time.Duration;

class ReactorTestsTest {

  @Test
  public void createFlux_just() {

    var characterFlux = Flux.just("1", "2", "3")
        .delayElements(Duration.ofMillis(500));

    var foodFlux = Flux.just("4", "5", "6")
        .delaySubscription(Duration.ofMillis(250))
        .delayElements(Duration.ofMillis(500));

    var mergedFlux = characterFlux.zipWith(foodFlux);

    StepVerifier.create(mergedFlux)
        .expectNextMatches(objects -> objects.getT1().equals("1") && objects.getT2().equals("4"))
        .expectNextMatches(objects -> objects.getT1().equals("2") && objects.getT2().equals("5"))
        .expectNextMatches(objects -> objects.getT1().equals("3") && objects.getT2().equals("6"))
        .verifyComplete();
  }

}