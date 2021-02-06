package br.com.trazminhacerva.matcher.endpoint;

import br.com.trazminhacerva.matcher.domain.sale.SaleRepository;
import br.com.trazminhacerva.matcher.endpoint.dto.SaleDTO;
import br.com.trazminhacerva.matcher.endpoint.mapper.SaleMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.function.Function;

/**
 * @author Marco Prado
 * @version 1.0 05/02/2021
 */
@Component("newSaleFunction")
@RequiredArgsConstructor
public class NewSaleFunction implements Function<SaleDTO, SaleDTO> {

    private final SaleRepository saleRepository;
    private final SaleMapper saleMapper;

    @Override
    public SaleDTO apply(final SaleDTO saleDto) {
        return saleMapper.toDto(saleRepository.save(saleMapper.toEntity(saleDto)));
    }
}
