package br.com.trazminhacerva.sales.domain;

import lombok.Getter;
import lombok.Setter;
import lombok.experimental.Accessors;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.index.GeoSpatialIndexed;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * @author Marco Prado
 * @version 1.0 27/01/2021
 */
@Getter
@Accessors(chain = true)
public class Sale {

    @Id
    private String id;
    private final String name;
    private final Double pricePerLiter;
    private final List<String> tags = new ArrayList<>();
    @GeoSpatialIndexed
    private final double[] location;
    @Setter
    private SaleStatus status;

    public Sale(final String name, final Double pricePerLiter, final List<String> tags, final double[] location, final SaleStatus status) {
        this.name = name;
        this.pricePerLiter = pricePerLiter;
        this.tags.addAll(tags);
        this.location = location;
        this.status = status;
    }

    public static Sale from(String name, Double pricePerLiter, List<String> tags, double[] location) {
        return new Sale(name, pricePerLiter, new ArrayList<>(tags), location, SaleStatus.VALID);
    }

    public List<String> getTags() {
        return Collections.unmodifiableList(tags);
    }

    public Sale deleted() {
        this.status = SaleStatus.DELETED;
        return this;
    }
}
