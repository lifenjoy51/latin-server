package me.lifenjoy51.latin.service;

import me.lifenjoy51.latin.LatinApplication;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.SpringApplicationConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = LatinApplication.class)
public class GoogleSheetServiceTest {
    
    @Autowired
    GoogleSheetService googleSheetService;

    @Test
    public void testSync() throws Exception {
        googleSheetService.sync();
    }
}