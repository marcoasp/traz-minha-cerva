package br.com.trazminhacerva.matcher.domain.sale;

import br.com.trazminhacerva.matcher.endpoint.dto.InterestDTO;
import br.com.trazminhacerva.matcher.endpoint.dto.UserDTO;
import lombok.Builder;
import lombok.Getter;
import org.springframework.data.geo.Distance;
import org.springframework.data.geo.Metrics;
import org.springframework.data.geo.Point;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.util.CollectionUtils;

import java.util.List;
import java.util.Optional;

@Builder
@Getter
public class SearchCriteria {

    private final List<String> tags;
    private final double[] location;
    private final Double distance;
    private final Double pricePerLiterFrom;
    private final Double pricePerLiterTo;

    public Criteria build() {
        Criteria criteria = new Criteria();
        if(!CollectionUtils.isEmpty(tags)) {
            criteria.and("tags").in(tags);
        }

        Optional.ofNullable(distance).ifPresent(v ->
                criteria.and("location")
                        .nearSphere(new Point(location[0],location[1]))
                        .maxDistance(new Distance(distance, Metrics.KILOMETERS).getNormalizedValue())
        );
        Optional.ofNullable(pricePerLiterFrom).ifPresent(v -> criteria.and("pricePerLiter").gte(v));
        Optional.ofNullable(pricePerLiterTo).ifPresent(v -> criteria.and("pricePerLiter").lte(v));
        return criteria;
    }

    public static SearchCriteria of(final UserDTO user, final InterestDTO i) {
        return new SearchCriteria(i.getTags(), user.getLocation(), i.getDistance(), i.getPricePerLiterFrom(), i.getPricePerLiterTo());
    }
}
