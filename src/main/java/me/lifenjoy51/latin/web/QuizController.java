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

package me.lifenjoy51.latin.web;


import me.lifenjoy51.latin.domain.Quiz;
import me.lifenjoy51.latin.service.QuizService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;

@Controller
@RequestMapping("/api/v1")
public class QuizController {

    @Autowired
    private QuizService quizService;

    @RequestMapping(value = "/quiz/words")
    @ResponseBody
    @Transactional(readOnly = true)
    public List<Quiz> quizWords(@RequestParam(value = "userId") String userId,
                             @RequestParam(value = "unit") Integer unit) {
        //문제 뽑기.
        List<Quiz> quizzes = this.quizService.getWords(userId, unit);
        return quizzes;
    }

    @RequestMapping(value = "/quiz/sentences")
    @ResponseBody
    @Transactional(readOnly = true)
    public List<Quiz> quizSentences(@RequestParam(value = "userId") String userId,
                                 @RequestParam(value = "unit") Integer unit) {
        //문제 뽑기.
        List<Quiz> quizzes = this.quizService.getSentences(userId, unit);
        return quizzes;
    }

    @RequestMapping(value = "/quiz/all")
    @ResponseBody
    @Transactional(readOnly = true)
    public List<Quiz> quizAll(@RequestParam(value = "userId") String userId,
                           @RequestParam(value = "unit") Integer unit) {
        //문제 뽑기.
        List<Quiz> quizzes = this.quizService.getAll(userId, unit);
        return quizzes;
    }

    @RequestMapping(value = "/quiz/hist", method = RequestMethod.POST)
    @ResponseBody
    @Transactional(readOnly = true)
    public ResponseEntity<String> quizHist(@RequestParam(value = "userId") String userId,
                                           @RequestParam(value = "latin") String latin,
                                           @RequestParam(value = "score") Integer score,
                                           @RequestParam(value = "tp") String tp) {
        //정답이거나 오답인 경우에 기록 저장.
        quizService.saveHist(userId, latin, score, tp);
        return new ResponseEntity<String>(HttpStatus.OK);
    }

}
