package com.learningspringboot.journalApp.Services;

import com.learningspringboot.journalApp.Entity.JournalEntry;
import com.learningspringboot.journalApp.Entity.User;
import com.learningspringboot.journalApp.Repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

//@Component
@Service //it is indicate that class has business logic
public class UserService {

    @Autowired
    private UserRepository userRepository;
    private static final PasswordEncoder passwordEncoder=new BCryptPasswordEncoder();

    public boolean saveNewUser(User user)
    {
        try{
            user.setPassword(passwordEncoder.encode(user.getPassword()));
            user.setRoles(Arrays.asList("USER"));
            user.setJournalEntries(new ArrayList<>());
            userRepository.save(user);
            return true;
        }
        catch (Exception e)
        {
            return false;
        }
    }

    public void saveUser(User user)
    {
        userRepository.save(user);
    }

    public User findByUserName(String userName)
    {
        return userRepository.findByUserName(userName);
    }


    public void deleteByUserName(String name) {
        userRepository.deleteByUserName(name);
    }


    public List<User> getAll()
    {
        return userRepository.findAll();
    }

    public void saveAdmin(User user) {
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        user.setRoles(Arrays.asList("ADMIN","USER"));
        userRepository.save(user);
    }
}
