package br.com.trazminhacerva.matcher.domain.user;

import org.springframework.data.mongodb.repository.MongoRepository;

/**
 * @author Marco Prado
 * @version 1.0 05/02/2021
 */
public interface UserRepository extends MongoRepository<User, String> {
}
