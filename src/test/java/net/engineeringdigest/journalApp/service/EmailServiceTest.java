package net.engineeringdigest.journalApp.service;

import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
public class EmailServiceTest {

    @Autowired
    EMailService emailService;

    @Disabled
    @Test
    public void sendMailTest(){

        emailService.sendMail("omshankarlal1770@gmail.com","Testing Java Spring Boot", "Hi, Aap kaise Ho ?");
    }

}
