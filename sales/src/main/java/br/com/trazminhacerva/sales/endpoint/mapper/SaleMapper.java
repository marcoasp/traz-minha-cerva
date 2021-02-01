package br.com.trazminhacerva.sales.endpoint.mapper;

import br.com.trazminhacerva.sales.domain.Sale;
import br.com.trazminhacerva.sales.endpoint.dto.SaleDTO;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

/**
 * @author Marco Prado
 * @version 1.0 28/01/2021
 */
@Mapper(componentModel = "spring")
public interface SaleMapper {

    SaleMapper INSTANCE = Mappers.getMapper(SaleMapper.class);

    SaleDTO toDto(Sale sale);
}
