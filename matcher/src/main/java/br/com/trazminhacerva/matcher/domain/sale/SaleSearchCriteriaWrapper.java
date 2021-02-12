package br.com.trazminhacerva.matcher.domain.sale;

import br.com.trazminhacerva.matcher.endpoint.dto.UserDTO;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import org.springframework.data.geo.Distance;
import org.springframework.data.geo.Metrics;
import org.springframework.data.geo.Point;
import org.springframework.data.mongodb.core.aggregation.Aggregation;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.NearQuery;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.util.CollectionUtils;

import java.util.List;
import java.util.Optional;

import static java.util.stream.Collectors.toList;

@Builder
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public class SaleSearchCriteriaWrapper {

    private final String userEmail;
    private final double[] location;
    private final List<SearchCriteria> interestsCriteria;
    private final double distance;

    public static SaleSearchCriteriaWrapper from(final UserDTO user) {
        return SaleSearchCriteriaWrapper.builder()
                .location(user.getLocation())
                .distance(user.getDistance())
                .interestsCriteria(user.getInterests().stream().map(i -> SearchCriteria.of(user, i)).collect(toList()))
                .build();
    }

    Query createQuery() {
        Criteria criteria = new  Criteria();
        if(!CollectionUtils.isEmpty(this.interestsCriteria)) {
            criteria.orOperator(this.interestsCriteria.stream().map(SearchCriteria::build).collect(toList()).toArray(new Criteria[0]));
        }
        criteria.and("location")
                .nearSphere(new Point(location[0],location[1]))
                .maxDistance(new Distance(distance, Metrics.KILOMETERS).getNormalizedValue());
        return new Query(criteria);
    }
}
