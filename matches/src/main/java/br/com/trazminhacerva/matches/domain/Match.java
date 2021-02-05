package br.com.trazminhacerva.matches.domain;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import org.springframework.data.mongodb.core.index.GeoSpatialIndexed;

import java.util.ArrayList;
import java.util.List;

/**
 * @author Marco Prado
 * @version 1.0 05/02/2021
 */
@Getter
public class Match {
    private String id;
    private final String saleId;
    private final String userId;
    private final String name;
    private final Double pricePerLiter;
    private final List<String> tags = new ArrayList<>();
    @GeoSpatialIndexed
    private final double[] location;

    @Builder
    public Match(
            final String saleId,
            final String userId,
            final String name,
            final Double pricePerLiter,
            final List<String> tags,
            final double[] location) {
        this.saleId = saleId;
        this.userId = userId;
        this.name = name;
        this.tags.addAll(tags);
        this.pricePerLiter = pricePerLiter;
        this.location = location;
    }
}
