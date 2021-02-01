package br.com.trazminhacerva.sales.endpoint.dto;

import br.com.trazminhacerva.sales.domain.SaleStatus;
import lombok.Builder;
import lombok.Data;

import java.util.List;

/**
 * @author Marco Prado
 * @version 1.0 28/01/2021
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
