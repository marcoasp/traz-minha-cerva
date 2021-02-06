package br.com.trazminhacerva.matcher.endpoint.dto;

import lombok.Builder;
import lombok.Data;

import java.util.List;

/**
 * @author Marco Prado
 * @version 1.0 01/02/2021
 */
@Data
@Builder
public class UserDTO {

    private String id;
    private String name;
    private String email;
    private double[] location;
    private double distance;
    private List<InterestDTO> interests;
}
