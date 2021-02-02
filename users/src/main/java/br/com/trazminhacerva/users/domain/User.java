package br.com.trazminhacerva.users.domain;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.experimental.Accessors;
import org.springframework.data.mongodb.core.index.GeoSpatialIndexed;

/**
 * @author Marco Prado
 * @version 1.0 01/02/2021
 */
@Getter
@Accessors(chain = true)
@RequiredArgsConstructor
public class User {

    private String id;
    private String name;
    private final Email email;
    @GeoSpatialIndexed
    private double[] location;

    private User(final String name, final Email email, final double[] location) {
        this.name = name;
        this.email = email;
        this.location = location;
    }

    public static User from(final String name, final String email, final double[] location) {
        return new User(name, Email.of(email), location);
    }

    public User update(final String name, final double[] location) {
        this.name = name;
        this.location = location;
        return this;
    }
}
