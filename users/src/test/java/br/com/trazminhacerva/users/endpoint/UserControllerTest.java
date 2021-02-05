package br.com.trazminhacerva.users.endpoint;

import br.com.trazminhacerva.users.domain.User;
import br.com.trazminhacerva.users.domain.UserRepository;
import br.com.trazminhacerva.users.endpoint.dto.UserDTO;
import br.com.trazminhacerva.users.endpoint.mapper.UserMapper;
import br.com.trazminhacerva.users.utils.ReactiveMockitoAnswers;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.reactive.WebFluxTest;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Bean;
import org.springframework.http.MediaType;
import org.springframework.security.config.web.server.ServerHttpSecurity;
import org.springframework.security.oauth2.jwt.ReactiveJwtDecoder;
import org.springframework.security.test.web.reactive.server.SecurityMockServerConfigurers;
import org.springframework.security.web.server.SecurityWebFilterChain;
import org.springframework.test.web.reactive.server.WebTestClient;
import reactor.core.publisher.Mono;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;
import static org.springframework.security.config.Customizer.withDefaults;
import static org.springframework.security.test.web.reactive.server.SecurityMockServerConfigurers.csrf;
import static org.springframework.security.test.web.reactive.server.SecurityMockServerConfigurers.mockJwt;

/**
 * @author Marco Prado
 * @version 1.0 01/02/2021
 */
@WebFluxTest(UserController.class)
public class UserControllerTest {

    @Autowired
    private WebTestClient webTestClient;

    @MockBean
    private UserRepository userRepository;

    @Test
    public void shouldCreateUser() {
        given(userRepository.save(any(User.class)))
                .willAnswer(ReactiveMockitoAnswers::firstArgMono);

        UserDTO newUser = UserDTO.builder()
                .name("Marco")
                .email("marco@email.com")
                .location(new double[] {10.0, 20.0})
                .build();


        webTestClient
                .mutateWith(mockJwt().jwt(jwt -> jwt.claim("userId", "abcd")))
                .post().uri("/")
                .contentType(MediaType.APPLICATION_JSON)
                .body(Mono.just(newUser), UserDTO.class)
                .exchange()
                .expectStatus().isCreated()
                .expectBody(UserDTO.class).isEqualTo(newUser);

        verify(userRepository).save(any(User.class));
    }

    @Test
    public void shouldUpdateUser() {
        User currentUser = User.from("Marco", "marco@email.com", new double[]{10.0, 20.0});
        given(userRepository.findById("abcd")).willReturn(Mono.just(currentUser));
        given(userRepository.save(any(User.class)))
                .willAnswer(ReactiveMockitoAnswers::firstArgMono);

        UserDTO modifiedUser = UserDTO.builder()
                .name("Marco Antonio da Silva Prado")
                .email("marcoprado@email.com")
                .location(new double[] {20.0, 30.0})
                .build();

        UserDTO expectedUser = UserDTO.builder()
                .name("Marco Antonio da Silva Prado")
                .email("marco@email.com")
                .location(new double[] {20.0, 30.0})
                .build();


        webTestClient
                .mutateWith(mockJwt().jwt(jwt -> jwt.claim("userId", "abcd")))
                .put().uri("/")
                .contentType(MediaType.APPLICATION_JSON)
                .body(Mono.just(modifiedUser), UserDTO.class)
                .exchange()
                .expectStatus().isOk()
                .expectBody(UserDTO.class).isEqualTo(expectedUser);

        verify(userRepository).save(any(User.class));
    }

    @Test
    public void shouldReturnNotFoundOnUpdateUser() {
        given(userRepository.findById("abcd")).willReturn(Mono.empty());

        UserDTO modifiedUser = UserDTO.builder()
                .name("Marco Antonio da Silva Prado")
                .email("marcoprado@email.com")
                .location(new double[] {20.0, 30.0})
                .build();

        webTestClient
                .mutateWith(mockJwt().jwt(jwt -> jwt.claim("userId", "abcd")))
                .put().uri("/abcd")
                .contentType(MediaType.APPLICATION_JSON)
                .body(Mono.just(modifiedUser), UserDTO.class)
                .exchange()
                .expectStatus().isNotFound();

        verify(userRepository, never()).save(any(User.class));
    }

    @TestConfiguration
    static class Config {

        @Bean
        public UserMapper userMapper() {
            return UserMapper.INSTANCE;
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