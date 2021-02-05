package br.com.trazminhacerva.matches.endpoint.dto;

import br.com.trazminhacerva.matches.domain.MatchStatus;
import lombok.Builder;
import lombok.Data;

import java.util.List;

/**
 * @author Marco Prado
 * @version 1.0 05/02/2021
 */
@Builder
@Data
public class MatchDTO {
    private String id;
    private String saleId;
    private String userId;
    private String name;
    private Double pricePerLiter;
    private List<String> tags;
    private double[] location;
    private MatchStatus status;
}
