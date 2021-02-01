package br.com.trazminhacerva.sales.endpoint;

import br.com.trazminhacerva.sales.domain.Sale;
import br.com.trazminhacerva.sales.domain.SaleRepository;
import br.com.trazminhacerva.sales.domain.SaleStatus;
import br.com.trazminhacerva.sales.endpoint.dto.SaleDTO;
import br.com.trazminhacerva.sales.endpoint.mapper.SaleMapper;
import br.com.trazminhacerva.sales.utils.ReactiveMockitoAnswers;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.reactive.WebFluxTest;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Bean;
import org.springframework.http.MediaType;
import org.springframework.test.web.reactive.server.WebTestClient;
import reactor.core.publisher.Mono;

import java.util.Arrays;
import java.util.Collections;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;

/**
 * @author marprado - Marco Prado
 * @version 1.0 27/01/2021
 */
@WebFluxTest(SaleController.class)
public class SaleControllerTest {

    @Autowired
    WebTestClient webTestClient;

    @MockBean
    SaleRepository repository;

    @Test
    public void shouldCreateSale() {
        given(repository.save(any(Sale.class)))
                .willAnswer(ReactiveMockitoAnswers::firstArgMono);

        SaleDTO newSale = SaleDTO.builder()
                .name("Eisenbahn American IPA")
                .pricePerLiter(11.0)
                .location(new double[]{10.0, 20.0})
                .tags(Arrays.asList("IPA", "Eisenbahn", "Especial"))
                .build();

        SaleDTO expectedSale = SaleDTO.builder()
                .name("Eisenbahn American IPA")
                .pricePerLiter(11.0)
                .location(new double[]{10.0, 20.0})
                .tags(Arrays.asList("IPA", "Eisenbahn", "Especial"))
                .status(SaleStatus.VALID)
                .build();


        webTestClient.post().uri("/")
                .contentType(MediaType.APPLICATION_JSON)
                .body(Mono.just(newSale), SaleDTO.class)
                .exchange()
                .expectStatus().isCreated()
                .expectBody(SaleDTO.class).isEqualTo(expectedSale)
        ;

        verify(repository).save(any(Sale.class));
    }

    @Test
    public void shouldReturnNotFoundOnUpdateSaleStatus() {
        given(repository.findById("abcd"))
                .willReturn(Mono.empty());

        webTestClient.put().uri("/abcd/status/EXPIRED")
                .contentType(MediaType.APPLICATION_JSON)
                .exchange()
                .expectStatus().isNotFound();

        verify(repository, never()).save(any(Sale.class));
    }

    @Test
    public void shouldUpdateSaleStatus() {
        Sale sale = Sale.from("Heineken", 9.0, Collections.emptyList(), new double[]{10.0, 20.0});
        given(repository.findById("abcd"))
                .willReturn(Mono.just(sale));
        given(repository.save(any(Sale.class)))
                .willAnswer(ReactiveMockitoAnswers::firstArgMono);

        webTestClient.put().uri("/abcd/status/EXPIRED")
                .contentType(MediaType.APPLICATION_JSON)
                .exchange()
                .expectStatus().isOk()
                .expectBody().jsonPath("$.status").isEqualTo("EXPIRED");

        verify(repository).save(any(Sale.class));
    }

    @Test
    public void shouldDeleteSale() {
        Sale sale = Sale.from("Heineken", 9.0, Collections.emptyList(), new double[]{10.0, 20.0});
        given(repository.findById("abcd"))
                .willReturn(Mono.just(sale));
        given(repository.save(any(Sale.class)))
                .willAnswer(ReactiveMockitoAnswers::firstArgMono);

        webTestClient.delete().uri("/abcd")
                .exchange()
                .expectStatus().isNoContent()
        ;

        verify(repository).save(any(Sale.class));
    }

    @Test
    public void shouldReturnNotFoundOnDeleteSale() {
        given(repository.findById("abcd"))
                .willReturn(Mono.empty());

        webTestClient.delete().uri("/abcd")
                .exchange()
                .expectStatus().isNotFound();

        verify(repository, never()).save(any(Sale.class));
    }

    @TestConfiguration
    static class Config {
        @Bean
        public SaleMapper saleMapper() {
            return SaleMapper.INSTANCE;
        }
    }
}