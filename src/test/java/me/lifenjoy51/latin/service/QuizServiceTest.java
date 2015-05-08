package me.lifenjoy51.latin.service;

import me.lifenjoy51.latin.LatinApplication;
import me.lifenjoy51.latin.domain.Quiz;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.SpringApplicationConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;

import java.util.List;

import static org.junit.Assert.*;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = LatinApplication.class)
@WebAppConfiguration
public class QuizServiceTest {
    
    @Autowired
    QuizService quizService;

    @Before
    public void setUp() throws Exception {

    }

    @Test
    public void testSaveHist() throws Exception {
        
    }

    @Test
    public void testGetWords() throws Exception {
        List<Quiz> q = quizService.getWords("lifenjoy51",1);
        System.out.println(q);
    }

    @Test
    public void testGetSentences() throws Exception {

    }

    @Test
    public void testGetAll() throws Exception {

    }
}