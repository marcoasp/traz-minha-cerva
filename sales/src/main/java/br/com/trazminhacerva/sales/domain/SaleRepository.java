package br.com.trazminhacerva.sales.domain;

import org.springframework.data.mongodb.repository.ReactiveMongoRepository;

/**
 * @author Marco Prado
 * @version 1.0 27/01/2021
 */
public interface SaleRepository extends ReactiveMongoRepository<Sale, String> {
}
