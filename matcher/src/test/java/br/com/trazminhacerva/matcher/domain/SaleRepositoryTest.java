package br.com.trazminhacerva.matcher.domain;

import br.com.trazminhacerva.matcher.domain.sale.Sale;
import br.com.trazminhacerva.matcher.domain.sale.SaleRepository;
import br.com.trazminhacerva.matcher.domain.sale.SaleSearchCriteriaWrapper;
import br.com.trazminhacerva.matcher.domain.sale.SaleStatus;
import br.com.trazminhacerva.matcher.domain.sale.SearchCriteria;
import br.com.trazminhacerva.matcher.endpoint.dto.InterestDTO;
import br.com.trazminhacerva.matcher.endpoint.dto.UserDTO;
import br.com.trazminhacerva.matcher.infra.MongoIndexCreationListener;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.data.mongo.DataMongoTest;
import org.springframework.context.annotation.Import;
import org.testcontainers.containers.DockerComposeContainer;
import org.testcontainers.containers.wait.strategy.Wait;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;

import java.io.File;
import java.time.Duration;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import static java.util.stream.Collectors.toList;
import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.CoreMatchers.hasItems;
import static org.hamcrest.MatcherAssert.assertThat;

@DataMongoTest
@Testcontainers
@Import(MongoIndexCreationListener.class)
public class SaleRepositoryTest {

    @Autowired
    SaleRepository repository;

    @Container
    public static DockerComposeContainer environment =
            new DockerComposeContainer(new File("src/test/resources/docker-compose.yml"))
                    .withExposedService("mongo", 27017,
                            Wait.forListeningPort().withStartupTimeout(Duration.ofSeconds(30)));

    @BeforeEach
    public void setUp() {
        repository.deleteAll();
        Arrays.asList(
                new Sale("1",  "beer1", 5.0, Arrays.asList("tag1"), new double[]{-23.2192762, -45.8543312}, SaleStatus.VALID),
                new Sale("2",  "beer2",  10.0, Collections.emptyList(), new double[]{-23.2192762, -45.8543312}, SaleStatus.VALID),
                new Sale("3",  "beer3",  20.0, Collections.emptyList(), new double[]{-23.2219481, -45.8526897}, SaleStatus.VALID),
                new Sale("4",  "beer4",  20.0, Collections.emptyList(), new double[]{-23.2219481, -45.8526897}, SaleStatus.VALID),
                new Sale("5",  "beer5",  20.0, Collections.emptyList(), new double[]{-90.0, -90.0}, SaleStatus.VALID)
        ).forEach(repository::save);
    }

    @Test
    public void shouldNotConsideredAlreadyExistingMatches() {
        UserDTO user = UserDTO.builder()
                .location(new double[]{-23.2023046, -45.8639857})
                .interests(Arrays.asList(InterestDTO.builder().distance(10).build())).build();

        SaleSearchCriteriaWrapper criteria = SaleSearchCriteriaWrapper.from(user);

        List<Sale> page = repository.findBy(criteria);

        assertThat(page.size(), equalTo(4));
        assertThat(page.stream().map(Sale::getName).collect(toList()), hasItems("beer1", "beer2", "beer3", "beer4"));
    }


    @Test
    public void shouldSearchByTagOnly() {
        UserDTO user = UserDTO.builder().location(new double[]{-23.2023046, -45.8639857})
                .interests(Arrays.asList(InterestDTO.builder().distance(10).tags(Arrays.asList("tag1")).build())).build();
        SaleSearchCriteriaWrapper criteria = SaleSearchCriteriaWrapper.from(user);
        List<Sale> sales = repository.findBy(criteria);

        assertThat(sales.size(), equalTo(1));
        assertThat(sales.get(0).getTags(), hasItems("tag1"));
    }

    @Test
    public void shouldSearchByPriceOnly() {
        UserDTO user = UserDTO.builder().location(new double[]{-23.2023046, -45.8639857})
                .interests(Arrays.asList(InterestDTO.builder().distance(10).pricePerLiterTo(10.0).build())).build();
        SaleSearchCriteriaWrapper criteria = SaleSearchCriteriaWrapper.from(user);
        List<Sale> sales = repository.findBy(criteria);

        assertThat(sales.size(), equalTo(2));
        assertThat(sales.get(0).getPricePerLiter(), equalTo(5.0));
        assertThat(sales.get(1).getPricePerLiter(), equalTo(10.0));
    }

    @Test
    public void shouldSearchByTagAndPrice() {
        UserDTO user = UserDTO.builder().location(new double[]{-23.2023046, -45.8639857})
                .interests(Arrays.asList(InterestDTO.builder().distance(10).pricePerLiterTo(10.0).tags(Arrays.asList("tag1")).build())).build();
        SaleSearchCriteriaWrapper criteria = SaleSearchCriteriaWrapper.from(user);
        List<Sale> sales = repository.findBy(criteria);

        assertThat(sales.size(), equalTo(1));
        assertThat(sales.get(0).getPricePerLiter(), equalTo(5.0));
    }
}