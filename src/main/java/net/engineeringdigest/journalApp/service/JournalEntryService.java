package net.engineeringdigest.journalApp.service;

import net.engineeringdigest.journalApp.entity.JournalEntry;
import net.engineeringdigest.journalApp.entity.User;
import net.engineeringdigest.journalApp.repository.JournalEntryRepository;
import net.engineeringdigest.journalApp.repository.UserRepository;
import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
public class JournalEntryService  {

    @Autowired
    private JournalEntryRepository journalEntryRepository;

    @Autowired
    private UserService userService;

    @Transactional
    public void saveEntry (JournalEntry journalEntry, String userName){
        User user = userService.findByUserName(userName);
        JournalEntry saved = journalEntryRepository.save(journalEntry);
        user.getJournalEntries().add(saved);
        userService.saveEntry(user);
    }

    public void saveEntry (JournalEntry journalEntry){
        journalEntryRepository.save(journalEntry);
    }

    public List<JournalEntry> getAll(){
        return journalEntryRepository.findAll();
    }

    public Optional<JournalEntry> getById(ObjectId Id){
        return  journalEntryRepository.findById(Id);
    }

    @Transactional
    public boolean deleteById(String userName , ObjectId Id){
        boolean removed = false;
        try{
            User userInDb = userService.findByUserName(userName);
            removed = userInDb.getJournalEntries().removeIf(n -> n.getId().equals(Id));
            if (removed){
                userService.saveEntry(userInDb);
                journalEntryRepository.deleteById(Id);
            }
        }

        catch (Exception e){
            System.out.println(e);
            throw new RuntimeException("An Error Occurred While Deleting: " ,e);

        }
        return removed;

    }

    public JournalEntry updateById ( ObjectId Id){
       return journalEntryRepository.findById(Id).orElse(null);
    }
}
