package net.engineeringdigest.journalApp.service;

import net.engineeringdigest.journalApp.model.SentimentData;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@Disabled
@SpringBootTest
public class SentimentConsumerServiceTest {

    @Autowired
    SentimentConsumerService sentimentConsumerService;

    @Autowired
    SentimentData sentimentData;

    @Test
    public void consumerServiceTest(){
        sentimentData.setEmail("monishlal8888@gmail.com");
        sentimentData.setSentiment("ANXIOUS");
        sentimentConsumerService.consume(sentimentData);
    }
}
