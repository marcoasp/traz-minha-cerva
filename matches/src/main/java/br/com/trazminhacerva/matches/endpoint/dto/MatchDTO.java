package br.com.trazminhacerva.matches.endpoint.dto;

import lombok.Builder;
import lombok.Data;
import org.springframework.data.mongodb.core.index.GeoSpatialIndexed;

import java.util.ArrayList;
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
    private List<String> tags = new ArrayList<>();
    private double[] location;
}
