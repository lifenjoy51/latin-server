package me.lifenjoy51.latin.service;

import me.lifenjoy51.latin.LatinApplication;
import me.lifenjoy51.latin.domain.Quiz;
import me.lifenjoy51.latin.domain.QuizChoice;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.SpringApplicationConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

import static org.hamcrest.Matchers.is;
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

        Set<QuizChoice> quizChoices = new HashSet<QuizChoice>();
        
        for(Quiz qz : q){
            assertThat(qz.getChoices().size(), is(4));
            System.out.println(qz.getAnswer());
            quizChoices.add(qz.getAnswer());
        }
        
        assertEquals(q.size(), quizChoices.size());
    }

    @Test
    public void testGetSentences() throws Exception {
        List<Quiz> q = quizService.getSentences("lifenjoy51",1);
        System.out.println(q);

        Set<QuizChoice> quizChoices = new HashSet<QuizChoice>();

        for(Quiz qz : q){
            assertThat(qz.getChoices().size(), is(4));
            System.out.println(qz.getAnswer());
            quizChoices.add(qz.getAnswer());
        }

        assertEquals(q.size(), quizChoices.size());

    }

    @Test
    public void testGetAll() throws Exception {
        List<Quiz> q = quizService.getAll("lifenjoy51",0);
        System.out.println(q);

        Set<QuizChoice> quizChoices = new HashSet<QuizChoice>();

        for(Quiz qz : q){
            assertThat(qz.getChoices().size(), is(4));
            System.out.println(qz.getAnswer());
            quizChoices.add(qz.getAnswer());
        }

        assertEquals(q.size(), quizChoices.size());

    }
}