package br.com.trazminhacerva.users.endpoint.dto;

import lombok.Builder;
import lombok.Data;

import java.util.List;

/**
 * @author Marco Prado
 * @version 1.0 12/02/2021
 */
@Data
@Builder
public class UserInterestsWrapperDTO {
    private double distance;
    private List<InterestDTO> interests;
}
