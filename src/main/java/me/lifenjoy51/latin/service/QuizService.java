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
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

import java.util.*;

@Service("quizService")
@Transactional
public class QuizService {

    private final int CHOICE_COUNT = 4;

    private final SentenceRepository sentenceRepository;
    private final UserSentenceHistRepository userSentenceHistRepository;
    private final WordRepository wordRepository;
    private final UserWordHistRepository userWordHistRepository;
    private final UserRepository userRepository;

    @Autowired
    public QuizService(SentenceRepository sentenceRepository, UserSentenceHistRepository userSentenceHistRepository,
                       WordRepository wordRepository, UserWordHistRepository userWordHistRepository, UserRepository userRepository) {
        this.sentenceRepository = sentenceRepository;
        this.userSentenceHistRepository = userSentenceHistRepository;
        this.wordRepository = wordRepository;
        this.userWordHistRepository = userWordHistRepository;
        this.userRepository = userRepository;
    }

    /**
     * record problem solving history.
     * *
     *
     * @param userId
     * @param latin
     * @param score
     */
    public void saveHist(String userId, String latin, Integer score, String tp) {
        if (StringUtils.isEmpty(latin)) return;
        User user = userRepository.findOne(userId);

        //유형별 분류. if가 최선인가?
        if ("sentence".equalsIgnoreCase(tp)) {
            Sentence sentence = sentenceRepository.findByLatin(latin);
            userSentenceHistRepository.saveAndFlush(new UserSentenceHist(user, sentence, score));
        } else if ("word".equalsIgnoreCase(tp)) {
            Word word = wordRepository.findOne(latin);
            userWordHistRepository.saveAndFlush(new UserWordHist(user, word, score));
        }
    }

    /**
     * 해당 유닛의 모든 단어로 문제를 만든다.*
     *
     * @param userId
     * @param unit
     * @return
     */
    public List<Quiz> getWords(String userId, Integer unit) {

        //전체 단어를 준비.
        List<Word> totalWords = wordRepository.findByUnit(unit);
        //챕터가 0일 때. 전부 불러온다.
        if (unit == 0) totalWords = wordRepository.findAll();
        Collections.shuffle(totalWords);

        // 전체 단어에 대해서 문제를 생성한다.
        List<Quiz> quizs = new ArrayList<Quiz>();
        for (Word w : totalWords) {

            //문제를 만들기 위해 단어를 선택한다. 보기를 위해 여러개 선택함.
            Set<Word> words = new HashSet<Word>();

            // 우선 한개의 답은 무조건 들어가야한다
            words.add(w);

            // 그리고 나머지 보기들이 랜덤하게 들어간다.
            // 문제 개수만큼 채워질 때 까지.
            while (words.size() < CHOICE_COUNT) {
                Word choice = totalWords.get(new Random().nextInt(totalWords.size()));
                words.add(choice);
            }

            //선택된 4개의 단어로 문제 선택지를 생성한다.
            List<QuizChoice> quizChoices = QuizChoice.listByWords(words);

            //문제선택지를 가지고 문제를 만든다.
            quizs.add(new Quiz(QuizType.Word, quizChoices, new QuizChoice(w)));

        }

        //전체일때는 30개만 자른다.
        if(unit == 0) quizs = quizs.subList(0, quizs.size() > 30 ? 30 : quizs.size());
        
        return quizs;
    }

    /**
     * 전체 문장을 뽑아서 문제를 만든다. 해당 유닛에 대한!*
     *
     * @param userId
     * @param unit
     * @return
     */
    public List<Quiz> getSentences(String userId, Integer unit) {

        List<Sentence> totalSentences = sentenceRepository.findByUnit(unit);
        //챕터가 0일 때. 전부 불러온다.
        if (unit == 0) totalSentences = sentenceRepository.findAll();
        Collections.shuffle(totalSentences);

        // 전체 단어에 대해서 문제를 생성한다.
        List<Quiz> quizs = new ArrayList<Quiz>();
        for (Sentence s : totalSentences) {

            //문제를 만들기 위해 단어를 선택한다. 보기를 위해 여러개 선택함.
            Set<Sentence> sentences = new HashSet<Sentence>();

            // 우선 한개의 답은 무조건 들어가야한다
            sentences.add(s);

            // 그리고 나머지 보기들이 랜덤하게 들어간다.
            // 문제 개수만큼 채워질 때 까지.
            while (sentences.size() < CHOICE_COUNT) {
                Sentence choice = totalSentences.get(new Random().nextInt(totalSentences.size()));
                sentences.add(choice);
            }

            //선택된 4개의 단어로 문제 선택지를 생성한다.
            List<QuizChoice> quizChoices = QuizChoice.listBySentence(sentences);

            //문제선택지를 가지고 문제를 만든다.
            quizs.add(new Quiz(QuizType.Sentence, quizChoices, new QuizChoice(s)));

        }

        //전체일때는 30개만 자른다.
        if(unit == 0) quizs = quizs.subList(0, quizs.size() > 30 ? 30 : quizs.size());

        return quizs;
    }

    /**
     * 단어와 문장 전부 받아온다.*
     *
     * @param userId
     * @param unit
     * @return
     */
    public List<Quiz> getAll(String userId, Integer unit) {
        List<Quiz> quizWords = this.getWords(userId, unit);
        List<Quiz> quizSentences = this.getSentences(userId, unit);

        List<Quiz> quizAll = new ArrayList<Quiz>();
        quizAll.addAll(quizWords);
        quizAll.addAll(quizSentences);

        Collections.shuffle(quizAll);

        List<Quiz> subList = quizAll.subList(0, quizAll.size() > 30 ? 30 : quizAll.size());

        return subList;
    }
}
