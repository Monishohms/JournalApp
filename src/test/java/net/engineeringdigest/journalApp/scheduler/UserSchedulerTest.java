package net.engineeringdigest.journalApp.scheduler;


import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@Slf4j
@SpringBootTest
public class UserSchedulerTest {

    @Autowired
    UserScheduler userScheduler;

    @Test
    public void userSchedulerTest(){
        try{
            userScheduler.fetchUserAndSendMail();
        } catch (Exception e) {
            log.error("Exception occured", e);
        }

    }
}
