package br.com.trazminhacerva.matches.endpoint;

import br.com.trazminhacerva.matches.domain.MatchRepository;
import br.com.trazminhacerva.matches.endpoint.dto.MatchDTO;
import br.com.trazminhacerva.matches.endpoint.mapper.MatchMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

/**
 * @author Marco Prado
 * @version 1.0 05/02/2021
 */
@RestController
@RequiredArgsConstructor
public class MatchController {

    private final MatchRepository matchRepository;
    private final MatchMapper matchMapper;

    @GetMapping
    public Flux<MatchDTO> getUserMatches(@AuthenticationPrincipal Mono<Jwt> principal) {
        return principal
                .flatMapMany(jwt -> matchRepository.findByUserId(jwt.getClaimAsString("userId")))
                .map(matchMapper::toDto);
    }
}
