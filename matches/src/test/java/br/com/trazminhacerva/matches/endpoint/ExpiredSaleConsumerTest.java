package br.com.trazminhacerva.matches.endpoint;

import br.com.trazminhacerva.matches.domain.Match;
import br.com.trazminhacerva.matches.domain.MatchRepository;
import br.com.trazminhacerva.matches.endpoint.dto.MatchDTO;
import br.com.trazminhacerva.matches.utils.ReactiveMockitoAnswers;
import com.mongodb.client.result.UpdateResult;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.mongo.MongoReactiveAutoConfiguration;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.cloud.stream.binder.test.InputDestination;
import org.springframework.cloud.stream.binder.test.TestChannelBinderConfiguration;
import org.springframework.context.annotation.Import;
import org.springframework.data.mongodb.core.ReactiveMongoTemplate;
import org.springframework.messaging.support.MessageBuilder;
import reactor.core.publisher.Mono;

import java.util.Arrays;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;

/**
 * @author Marco Prado
 * @version 1.0 05/02/2021
 */
@SpringBootTest
@Import(TestChannelBinderConfiguration.class)
@EnableAutoConfiguration(exclude= MongoReactiveAutoConfiguration.class)
public class ExpiredSaleConsumerTest {

    @Autowired
    private InputDestination input;

    @MockBean
    private MatchRepository matchRepository;

    @MockBean
    private ReactiveMongoTemplate reactiveMongoTemplate;

    @Test
    public void shouldExpireMatches() {
        given(matchRepository.updateStatusForSale(eq("abcd")))
                .willReturn(Mono.just(mock(UpdateResult.class)));
        input.send(MessageBuilder.withPayload("abcd").build(),  "expiredSaleConsumer-in-0");

        verify(matchRepository).updateStatusForSale(eq("abcd"));
    }
}