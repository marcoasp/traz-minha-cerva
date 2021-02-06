package br.com.trazminhacerva.matcher.domain.sale;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.index.GeoSpatialIndexed;

import java.util.ArrayList;
import java.util.List;

/**
 * @author Marco Prado
 * @version 1.0 05/02/2021
 */
@Getter
@RequiredArgsConstructor
public class Sale {
    @Id
    private final String id;
    private final String name;
    private final Double pricePerLiter;
    private final List<String> tags = new ArrayList<>();
    @GeoSpatialIndexed
    private final double[] location;
    private final SaleStatus status;
}
