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

import me.lifenjoy51.latin.domain.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;
import org.springframework.util.StringUtils;

import java.util.*;

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

    public Word save(Word word) {
        return this.wordRepository.saveAndFlush(word);
    }

    public Word getWord(String name) {
        Assert.hasLength(name, "Name must not be empty");
        return this.wordRepository.findOne(name);
    }

    public Problem nextProblem(String userId) {

        //user
        User user = userRepository.findOne(userId);

        //number of choices
        int choicesCnt = 4;

        //sources.
        List<Word> words = new ArrayList(this.getHalfWords(user));
        Collections.shuffle(words);

        //choices
        List<Word> choices = words.subList(0, choicesCnt);

        //answer
        Word answer = choices.get(new Random().nextInt(choicesCnt));

        //info        
        Long score = userWordHistRepository.getScore(user);
        Info info = new Info((score == null ? 0 : score.intValue()));

        return new Problem(answer, choices, info);
    }

    /**
     * get words by userid.
     * *
     *
     * @param user
     * @return
     */
    private Collection<Word> getHalfWords(User user) {

        //단어만 추출함.
        Set<Word> words = new HashSet<Word>();

        //이력에서 절반.
        List<UserWordHist> userHist = userWordHistRepository.findUserWordHist(user);
        for (UserWordHist uwh : userHist.subList(0, userHist.size() / 2)) {
            words.add(uwh.getWord());
        }

        //전체에서 절반.
        List<Word> totalWords = wordRepository.findAll();
        for (Word w : totalWords.subList(0, totalWords.size() / 2)) {
            words.add(w);
        }
        //this.wordRepository.findByTitleWordIn(topHalfWord);
        System.out.println("words : " + words);
        return words;
    }

    /**
     * record problem solving history.
     * *
     *
     * @param userId
     * @param titleWord
     * @param score
     */
    public void saveHist(String userId, String titleWord, Integer score) {
        if (StringUtils.isEmpty(titleWord)) return;
        User user = userRepository.findOne(userId);
        Word word = wordRepository.findOne(titleWord);
        userWordHistRepository.saveAndFlush(new UserWordHist(user, word, score));
    }
}
