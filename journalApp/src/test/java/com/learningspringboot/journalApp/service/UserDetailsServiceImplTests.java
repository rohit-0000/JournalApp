package com.learningspringboot.journalApp.service;

import com.learningspringboot.journalApp.Entity.User;
import com.learningspringboot.journalApp.Repository.UserRepository;
import com.learningspringboot.journalApp.Services.UserDetailServiceImp;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentMatchers;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.when;

//@SpringBootTest //we use this if one thing need to mock and other not
public class UserDetailsServiceImplTests {

    //    @Autowired //we use this if one thing need to mock and other not
    @InjectMocks
    private UserDetailServiceImp userDetailServiceImp;

//    @MockBean //we use this if one thing need to mock and other not
    @Mock
    private UserRepository userRepository;

    @BeforeEach
    void setUp(){
        MockitoAnnotations.initMocks(this);
    }
    @Test
    void loadUserByUsernameTest(){
        when(userRepository.findByUserName(ArgumentMatchers.anyString())).thenReturn(User.builder().userName("ram").password("vhsbdukhf").roles(new ArrayList<>()).build());
        UserDetails user=userDetailServiceImp.loadUserByUsername("Ram");
        assertNotNull(user);
    }
}
