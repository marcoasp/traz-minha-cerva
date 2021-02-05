package br.com.trazminhacerva.matches.utils;

import org.mockito.invocation.InvocationOnMock;
import reactor.core.publisher.Mono;

public class ReactiveMockitoAnswers {
    public static Mono<?> firstArgMono(InvocationOnMock invocationOnMock) {
        return Mono.just(invocationOnMock.getArgument(0));
    }
}