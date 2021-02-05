package br.com.trazminhacerva.matches.domain;

import org.springframework.data.mongodb.repository.ReactiveMongoRepository;
import reactor.core.publisher.Flux;

/**
 * @author Marco Prado
 * @version 1.0 05/02/2021
 */
public interface MatchRepository extends ReactiveMongoRepository<Match, String> {

    Flux<Match> findByUserId(String abcd);
}
