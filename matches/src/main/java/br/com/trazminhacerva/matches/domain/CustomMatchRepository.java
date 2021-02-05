package br.com.trazminhacerva.matches.domain;

import com.mongodb.client.result.UpdateResult;
import reactor.core.publisher.Mono;

/**
 * @author Marco Prado
 * @version 1.0 05/02/2021
 */
public interface CustomMatchRepository {

    Mono<UpdateResult> updateStatusForSale(String saleId);
}
