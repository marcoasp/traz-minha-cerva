package br.com.trazminhacerva.users.endpoint.mapper;

import br.com.trazminhacerva.users.domain.User;
import br.com.trazminhacerva.users.endpoint.dto.UserDTO;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

/**
 * @author Marco Prado
 * @version 1.0 01/02/2021
 */
@Mapper(componentModel = "spring")
public interface UserMapper {

    UserMapper INSTANCE = Mappers.getMapper(UserMapper.class);

    @Mapping(target = "email", source = "email.email")
    UserDTO toDto(User user);
}
