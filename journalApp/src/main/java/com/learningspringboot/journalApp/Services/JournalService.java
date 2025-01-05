package com.learningspringboot.journalApp.Services;

import com.learningspringboot.journalApp.Entity.JournalEntry;
import com.learningspringboot.journalApp.Entity.User;
import com.learningspringboot.journalApp.Repository.JournalEntryRepository;
import com.learningspringboot.journalApp.Repository.UserRepository;
import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.Optional;

@Service
public class JournalService {

    @Autowired
    private JournalEntryRepository journalEntryRepository;
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private UserService userService;

    public void saveEntry(JournalEntry journalEntry)
    {
        journalEntryRepository.save(journalEntry);
    }

    @Transactional
    public void saveEntry(JournalEntry journalEntry, String userName)
    {
        try{
            User user=userRepository.findByUserName(userName);
            journalEntry.setDate(LocalDateTime.now());
            JournalEntry saved=journalEntryRepository.save(journalEntry);
            user.getJournalEntries().add(saved);
            userService.saveUser(user); //here password already encrypted
        } catch (Exception e) {
            System.out.println(e);
            throw new RuntimeException("An error occurred while saving the entry"+e);
        }
    }

    public Optional<JournalEntry> getById(ObjectId myId)
    {
        return journalEntryRepository.findById(myId);
    }

    @Transactional
    public boolean deleteById(ObjectId id,String userName)
    {
        boolean removed=false;
        try{
            User user=userService.findByUserName(userName);
            removed=user.getJournalEntries().removeIf(x->x.getId().equals(id));
            if(removed)
            {
                userService.saveUser(user);
                journalEntryRepository.deleteById(id);
            }
        }
        catch (Exception e)
        {
            System.out.println(e);
            throw new RuntimeException("An error occures while deleting the journalEntry");
        }
        return removed;
    }
}
