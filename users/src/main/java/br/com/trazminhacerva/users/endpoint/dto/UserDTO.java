package br.com.trazminhacerva.users.endpoint.dto;

import lombok.Builder;
import lombok.Data;

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
}
