/*
 * Copyright 2012-2013 the original author or authors.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package me.lifenjoy51.latin.service;

import me.lifenjoy51.latin.domain.Problem;
import me.lifenjoy51.latin.domain.UserWordHist;
import me.lifenjoy51.latin.domain.Word;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Random;

@Component("wordService")
@Transactional
public class WordService {

    private final WordRepository wordRepository;

    private final UserWordHistRepository userWordHistRepository;

    private final UserRepository userRepository;


    @Autowired
    public WordService(WordRepository wordRepository, UserWordHistRepository userWordHistRepository, UserRepository userRepository) {
        this.wordRepository = wordRepository;
        this.userWordHistRepository = userWordHistRepository;
        this.userRepository = userRepository;
    }

    public Word getWord(String name) {
        Assert.hasLength(name, "Name must not be empty");
        return this.wordRepository.findOne(name);
    }

    public Problem nextProblem(String userId) {
        int choicesCnt = 4;
        List<Word> words = this.getWords(userId);
        Collections.shuffle(words);
        List<Word> choices = words.subList(0, choicesCnt);
        Word answer = choices.get(new Random().nextInt(choicesCnt));
        return new Problem(answer, choices);
    }

    /**
     * get words by userid.
     * *
     * @param userId
     * @return
     */
    private List<Word> getWords(String userId) {
        //user-word history
        List<UserWordHist> hist = userWordHistRepository.findUserWordHist(userRepository.getOne(userId));

        //top 1/2 words.
        List<String> topHalfWord = new ArrayList<String>();
        for (UserWordHist uwh : hist.subList(0, hist.size() / 2)) {
            topHalfWord.add(uwh.getWord().getTitleWord());
        }

        //get words
        List<Word> words = this.wordRepository.findByTitleWordIn(topHalfWord);
        System.out.println("words : " + words);
        return words;
    }

}
