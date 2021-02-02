package br.com.trazminhacerva.users.domain;

import org.springframework.data.mongodb.repository.ReactiveMongoRepository;

/**
 * @author Marco Prado
 * @version 1.0 01/02/2021
 */
public interface UserRepository extends ReactiveMongoRepository<User, String> {
}
