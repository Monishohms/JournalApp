package net.engineeringdigest.journalApp.controller;


import net.engineeringdigest.journalApp.cache.AppCache;
import net.engineeringdigest.journalApp.entity.User;
import net.engineeringdigest.journalApp.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/admin")
public class AdminController {

    @Autowired
    private UserService userService;

    @Autowired
    AppCache appCache;

    @GetMapping("/all-users")
    public ResponseEntity<?> getAllUsers(){
        List<User>  users = userService.getAll();
        if (users != null){
            return new ResponseEntity<>(users , HttpStatus.OK);
        }
        return new ResponseEntity<>("No Users",HttpStatus.NO_CONTENT);
    }

    @PostMapping("/create-admin-user")
    public void createAdmin(@RequestBody User user){
        userService.saveAdmin(user);
    }


    @GetMapping("/clear-app-cache")  // this is utility basically for my own purpose
    public void clearAppCache(){
        appCache.init();

    }
}
