package br.com.trazminhacerva.matches.endpoint;

import br.com.trazminhacerva.matches.domain.MatchRepository;
import br.com.trazminhacerva.matches.endpoint.dto.MatchDTO;
import br.com.trazminhacerva.matches.endpoint.mapper.MatchMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Flux;

import java.util.function.Consumer;

/**
 * @author Marco Prado
 * @version 1.0 05/02/2021
 */
@Component("expiredSaleConsumer")
@RequiredArgsConstructor
@Slf4j
public class ExpiredSaleConsumer implements Consumer<Flux<String>> {

    private final MatchRepository matchRepository;

    @Override
    public void accept(final Flux<String> saleIdFlux) {
        saleIdFlux
                .log()
                .switchMap(matchRepository::updateStatusForSale)
                .subscribe(r -> log.info("Updated {} matches", r.getMatchedCount()));
    }
}
