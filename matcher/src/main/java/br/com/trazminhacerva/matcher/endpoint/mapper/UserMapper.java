package br.com.trazminhacerva.matcher.endpoint.mapper;

import br.com.trazminhacerva.matcher.domain.user.User;
import br.com.trazminhacerva.matcher.endpoint.dto.UserDTO;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

/**
 * @author Marco Prado
 * @version 1.0 01/02/2021
 */
@Mapper(componentModel = "spring")
public interface UserMapper {

    UserMapper INSTANCE = Mappers.getMapper(UserMapper.class);

    UserDTO toDto(User user);
    User toEntity(UserDTO userDTO);
}
