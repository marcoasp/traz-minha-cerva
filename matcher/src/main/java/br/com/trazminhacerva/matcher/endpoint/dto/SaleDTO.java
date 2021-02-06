package br.com.trazminhacerva.matcher.endpoint.dto;

import br.com.trazminhacerva.matcher.domain.sale.SaleStatus;
import lombok.Builder;
import lombok.Data;

import java.util.List;

/**
 * @author Marco Prado
 * @version 1.0 05/02/2021
 */
@Data
@Builder
public class SaleDTO {
    private String id;
    private String name;
    private Double pricePerLiter;
    private List<String> tags;
    private double[] location;
    private SaleStatus status;
}
