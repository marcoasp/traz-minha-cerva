package br.com.trazminhacerva.users.endpoint;

import br.com.trazminhacerva.users.domain.User;
import br.com.trazminhacerva.users.domain.UserRepository;
import br.com.trazminhacerva.users.endpoint.dto.UserDTO;
import br.com.trazminhacerva.users.endpoint.exception.NotFoundException;
import br.com.trazminhacerva.users.endpoint.mapper.UserMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Mono;

/**
 * @author Marco Prado
 * @version 1.0 01/02/2021
 */
@RestController
@RequiredArgsConstructor
public class UserController {

    private final UserRepository userRepository;
    private final UserMapper userMapper;

    @PostMapping
    @ResponseStatus(code = HttpStatus.CREATED)
    public Mono<UserDTO> createUser(@RequestBody UserDTO newUserDTO) {
        return userRepository.save(User.from(newUserDTO.getName(), newUserDTO.getEmail(), newUserDTO.getLocation()))
                .map(userMapper::toDto);
    }

    @PutMapping("/{userId}")
    public Mono<UserDTO> updateUser(@PathVariable("userId") String id, @RequestBody UserDTO newUserDTO) {
        return userRepository.findById(id)
                .switchIfEmpty(Mono.error(NotFoundException::new))
                .flatMap(u -> userRepository.save(u.update(newUserDTO.getName(), newUserDTO.getLocation())))
                .map(userMapper::toDto)
        ;
    }
}
