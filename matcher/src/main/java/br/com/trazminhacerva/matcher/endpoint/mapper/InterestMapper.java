package br.com.trazminhacerva.matcher.endpoint.mapper;

import br.com.trazminhacerva.matcher.domain.user.Interest;
import br.com.trazminhacerva.matcher.endpoint.dto.InterestDTO;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

import java.util.List;

/**
 * @author Marco Prado
 * @version 1.0 01/02/2021
 */
@Mapper(componentModel = "spring")
public interface InterestMapper {

    InterestMapper INSTANCE = Mappers.getMapper(InterestMapper.class);

    InterestDTO toDto(Interest interest);
    Interest toEntity(InterestDTO interest);
    List<Interest> toEntity(List<InterestDTO> interest);
}
