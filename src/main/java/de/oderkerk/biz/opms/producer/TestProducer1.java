package de.oderkerk.biz.opms.producer;


import de.oderkerk.biz.opms.struct.Daten;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Component;

@Slf4j
@Component
public class TestProducer1 {

    @Autowired
    private KafkaTemplate<Daten, String> kafkaTemplate;

    public void sendData(String topic, String payload){
        log.info("sending payload='{}' to topic='{}", payload, topic);
        kafkaTemplate.send(topic,payload);


    }


}
