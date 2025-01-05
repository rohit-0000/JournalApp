package com.learningspringboot.journalApp.service;

import com.learningspringboot.journalApp.Entity.User;
import com.learningspringboot.journalApp.Repository.UserRepository;
import com.learningspringboot.journalApp.Services.UserService;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ArgumentsSource;
import org.junit.jupiter.params.provider.CsvSource;
import org.junit.jupiter.params.provider.ValueSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
public class UserServiceTests {
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private UserService userservice;

    @Disabled
    @Test
    public void testFindByUserName()
    {
        assertEquals(4,2+2);
        assertNotNull(userRepository.findByUserName("Ram"));

        User user=userRepository.findByUserName("Ram");
        assertTrue(!user.getJournalEntries().isEmpty());
    }

    @ParameterizedTest
    @CsvSource({
            "1,1,2",
            "10, 2 , 12",
            "3, 3, 9"
    })
    public void test(int a,int b,int expected)
    {
        assertEquals(expected,a+b);
    }

//    @AfterAll //after all test
//    @BeforeEach //apply before each test
    @BeforeAll //before all test
    static void setup(){

    }

//    @CsvSource({
//            "Ram",
//            "Shyam",
//            "Rohit"
//    })
    @ParameterizedTest
    @ValueSource(strings ={
            "Ram",
            "Shyam",
            "Rohit"
    })
    public void Parametrized_testFindByUserName(String name)
    {
        assertNotNull(userRepository.findByUserName(name),"failed for "+ name);
    }

    @ParameterizedTest
    @ArgumentsSource(UserArgumentsProvider.class)
    public void testSaveNewUser(User user)
    {
        assertTrue(userservice.saveNewUser(user));
    }


}
