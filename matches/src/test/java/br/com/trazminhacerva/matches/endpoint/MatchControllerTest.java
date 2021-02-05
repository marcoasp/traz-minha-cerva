package br.com.trazminhacerva.matches.endpoint;

import br.com.trazminhacerva.matches.domain.Match;
import br.com.trazminhacerva.matches.domain.MatchRepository;
import br.com.trazminhacerva.matches.endpoint.dto.MatchDTO;
import br.com.trazminhacerva.matches.endpoint.mapper.MatchMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.reactive.WebFluxTest;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Bean;
import org.springframework.security.config.web.server.ServerHttpSecurity;
import org.springframework.security.web.server.SecurityWebFilterChain;
import org.springframework.test.web.reactive.server.WebTestClient;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.verify;
import static org.springframework.security.config.Customizer.withDefaults;
import static org.springframework.security.test.web.reactive.server.SecurityMockServerConfigurers.mockJwt;

/**
 * @author Marco Prado
 * @version 1.0 05/02/2021
 */

@WebFluxTest(MatchController.class)
public class MatchControllerTest {

    @Autowired
    private WebTestClient webTestClient;

    @MockBean
    private MatchRepository matchRepository;

    @Test
    public void shouldReturnUserMatches() {
        List<Match> matches = Arrays.asList(
                Match.builder()
                        .userId("abcd")
                        .saleId("efg")
                        .name("Skol")
                        .pricePerLiter(10.0)
                        .location(new double[]{10.0, 20.0})
                        .tags(Arrays.asList("Pilsen")).build(),
                Match.builder()
                        .userId("abcd")
                        .saleId("hij")
                        .name("Apia")
                        .pricePerLiter(20.0)
                        .location(new double[]{10.0, 20.0})
                        .tags(Arrays.asList("IPA")).build()
        );
        given(matchRepository.findByUserId("abcd")).willReturn(Flux.fromIterable(matches));

        final List<MatchDTO> expectedMatches = Arrays.asList(
                MatchDTO.builder()
                        .userId("abcd")
                        .saleId("efg")
                        .name("Skol")
                        .pricePerLiter(10.0)
                        .location(new double[]{10.0, 20.0})
                        .tags(Arrays.asList("Pilsen")).build(),
                MatchDTO.builder()
                        .userId("abcd")
                        .saleId("hij")
                        .name("Apia")
                        .pricePerLiter(20.0)
                        .location(new double[]{10.0, 20.0})
                        .tags(Arrays.asList("IPA")).build()
        );

        webTestClient
                .mutateWith(mockJwt().jwt(jwt -> jwt.claim("userId", "abcd")))
                .get()
                .uri("/")
                .exchange()
                .expectStatus().isOk()
                .expectBodyList(MatchDTO.class)
                .isEqualTo(expectedMatches);

        verify(matchRepository).findByUserId(eq("abcd"));
    }

    @TestConfiguration
    static class Config {

        @Bean
        public MatchMapper matchMapper() {
            return MatchMapper.INSTANCE;
        }

        @Bean
        SecurityWebFilterChain springSecurityFilterChain(ServerHttpSecurity http) {
            http
                    .authorizeExchange(exchanges -> exchanges
                            .anyExchange().authenticated()
                    )
                    .oauth2ResourceServer(oauth2 -> oauth2
                            .jwt(withDefaults())
                    ).csrf().disable();
            return http.build();
        }
    }

}