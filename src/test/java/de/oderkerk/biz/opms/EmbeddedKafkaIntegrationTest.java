package de.oderkerk.biz.opms;

import com.fasterxml.jackson.databind.ObjectMapper;
import de.oderkerk.biz.opms.consumer.KafkaConsumer;
import de.oderkerk.biz.opms.producer.TestProducer1;
import de.oderkerk.biz.opms.struct.Daten;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.kafka.test.context.EmbeddedKafka;
import org.springframework.test.annotation.DirtiesContext;


import java.util.concurrent.TimeUnit;


import static org.hamcrest.Matchers.containsString;
import static org.junit.Assert.assertThat;
import static org.junit.Assert.assertTrue;

@SpringBootTest
@DirtiesContext
@EmbeddedKafka(partitions = 1, brokerProperties = { "listeners=PLAINTEXT://localhost:9092", "port=9092" })
class EmbeddedKafkaIntegrationTest {

    Daten testdaten;
    @BeforeEach
    void setup(){
        testdaten= new Daten(4711, "Mike", "Tester", "Testtrasse 1 ", "99999", "Testort", "Testland");
    }


    @Autowired
    private KafkaConsumer consumer;

    @Autowired
    private TestProducer1 producer;

    @Value("${test.topic}")
    private String topic;

    @Test
    public void givenEmbeddedKafkaBroker_whenSendingWithSimpleProducer_thenMessageReceived() 
      throws Exception {
        ObjectMapper objectMapper = new ObjectMapper();

        String testjson =objectMapper.writeValueAsString(testdaten);
        
        producer.sendData(topic,testjson );
        
        boolean messageConsumed = consumer.getLatch().await(10, TimeUnit.SECONDS);
        assertTrue(messageConsumed);
        assertThat(consumer.getPayload(),containsString(testjson));
    }
}