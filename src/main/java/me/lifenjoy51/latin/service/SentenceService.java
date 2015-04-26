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

@Component("sentenceService")
@Transactional
public class SentenceService {

    final int CHOICE_COUNT = 4;
    private final SentenceRepository sentenceRepository;
    private final UserSentenceHistRepository userSentenceHistRepository;
    private final UserRepository userRepository;


    @Autowired
    public SentenceService(SentenceRepository sentenceRepository, UserSentenceHistRepository userSentenceHistRepository, UserRepository userRepository) {
        this.sentenceRepository = sentenceRepository;
        this.userSentenceHistRepository = userSentenceHistRepository;
        this.userRepository = userRepository;
    }

    public Sentence save(Sentence sentence) {
        return this.sentenceRepository.saveAndFlush(sentence);
    }

    public Sentence getSentence(String name) {
        Assert.hasLength(name, "Name must not be empty");
        return this.sentenceRepository.findOne(name);
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
        List<Sentence> sentences = new ArrayList(this.getHalfSentences(user, unit));
        //TODO 개수가 너무 적다면?!
        Assert.isTrue(sentences.size() > CHOICE_COUNT);

        Collections.shuffle(sentences);

        //choices
        List<Sentence> choices = sentences.subList(0, CHOICE_COUNT);

        //answer
        Sentence answer = choices.get(new Random().nextInt(CHOICE_COUNT));

        //info
        Long score = userSentenceHistRepository.getScore(user);
        Info info = new Info((score == null ? 0 : score.intValue()));

        return new ProblemSet(answer, choices, info);
    }

    /**
     * get sentences by userid.
     * 사용자의 이력을 뒤져서 오답위주로 가져온다.*
     * *
     *
     * @param user
     * @param unit
     * @return
     */
    private Collection<Sentence> getHalfSentences(User user, Integer unit) {

        //단어만 추출함.
        Set<Sentence> sentences = new HashSet<Sentence>();

        //이력
        List<UserSentenceHist> userSentenceHist;
        List<Sentence> totalSentences;
        if (unit == 0) {
            userSentenceHist = userSentenceHistRepository.findUserSentenceHist(user);
            totalSentences = sentenceRepository.findAll();
        } else {
            userSentenceHist = userSentenceHistRepository.findUserSentenceHistByUnit(user, unit);
            totalSentences = sentenceRepository.findByUnit(unit);
        }

        //전체 단어에서 이력에 없는 부분을 채운다.
        //TODO O(n^2)인데... 최적화 할 수 없을까?!
        for (Sentence w : totalSentences) {
            boolean flag = false;
            for (UserSentenceHist uwh : userSentenceHist) {
                //표제어가 같은지 확인.
                if (w.getLatin().equals(uwh.getSentence().getLatin())) {
                    flag = true;
                }
            }
            //없으면.
            if (!flag) {
                userSentenceHist.add(new UserSentenceHist(user, w, w.getUnit()));
            }
        }

        //점수 순으로 정렬한다.
        Collections.sort(userSentenceHist, new Comparator<UserSentenceHist>() {
            @Override
            public int compare(final UserSentenceHist o1, final UserSentenceHist o2) {
                if (o1.getScore() > o2.getScore()) {
                    return 1;
                } else if (o1.getScore() < o2.getScore()) {
                    return -1;
                } else {
                    return 0;
                }
            }
        });

        //System.out.println("userSentenceHist");
        //System.out.println(userSentenceHist.size());
        //int endIndex = CHOICE_COUNT * 5;
        int endIndex = totalSentences.size() / 2;
        if (unit > 0) endIndex = totalSentences.size();    //한 유닛을 공부할 때에는 전부 다 노출.
        for (UserSentenceHist uwh : userSentenceHist.subList(0, endIndex)) {
            sentences.add(uwh.getSentence());

            //test.
            //System.out.println(uwh.getSentence().getTitleSentence());
        }


        //this.sentenceRepository.findByTitleSentenceIn(topHalfSentence);
        //System.out.println("sentences : " + sentences);
        return sentences;
    }

    /**
     * record problem solving history.
     * *
     *
     * @param userId
     * @param titleSentence
     * @param score
     */
    public void saveHist(String userId, String titleSentence, Integer score) {
        if (StringUtils.isEmpty(titleSentence)) return;
        User user = userRepository.findOne(userId);
        Sentence sentence = sentenceRepository.findOne(titleSentence);
        userSentenceHistRepository.saveAndFlush(new UserSentenceHist(user, sentence, score));
    }

    public List<Sentence> study(Integer unit) {
        List<Sentence> totalSentences = sentenceRepository.findByUnit(unit);
        return totalSentences;
    }
}
