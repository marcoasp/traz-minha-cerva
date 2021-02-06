package br.com.trazminhacerva.matcher.endpoint;

import br.com.trazminhacerva.matcher.domain.sale.Sale;
import br.com.trazminhacerva.matcher.domain.sale.SaleRepository;
import br.com.trazminhacerva.matcher.endpoint.dto.SaleDTO;
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
import org.springframework.data.mongodb.core.MongoTemplate;
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
public class NewSaleFunctionTest {

    @Autowired
    private InputDestination input;

    @Autowired
    private OutputDestination output;

    @MockBean
    private SaleRepository saleRepository;

    @Autowired
    private ObjectMapper mapper;

    @Test
    public void shouldSaveNewSale() throws IOException {
        SaleDTO newSale = SaleDTO.builder().id("abcd").tags(Collections.emptyList()).build();
        given(saleRepository.save(any(Sale.class))).willAnswer(AdditionalAnswers.returnsFirstArg());

        input.send(MessageBuilder.withPayload(newSale).build(), "newSaleFunction-in-0");
        SaleDTO receivedSale = mapper.readValue(output.receive(1L, "newSaleFunction-out-0").getPayload(), SaleDTO.class);
        assertThat(receivedSale).isEqualTo(newSale);

        verify(saleRepository).save(any(Sale.class));
    }
}