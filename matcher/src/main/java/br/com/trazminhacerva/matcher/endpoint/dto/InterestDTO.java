package br.com.trazminhacerva.matcher.endpoint.dto;

import lombok.Builder;
import lombok.Data;

import java.util.List;

/**
 * @author Marco Prado
 * @version 1.0 05/02/2021
 */
@Builder
@Data
public class InterestDTO {
    private Double pricePerLiterFrom;
    private Double pricePerLiterTo;
    private String name;
    private double distance;
    private List<String> tags;
}
