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
    private final Double pricePerLiterFrom;
    private final Double pricePerLiterTo;

    public Criteria build() {
        Criteria criteria = new Criteria();
        if(!CollectionUtils.isEmpty(tags)) {
            criteria.and("tags").in(tags);
        }


        Optional.ofNullable(pricePerLiterFrom).ifPresent(v -> criteria.and("pricePerLiter").gte(v));
        Optional.ofNullable(pricePerLiterTo).ifPresent(v -> criteria.and("pricePerLiter").lte(v));
        return criteria;
    }

    public static SearchCriteria of(final UserDTO user, final InterestDTO i) {
        return new SearchCriteria(i.getTags(), i.getPricePerLiterFrom(), i.getPricePerLiterTo());
    }
}
