package net.engineeringdigest.journalApp.service;

import net.engineeringdigest.journalApp.model.SentimentData;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

@Service
public class SentimentConsumerService {

    @Autowired
    EMailService eMailService;


    @KafkaListener(topics = "weekly-sentiments", groupId = "weekly-sentiments-group")
    public void consume (SentimentData sentimentData){
        sendMail(sentimentData);
    }

    private void sendMail(SentimentData sentimentData){

        eMailService.sendMail(sentimentData.getEmail(),"Sentiment for previous week" , " Your Sentiment for 7 Days is : " + sentimentData.getSentiment());
    }
}
