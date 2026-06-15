package net.engineeringdigest.journalApp.controller;


import net.engineeringdigest.journalApp.entity.JournalEntry;
import net.engineeringdigest.journalApp.entity.User;
import net.engineeringdigest.journalApp.service.JournalEntryService;
import net.engineeringdigest.journalApp.service.UserService;
import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/journal")

public class JournalEntryController {

    @Autowired
    private JournalEntryService journalEntryService; // bean ka roop me nhi hai tho obj me null hoga and throws nullpointer deferencing error

    @Autowired
    private UserService userService;

    @GetMapping ()
    public ResponseEntity<List<JournalEntry>> getAllJournalEntriesOfUser(){
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String userName = authentication.getName();
        User user = userService.findByUserName(userName);
        List <JournalEntry> entries = user.getJournalEntries();
        if (entries.isEmpty()){
            return new ResponseEntity<>( HttpStatus.NOT_FOUND ) ;
        }
        return new ResponseEntity<>(entries, HttpStatus.OK ) ;
    }

    @PostMapping()
    public ResponseEntity<?> createEntry(@RequestBody JournalEntry myEntry){
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String userName = authentication.getName();
        myEntry.setDate(LocalDateTime.now());
        journalEntryService.saveEntry(myEntry, userName);
        return new ResponseEntity<>( HttpStatus.CREATED);
    }

    @GetMapping("/id/{myId}")
    public ResponseEntity<?> getJournalEntryById(@PathVariable ObjectId myId){
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String userName = authentication.getName();
        User userInDb  = userService.findByUserName(userName);
        List<JournalEntry> collect = userInDb.getJournalEntries().stream().filter(x -> x.getId().equals(myId)).collect(Collectors.toList());
        if (! collect.isEmpty()){
            Optional<JournalEntry>journalEntry = journalEntryService.getById(myId);
            if (journalEntry.isPresent()){
                return new ResponseEntity<>(journalEntry.get(), HttpStatus.OK);
            }

        }
        return new ResponseEntity<>( HttpStatus.NOT_FOUND);

    }

    @DeleteMapping("/id/{myId}")
    public ResponseEntity<?> deleteJournalEntryById( @PathVariable ObjectId myId){
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String userName = authentication.getName();
        boolean removed  = journalEntryService.deleteById(userName, myId);
        if (removed){
            return new ResponseEntity<>(HttpStatus.NO_CONTENT) ;
        }
        else{
            return new ResponseEntity<>( HttpStatus.NOT_FOUND);
        }
    }

    @PutMapping("id/{myId}")
    public ResponseEntity<JournalEntry> updateJournalEntryById(@PathVariable ObjectId myId , @RequestBody JournalEntry updatedEntry) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String userName = authentication.getName();
        User userInDb = userService.findByUserName(userName);
        List<JournalEntry> collect = userInDb.getJournalEntries().stream().filter(x -> x.getId().equals(myId)).collect(Collectors.toList());
        if( ! collect.isEmpty()){
            JournalEntry oldEntry = journalEntryService.getById(myId).orElse(null);
            if (oldEntry != null) {
                oldEntry.setTitle(updatedEntry.getTitle().isEmpty() ? oldEntry.getTitle() : updatedEntry.getTitle());
                oldEntry.setContent(updatedEntry.getContent().isEmpty() ? oldEntry.getContent() : updatedEntry.getContent());
                journalEntryService.saveEntry(oldEntry);
                return new ResponseEntity<>(oldEntry, HttpStatus.OK);
            }
        }

        return new ResponseEntity<>(HttpStatus.NOT_FOUND);


    }
}

