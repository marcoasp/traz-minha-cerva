package br.com.trazminhacerva.matcher.domain.user;

import lombok.Builder;
import lombok.Getter;

import java.util.Collections;
import java.util.List;

/**
 * @author Marco Prado
 * @version 1.0 05/02/2021
 */
@Builder
@Getter
public class Interest {
    private Double pricePerLiterFrom;
    private Double pricePerLiterTo;
    private String name;
    private List<String> tags;

    public List<String> getTags() {
        return Collections.unmodifiableList(tags);
    }
}
