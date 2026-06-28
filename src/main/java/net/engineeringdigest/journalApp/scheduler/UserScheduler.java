package net.engineeringdigest.journalApp.scheduler;


import lombok.extern.slf4j.Slf4j;
import net.engineeringdigest.journalApp.entity.JournalEntry;
import net.engineeringdigest.journalApp.entity.User;
import net.engineeringdigest.journalApp.entity.UserRepositoryImpl;
import net.engineeringdigest.journalApp.enums.Sentiment;
import net.engineeringdigest.journalApp.model.SentimentData;
import net.engineeringdigest.journalApp.service.EMailService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Slf4j
@Component
public class UserScheduler {

    @Autowired
    private KafkaTemplate<String,SentimentData> kafkaTemplate;

    @Autowired
    UserRepositoryImpl userRepository;

    @Autowired
    EMailService eMailService;

    @Scheduled(cron = "0 0 9 ? * SUN *")
    public void fetchUserAndSendMail(){

        List<User> userForSA = userRepository.getUserForSA();
        for (User user : userForSA){
            List<JournalEntry> journalEntries = user.getJournalEntries();
            List<Sentiment> sentiments = journalEntries.stream().filter(x -> x.getDate().isAfter(LocalDateTime.now().minus(7, ChronoUnit.DAYS))).map(x -> x.getSentiment()).collect(Collectors.toList());
            Map<Sentiment, Integer> sentimentsCounts = new HashMap<>();
            for (Sentiment sentiment : sentiments){
                if (sentiment != null){
                    sentimentsCounts.put(sentiment, sentimentsCounts.getOrDefault(sentiment,0)+1);
                }
            }
            Sentiment mostFreqSentimenet = null;
            int maxCount= 0;
            for (Map.Entry<Sentiment,Integer> entry : sentimentsCounts.entrySet()){
                if (entry.getValue() > maxCount){
                    maxCount = entry.getValue();
                    mostFreqSentimenet = entry.getKey();
                }
            }

            if (mostFreqSentimenet != null){
               // eMailService.sendMail(user.getEmail(), "Sentiment for last 7 days ", mostFreqSentimenet.toString()  ); <-----Instead of directly sending mail we are using Kafka

                try{

                    SentimentData buildSentimentData = SentimentData.builder().email(user.getEmail()).sentiment("Sentiment for last 7 Days is " + mostFreqSentimenet).build();
                    kafkaTemplate.send("weekly-sentiments", user.getEmail(),buildSentimentData);
                } catch (Exception e) {
                    log.error("Exception Ocurred at " , e);
                }



            }


        }

    }


}
