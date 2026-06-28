package net.engineeringdigest.journalApp.controller;

import net.engineeringdigest.journalApp.api.response.WeatherResponse;
import net.engineeringdigest.journalApp.entity.User;
import net.engineeringdigest.journalApp.repository.UserRepository;
import net.engineeringdigest.journalApp.service.UserService;
import net.engineeringdigest.journalApp.service.WeatherService;
import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;


@RestController
@RequestMapping("/user")

public class UserController {

    @Autowired
    WeatherService weatherService;

    @Autowired
    private UserService userService;

    @Autowired
    private UserRepository userRepository;

    @GetMapping
    public ResponseEntity<?>  userDetails(){
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String userName = authentication.getName();
        User userInDb = userService.findByUserName(userName);
        WeatherResponse weatherResponse = weatherService.getWeather("chennai");
        String greeting= "";
        if (userInDb != null){
            if (weatherResponse != null) greeting = ", Weather looks like " + weatherResponse.getWeather().get(0).getDescription();
            return new ResponseEntity<>("Hi " + userName + greeting, HttpStatus.FOUND);
        }
        return new ResponseEntity<>("User Not Found --- Create newUser",HttpStatus.NOT_FOUND);
    }


    @PutMapping
    public ResponseEntity<User> updateUser (@RequestBody User updatedUser ){
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String userName = authentication.getName();
        User userInDB =   userService.findByUserName(userName);
        if (userInDB != null){
            userInDB.setUserName(updatedUser.getUserName());
            userInDB.setPassword(updatedUser.getPassword());
            userService.saveEntry(userInDB);
            return new ResponseEntity<>(userInDB,HttpStatus.OK);
        }

        return new ResponseEntity<>(HttpStatus.NOT_FOUND);

    }

    @DeleteMapping
    public ResponseEntity<?> deleteUser( ){

        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        User userInDb = userService.findByUserName(authentication.getName());
        if(userInDb != null){
            userRepository.deleteByUserName(userInDb.getUserName());
            return new ResponseEntity<>(HttpStatus.OK);
        }

        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

}
