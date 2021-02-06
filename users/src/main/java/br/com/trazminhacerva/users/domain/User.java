package br.com.trazminhacerva.users.domain;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.experimental.Accessors;
import org.springframework.data.mongodb.core.index.GeoSpatialIndexed;

import java.util.ArrayList;
import java.util.List;

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
    private double distance;
    private List<Interest> interests = new ArrayList<>();

    private User(final String name, final Email email, final double[] location) {
        this.name = name;
        this.email = email;
        this.location = location;
    }

    private User(final String name, final Email email, final double[] location, List<Interest> interets) {
        this(name, email, location);
        this.interests.addAll(interets);
    }

    public static User from(final String name, final String email, final double[] location) {
        return new User(name, Email.of(email), location);
    }

    public static User from(final String name, final String email, final double[] location, final List<Interest> interests) {
        return new User(name, Email.of(email), location, interests);
    }

    public User update(final String name, final double[] location) {
        this.name = name;
        this.location = location;
        return this;
    }

    public User updateInterests(final List<Interest> newInterests) {
        this.interests.clear();
        this.interests.addAll(newInterests);
        return this;
    }
}
