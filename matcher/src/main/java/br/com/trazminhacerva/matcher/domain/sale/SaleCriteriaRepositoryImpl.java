package br.com.trazminhacerva.matcher.domain.sale;

import lombok.RequiredArgsConstructor;
import org.springframework.data.mongodb.core.MongoTemplate;

import java.util.List;

@RequiredArgsConstructor
public class SaleCriteriaRepositoryImpl implements SaleCriteriaRepository {

    private final MongoTemplate template;

    @Override
    public List<Sale> findBy(final SaleSearchCriteriaWrapper criteria) {
        return template.find(criteria.createQuery(), Sale.class);
    }
}
