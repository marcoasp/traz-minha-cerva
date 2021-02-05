package br.com.trazminhacerva.matches.endpoint;

import br.com.trazminhacerva.matches.domain.Match;
import br.com.trazminhacerva.matches.domain.MatchRepository;
import br.com.trazminhacerva.matches.endpoint.dto.MatchDTO;
import br.com.trazminhacerva.matches.endpoint.mapper.MatchMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Required;
import org.springframework.security.core.userdetails.User;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Flux;

import java.util.function.Consumer;

/**
 * @author Marco Prado
 * @version 1.0 05/02/2021
 */
@Component("newMatchConsumer")
@RequiredArgsConstructor
public class NewMatchConsumer implements Consumer<Flux<MatchDTO>> {

    private final MatchRepository matchRepository;
    private final MatchMapper mapper;

    @Override
    public void accept(final Flux<MatchDTO> matchFlux) {
        matchFlux
            .log()
            .map(mapper::toEntity)
            .switchMap(matchRepository::save)
            .subscribe();
    }
}
