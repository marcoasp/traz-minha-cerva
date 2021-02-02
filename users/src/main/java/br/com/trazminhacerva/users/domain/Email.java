package br.com.trazminhacerva.users.domain;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

/**
 * @author Marco Prado
 * @version 1.0 01/02/2021
 */
@Getter
@RequiredArgsConstructor(access = AccessLevel.PRIVATE)
public class Email {
    private final String email;

    public static Email of(final String email) {
        return new Email(email);
    }
}
