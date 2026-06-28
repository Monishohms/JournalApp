package net.engineeringdigest.journalApp.repository;


import net.engineeringdigest.journalApp.entity.UserRepositoryImpl;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
public class UserRepositoryImplTest {

    @Autowired
    UserRepositoryImpl userRepository;

    @Test
    public void getUserForSATest(){

        userRepository.getUserForSA();
    }
}
