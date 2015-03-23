package me.lifenjoy51.latin.service;

import me.lifenjoy51.latin.LatinApplication;
import me.lifenjoy51.latin.domain.User;
import me.lifenjoy51.latin.domain.UserWordHist;
import me.lifenjoy51.latin.domain.Word;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.SpringApplicationConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.transaction.annotation.Transactional;

import static org.junit.Assert.*;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = LatinApplication.class)
@Transactional
public class UserWordHistRepositoryTest {
    
    @Autowired
    UserRepository userRepository;
    
    @Autowired
    WordRepository wordRepository;
    
    @Autowired
    UserWordHistRepository userWordHistRepository;
    
    @Autowired
    GoogleSheetService googleSheetService;
    
    //vars.
    User test;
    
    @Before
    public void before(){
        System.out.println("before!");
        
        //words
        googleSheetService.sync();
        
        //user
        test = new User("test");
        userRepository.save(test);
    }

    @Test
    public void testFindUserWordHist() throws Exception {
        
        //words
        Word amabote = wordRepository.getOne("amābō tē");
        Word discipule = wordRepository.getOne("discipule");
        Word et = wordRepository.getOne("et");
        Word me = wordRepository.getOne("mē");
        
        //test.
        userWordHistRepository.save(new UserWordHist(test, amabote, 1));
        userWordHistRepository.save(new UserWordHist(test, discipule, 0));
        userWordHistRepository.save(new UserWordHist(test, et, -1));
        userWordHistRepository.save(new UserWordHist(test, me, 1));
        userWordHistRepository.save(new UserWordHist(test, amabote, 1));
        userWordHistRepository.save(new UserWordHist(test, discipule, 1));
        userWordHistRepository.save(new UserWordHist(test, et, 0));
        userWordHistRepository.save(new UserWordHist(test, me, -1));
        
        //verify
        System.out.println("userRepository!");
        System.out.println(userRepository.findAll());
        System.out.println("wordRepository!");
        System.out.println(wordRepository.findAll());
        System.out.println("userWordHistRepository!");
        System.out.println(userWordHistRepository.findAll());
        System.out.println("userWordHistRepository!!!!!!");
        System.out.println(userWordHistRepository.findUserWordHist(test));
        
        for(UserWordHist uwh : userWordHistRepository.findUserWordHist(test)){
            System.out.println(uwh);
        }

        System.out.println("score!!");
        System.out.println(userWordHistRepository.getScore(test));
        

    }
}