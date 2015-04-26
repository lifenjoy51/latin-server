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

    final int CHOICE_COUNT = 4;
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

    /**
     * 이전에 입력한 이력을 가지고 자주 틀린 문제 위주로 문제를 가져온다.*
     *
     * @param userId
     * @param unit
     * @return
     */
    public ProblemSet nextProblem(String userId, Integer unit) {

        //user
        User user = userRepository.findOne(userId);

        //number of choices

        //sources. 오답위주 문제 가져오기.
        List<Word> words = new ArrayList(this.getHalfWords(user, unit));
        //TODO 개수가 너무 적다면?!
        Assert.isTrue(words.size() > CHOICE_COUNT);

        Collections.shuffle(words);

        //choices
        List<Word> choices = words.subList(0, CHOICE_COUNT);

        //answer
        Word answer = choices.get(new Random().nextInt(CHOICE_COUNT));

        //info
        Long score = userWordHistRepository.getScore(user);
        Info info = new Info((score == null ? 0 : score.intValue()));

        return new ProblemSet(answer, choices, info);
    }

    /**
     * get words by userid.
     * 사용자의 이력을 뒤져서 오답위주로 가져온다.*
     * *
     *
     * @param user
     * @param unit
     * @return
     */
    private Collection<Word> getHalfWords(User user, Integer unit) {

        //단어만 추출함.
        Set<Word> words = new HashSet<Word>();

        //이력
        List<UserWordHist> userWordHist;
        List<Word> totalWords;
        if (unit == 0) {
            userWordHist = userWordHistRepository.findUserWordHist(user);
            totalWords = wordRepository.findAll();
        } else {
            userWordHist = userWordHistRepository.findUserWordHistByUnit(user, unit);
            totalWords = wordRepository.findByUnit(unit);
        }

        //전체 단어에서 이력에 없는 부분을 채운다.
        //TODO O(n^2)인데... 최적화 할 수 없을까?!
        for (Word w : totalWords) {
            boolean flag = false;
            for (UserWordHist uwh : userWordHist) {
                //표제어가 같은지 확인.
                if (w.getTitleWord().equals(uwh.getWord().getTitleWord())) {
                    flag = true;
                }
            }
            //없으면.
            if (!flag) {
                userWordHist.add(new UserWordHist(user, w, w.getUnit()));
            }
        }

        //점수 순으로 정렬한다.
        Collections.sort(userWordHist, new Comparator<UserWordHist>() {
            @Override
            public int compare(final UserWordHist o1, final UserWordHist o2) {
                if (o1.getScore() > o2.getScore()) {
                    return 1;
                } else if (o1.getScore() < o2.getScore()) {
                    return -1;
                } else {
                    return 0;
                }
            }
        });

        //System.out.println("userWordHist");
        //System.out.println(userWordHist.size());
        //int endIndex = CHOICE_COUNT * 5;
        int endIndex = totalWords.size() / 2;
        if (unit > 0) endIndex = totalWords.size();    //한 유닛을 공부할 때에는 전부 다 노출.
        for (UserWordHist uwh : userWordHist.subList(0, endIndex)) {
            words.add(uwh.getWord());

            //test.
            System.out.println(uwh.getWord().getTitleWord());
        }
        
        
        
        //this.wordRepository.findByTitleWordIn(topHalfWord);
        //System.out.println("words : " + words);
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

    public List<Word> study(Integer unit) {
        List<Word> totalWords = wordRepository.findByUnit(unit);
        return totalWords;
    }
}
