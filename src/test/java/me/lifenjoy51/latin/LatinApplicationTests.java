package me.lifenjoy51.latin;

import me.lifenjoy51.latin.domain.Word;
import me.lifenjoy51.latin.service.GoogleSheetService;
import me.lifenjoy51.latin.service.UserService;
import me.lifenjoy51.latin.service.WordService;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.SpringApplicationConfiguration;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

/**
 * Integration test to run the application.
 *
 * @author Oliver Gierke
 */
@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = LatinApplication.class)
@WebAppConfiguration
//@ActiveProfiles("scratch")
// Separate profile for web tests to avoid clashing databases
public class LatinApplicationTests {

    @Autowired
    private WebApplicationContext context;
    private MockMvc mvc;
    
    @Autowired
    WordService wordService;

    //테스트 순서.
    // 1. 유저 등록.
    // 2. 문제 풀이.
    // 3. 제대로 나왔는지 확인?!

    @Before
    public void setUp() {

        //words
        wordService.save(new Word("amābō tē","제발","please"));
        wordService.save(new Word("discipule","남학생","male student"));
        wordService.save(new Word("et","그리고","and"));
        wordService.save(new Word("mē","내","my"));
        wordService.save(new Word("bis","두번","twice"));
        wordService.save(new Word("tuum","너의","your"));
        wordService.save(new Word("sine","~없이","without"));
        wordService.save(new Word("est","이다","is"));
        
        GoogleSheetService.TOTAL_COUNT = 8;
                

        this.mvc = MockMvcBuilders.webAppContextSetup(this.context).build();


    }

    @Test
    public void testHome() throws Exception {
        
        String userId = "lifenjoy51";

        //add user.
        this.mvc.perform(get("/register").param("userId", userId))
                .andExpect(status().isOk())
                .andExpect(content().string(""));

        //solving problerms.
        this.mvc.perform(get("/next")
                .param("userId", userId)
                .param("titleWord","")
                .param("score",""))
                .andExpect(status().isOk());
        
        //solving problerms.
        this.mvc.perform(get("/next")
                .param("userId", userId)
                .param("titleWord", "amābō tē")
                .param("score", "1"))
                .andExpect(status().isOk());

        //solving problerms.
        this.mvc.perform(get("/next")
                .param("userId", userId)
                .param("titleWord","discipule")
                .param("score","-1"))
                .andExpect(status().isOk());

        //solving problerms.
        this.mvc.perform(get("/next")
                .param("userId", userId)
                .param("titleWord","et")
                .param("score","0"))
                .andExpect(status().isOk());

        //solving problerms.
        this.mvc.perform(get("/next")
                .param("userId", userId)
                .param("titleWord","mē")
                .param("score","1"))
                .andExpect(status().isOk());

        //solving problerms.
        String result = this.mvc.perform(get("/next")
                .param("userId", userId)
                .param("titleWord","amābō tē")
                .param("score","1"))
                .andExpect(status().isOk())
                .toString();

        System.out.println("result");
        System.out.println(result);
        
    }
}
