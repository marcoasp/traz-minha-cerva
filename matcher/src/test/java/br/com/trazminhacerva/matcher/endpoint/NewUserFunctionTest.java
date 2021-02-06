package br.com.trazminhacerva.matcher.endpoint;

import br.com.trazminhacerva.matcher.domain.user.User;
import br.com.trazminhacerva.matcher.domain.user.UserRepository;
import br.com.trazminhacerva.matcher.endpoint.dto.UserDTO;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.mockito.AdditionalAnswers;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.mongo.MongoAutoConfiguration;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.cloud.stream.binder.test.InputDestination;
import org.springframework.cloud.stream.binder.test.OutputDestination;
import org.springframework.cloud.stream.binder.test.TestChannelBinderConfiguration;
import org.springframework.context.annotation.Import;
import org.springframework.messaging.support.MessageBuilder;

import java.io.IOException;
import java.util.Collections;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.verify;

/**
 * @author Marco Prado
 * @version 1.0 05/02/2021
 */
@SpringBootTest
@Import(TestChannelBinderConfiguration.class)
public class NewUserFunctionTest {

    @Autowired
    private InputDestination input;

    @Autowired
    private OutputDestination output;

    @MockBean
    private UserRepository userRepository;

    @Autowired
    private ObjectMapper mapper;

    @Test
    public void shouldSaveNewUser() throws IOException {
        UserDTO newUser = UserDTO.builder().id("abcd").interests(Collections.emptyList()).build();
        given(userRepository.save(any(User.class))).willAnswer(AdditionalAnswers.returnsFirstArg());

        input.send(MessageBuilder.withPayload(newUser).build(), "newUserFunction-in-0");
        UserDTO receivedUser = mapper.readValue(output.receive(1L, "newUserFunction-out-0").getPayload(), UserDTO.class);
        assertThat(receivedUser).isEqualTo(newUser);

        verify(userRepository).save(any(User.class));
    }
}