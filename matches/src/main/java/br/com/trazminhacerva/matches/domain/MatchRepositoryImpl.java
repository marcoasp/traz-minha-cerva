package br.com.trazminhacerva.matches.domain;

import com.mongodb.client.result.UpdateResult;
import lombok.RequiredArgsConstructor;
import org.springframework.data.mongodb.core.ReactiveMongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Mono;

/**
 * @author Marco Prado
 * @version 1.0 05/02/2021
 */
@Component
@RequiredArgsConstructor
public class MatchRepositoryImpl implements CustomMatchRepository {

    private final ReactiveMongoTemplate mongoTemplate;

    @Override
    public Mono<UpdateResult> updateStatusForSale(final String saleId) {
        Query criteria = Query.query(Criteria.where("saleId").is(saleId));
        Update update = Update.update("status", MatchStatus.EXPIRED.toString());
        return mongoTemplate.updateMulti(criteria, update, Match.class);
    }
}
