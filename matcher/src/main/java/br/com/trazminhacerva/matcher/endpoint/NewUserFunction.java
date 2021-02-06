package br.com.trazminhacerva.matcher.endpoint;

import br.com.trazminhacerva.matcher.domain.user.UserRepository;
import br.com.trazminhacerva.matcher.endpoint.dto.UserDTO;
import br.com.trazminhacerva.matcher.endpoint.mapper.UserMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.function.Function;

/**
 * @author Marco Prado
 * @version 1.0 05/02/2021
 */
@Component("newUserFunction")
@RequiredArgsConstructor
public class NewUserFunction implements Function<UserDTO, UserDTO> {

    private final UserRepository userRepository;
    private final UserMapper userMapper;

    @Override
    public UserDTO apply(final UserDTO userDTO) {
        return userMapper.toDto(userRepository.save(userMapper.toEntity(userDTO)));
    }
}
