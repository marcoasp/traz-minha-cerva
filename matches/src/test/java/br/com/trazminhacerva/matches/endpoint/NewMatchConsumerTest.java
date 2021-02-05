package br.com.trazminhacerva.matches.endpoint;

import br.com.trazminhacerva.matches.domain.Match;
import br.com.trazminhacerva.matches.domain.MatchRepository;
import br.com.trazminhacerva.matches.endpoint.dto.MatchDTO;
import br.com.trazminhacerva.matches.endpoint.mapper.MatchMapper;
import br.com.trazminhacerva.matches.utils.ReactiveMockitoAnswers;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.mongo.MongoAutoConfiguration;
import org.springframework.boot.autoconfigure.mongo.MongoReactiveAutoConfiguration;
import org.springframework.boot.autoconfigure.security.servlet.SecurityAutoConfiguration;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.cloud.stream.binder.test.InputDestination;
import org.springframework.cloud.stream.binder.test.TestChannelBinderConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Import;
import org.springframework.data.mongodb.core.ReactiveMongoTemplate;
import org.springframework.messaging.support.MessageBuilder;

import java.util.Arrays;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.verify;

/**
 * @author Marco Prado
 * @version 1.0 05/02/2021
 */
@SpringBootTest
@Import(TestChannelBinderConfiguration.class)
@EnableAutoConfiguration(exclude=MongoReactiveAutoConfiguration.class)
public class NewMatchConsumerTest {

    @Autowired
    private InputDestination input;

    @MockBean
    private MatchRepository matchRepository;

    @MockBean
    private ReactiveMongoTemplate reactiveMongoTemplate;

    @Test
    public void shouldSaveNewMatch() {
        given(matchRepository.save(any()))
                .willAnswer(ReactiveMockitoAnswers::firstArgMono);
        MatchDTO match = MatchDTO.builder()
                .userId("abcd")
                .saleId("hij")
                .name("Apia")
                .pricePerLiter(20.0)
                .location(new double[]{10.0, 20.0})
                .tags(Arrays.asList("IPA")).build();
        input.send(MessageBuilder.withPayload(match).build(),  "newMatchConsumer-in-0");

        verify(matchRepository).save(any(Match.class));
    }
}