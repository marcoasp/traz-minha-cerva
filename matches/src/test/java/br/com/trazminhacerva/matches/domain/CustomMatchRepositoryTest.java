package br.com.trazminhacerva.matches.domain;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.data.mongo.DataMongoTest;
import org.testcontainers.containers.DockerComposeContainer;
import org.testcontainers.containers.wait.strategy.Wait;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;
import reactor.core.publisher.Flux;
import reactor.test.StepVerifier;

import java.io.File;
import java.time.Duration;
import java.util.Arrays;
import java.util.List;

/**
 * @author Marco Prado
 * @version 1.0 05/02/2021
 */
@DataMongoTest
@Testcontainers
public class CustomMatchRepositoryTest {

    @Autowired
    private MatchRepository repository;

    @Container
    public static DockerComposeContainer environment =
            new DockerComposeContainer(new File("src/test/resources/docker-compose.yml"))
                    .withExposedService("mongo", 27017,
                            Wait.forListeningPort().withStartupTimeout(Duration.ofSeconds(30)));

    @Test
    public void shouldUpdateStatus() {
        List<Match> currentMatches = Arrays.asList(Match.builder()
                        .userId("abcd")
                        .saleId("efg")
                        .name("Match 1")
                        .pricePerLiter(10.0)
                        .location(new double[]{10.0, 20.0})
                        .tags(Arrays.asList("Pilsen")).build(),
                Match.builder()
                        .userId("defg")
                        .saleId("efg")
                        .name("Match 2")
                        .pricePerLiter(10.0)
                        .location(new double[]{10.0, 20.0})
                        .tags(Arrays.asList("Pilsen")).build(),
                Match.builder()
                        .userId("abcd")
                        .saleId("defg")
                        .name("March 3")
                        .pricePerLiter(10.0)
                        .location(new double[]{10.0, 20.0})
                        .tags(Arrays.asList("Pilsen")).build()
        );
        Flux<Match> flux = repository.deleteAll()
                .log()
                .thenMany(repository.saveAll(currentMatches))
                .then(repository.updateStatusForSale("efg"))
                .log()
                .thenMany(repository.findAll());

        StepVerifier.create(flux)
                .expectSubscription()
                .expectNextMatches(m -> MatchStatus.EXPIRED.equals(m.getStatus()))
                .expectNextMatches(m -> MatchStatus.EXPIRED.equals(m.getStatus()))
                .expectNextMatches(m -> MatchStatus.ACTIVE.equals(m.getStatus()))
                .verifyComplete();
    }
}