package net.engineeringdigest.journalApp.service;


import net.engineeringdigest.journalApp.entity.User;
import net.engineeringdigest.journalApp.repository.UserRepository;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import static org.junit.jupiter.api.Assertions.assertNotNull;

@Disabled
@SpringBootTest
public class ServiceTest {

    @Autowired
    private UserRepository userRepository;


    @Test
    public void testFindByUserName(){
        try {

            User user = userRepository.findByUserName("Monish Lal");
            System.out.println(user);
            assertNotNull(user);
        }
        catch (Exception e){
            System.out.println(e);
        }
    }
}
