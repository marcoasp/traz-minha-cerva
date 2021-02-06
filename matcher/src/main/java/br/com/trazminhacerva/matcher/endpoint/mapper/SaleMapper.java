package br.com.trazminhacerva.matcher.endpoint.mapper;

import br.com.trazminhacerva.matcher.domain.sale.Sale;
import br.com.trazminhacerva.matcher.endpoint.dto.SaleDTO;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

/**
 * @author Marco Prado
 * @version 1.0 05/02/2021
 */
@Mapper(componentModel = "spring")
public interface SaleMapper {

    SaleMapper INSTANCE = Mappers.getMapper(SaleMapper.class);

    Sale toEntity(SaleDTO dto);
    SaleDTO toDto(Sale entity);
}
