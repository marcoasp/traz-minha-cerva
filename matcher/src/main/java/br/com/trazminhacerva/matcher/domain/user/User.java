package br.com.trazminhacerva.matcher.domain.user;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.experimental.Accessors;
import org.springframework.data.mongodb.core.index.GeoSpatialIndexed;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * @author Marco Prado
 * @version 1.0 05/02/2021
 */
@Getter
@RequiredArgsConstructor
public class User {

    private final String id;
    private final String email;
    @GeoSpatialIndexed
    private final double[] location;
    private final List<Interest> interests;

    public List<Interest> getInterests() {
        return Collections.unmodifiableList(interests);
    }
}
