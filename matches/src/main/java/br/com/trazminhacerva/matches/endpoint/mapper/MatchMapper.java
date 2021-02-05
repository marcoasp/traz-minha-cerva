package br.com.trazminhacerva.matches.endpoint.mapper;

import br.com.trazminhacerva.matches.domain.Match;
import br.com.trazminhacerva.matches.endpoint.dto.MatchDTO;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

import java.util.List;

/**
 * @author Marco Prado
 * @version 1.0 05/02/2021
 */
@Mapper(componentModel = "spring")
public interface MatchMapper {

    MatchMapper INSTANCE = Mappers.getMapper(MatchMapper.class);

    MatchDTO toDto(Match entity);
}
