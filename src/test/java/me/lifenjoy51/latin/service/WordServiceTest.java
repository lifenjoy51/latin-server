package me.lifenjoy51.latin.service;

import me.lifenjoy51.latin.LatinApplication;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.SpringApplicationConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

import static org.junit.Assert.*;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = LatinApplication.class)
@Transactional
public class WordServiceTest {

    @Autowired
    WordRepository wordRepository;

    @Test
    public void testUnits() throws Exception {

        List<String> units = wordRepository.findByUnitGrouped();
        System.out.println(units);
    }
}