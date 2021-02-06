package br.com.trazminhacerva.matcher.domain.sale;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.index.GeoSpatialIndexed;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.ArrayList;
import java.util.List;

/**
 * @author Marco Prado
 * @version 1.0 05/02/2021
 */
@Getter
@RequiredArgsConstructor
@Document
public class Sale {
    private final String id;
    private final String name;
    private final Double pricePerLiter;
    private final List<String> tags;
    @GeoSpatialIndexed
    private final double[] location;
    private final SaleStatus status;
}
