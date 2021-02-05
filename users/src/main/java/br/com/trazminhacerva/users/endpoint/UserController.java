package br.com.trazminhacerva.users.endpoint;

import br.com.trazminhacerva.users.domain.User;
import br.com.trazminhacerva.users.domain.UserRepository;
import br.com.trazminhacerva.users.endpoint.dto.InterestDTO;
import br.com.trazminhacerva.users.endpoint.dto.UserDTO;
import br.com.trazminhacerva.users.endpoint.exception.NotFoundException;
import br.com.trazminhacerva.users.endpoint.mapper.InterestMapper;
import br.com.trazminhacerva.users.endpoint.mapper.UserMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Mono;

import java.util.List;

/**
 * @author Marco Prado
 * @version 1.0 01/02/2021
 */
@RestController
@RequiredArgsConstructor
public class UserController {

    private final UserRepository userRepository;
    private final UserMapper userMapper;
    private final InterestMapper interestMapper;

    @PostMapping
    @ResponseStatus(code = HttpStatus.CREATED)
    public Mono<UserDTO> createUser(@RequestBody UserDTO newUserDTO) {
        return userRepository.save(User.from(newUserDTO.getName(), newUserDTO.getEmail(), newUserDTO.getLocation()))
                .map(userMapper::toDto);
    }

    @PutMapping
    public Mono<UserDTO> updateUser(@AuthenticationPrincipal Mono<Jwt> principal, @RequestBody UserDTO newUserDTO) {
        return
                principal.flatMap(jwt -> userRepository.findById(jwt.getClaimAsString("userId")))
                .switchIfEmpty(Mono.error(NotFoundException::new))
                .flatMap(u -> userRepository.save(u.update(newUserDTO.getName(), newUserDTO.getLocation())))
                .map(userMapper::toDto)
        ;
    }

    @PutMapping("/interest")
    public Mono<UserDTO> updateInterests(@AuthenticationPrincipal Mono<Jwt> principal, @RequestBody List<InterestDTO> interests) {
        return
                principal.flatMap(jwt -> userRepository.findById(jwt.getClaimAsString("userId")))
                        .switchIfEmpty(Mono.error(NotFoundException::new))
                        .flatMap(u -> userRepository.save(u.updateInterests(interestMapper.toEntity(interests))))
                        .map(userMapper::toDto)
                ;
    }
}
