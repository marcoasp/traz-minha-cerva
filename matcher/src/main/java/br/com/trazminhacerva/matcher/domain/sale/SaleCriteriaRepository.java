package br.com.trazminhacerva.matcher.domain.sale;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface SaleCriteriaRepository {
    List<Sale> findBy(SaleSearchCriteriaWrapper criteria);
}
