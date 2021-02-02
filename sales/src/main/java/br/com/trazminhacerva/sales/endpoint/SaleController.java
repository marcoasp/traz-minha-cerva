package br.com.trazminhacerva.sales.endpoint;

import br.com.trazminhacerva.sales.domain.Sale;
import br.com.trazminhacerva.sales.domain.SaleRepository;
import br.com.trazminhacerva.sales.domain.SaleStatus;
import br.com.trazminhacerva.sales.endpoint.dto.SaleDTO;
import br.com.trazminhacerva.sales.endpoint.exception.NotFoundException;
import br.com.trazminhacerva.sales.endpoint.mapper.SaleMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Mono;

/**
 * @author Marco Prado
 * @version 1.0 27/01/2021
 */
@RestController
@RequiredArgsConstructor
public class SaleController {

    private final SaleRepository repository;
    private final SaleMapper saleMapper;

    @PostMapping
    @ResponseStatus(code = HttpStatus.CREATED)
    public Mono<SaleDTO> create(@RequestBody SaleDTO newSaleDto) {
        return repository.save(Sale.from(newSaleDto.getName(), newSaleDto.getPricePerLiter(), newSaleDto.getTags(), newSaleDto.getLocation()))
                .map(saleMapper::toDto);
    }

    @PutMapping("/{id}/status/{newStatus}")
    public Mono<SaleDTO> changeStatus(@PathVariable String id, @PathVariable SaleStatus newStatus) {
        return repository.findById(id)
                .switchIfEmpty(Mono.error(NotFoundException::new))
                .flatMap(s -> repository.save(s.setStatus(newStatus)))
                .map(saleMapper::toDto);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(code = HttpStatus.NO_CONTENT)
    public Mono<SaleDTO> delete(@PathVariable String id) {
        return repository.findById(id)
                .switchIfEmpty(Mono.error(NotFoundException::new))
                .flatMap(s -> repository.save(s.deleted()))
                .map(saleMapper::toDto);
    }
}
