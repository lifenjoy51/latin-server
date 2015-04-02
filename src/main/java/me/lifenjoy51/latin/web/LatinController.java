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


import me.lifenjoy51.latin.domain.Problem;
import me.lifenjoy51.latin.domain.User;
import me.lifenjoy51.latin.service.GoogleSheetService;
import me.lifenjoy51.latin.service.LatinConverter;
import me.lifenjoy51.latin.service.UserService;
import me.lifenjoy51.latin.service.WordService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
public class LatinController {

    @Autowired
    private UserService userService;

    @Autowired
    private WordService wordService;

    @Autowired
    private GoogleSheetService googleSheetService;

    @RequestMapping("/")
    @ResponseBody
    @Transactional(readOnly = true)
    public User helloWorld() {
        return this.userService.getUser("lifenjoy51");
    }

    @RequestMapping("/sync")
    @ResponseBody
    @Transactional(readOnly = true)
    public ResponseEntity<String> sync() {
        this.googleSheetService.sync();
        return new ResponseEntity<String>(HttpStatus.BAD_REQUEST);
    }

    @RequestMapping("/next")
    @ResponseBody
    @Transactional(readOnly = true)
    public Problem next(@RequestParam(value = "userId") String userId,
                        @RequestParam(value = "titleWord") String titleWord,
                        @RequestParam(value = "score") Integer score,
                        @RequestParam(value = "unit") Integer unit) {
        //정답이거나 오답인 경우에 기록 저장.
        if (score != 0) wordService.saveHist(userId, titleWord, score);
        //문제 뽑기.
        Problem p = this.wordService.nextProblem(userId, unit);
        return p;
    }

    @RequestMapping("/convert")
    @ResponseBody
    @Transactional(readOnly = true)
    public String convert(@RequestParam(value = "content") String content) {
        return LatinConverter.convert(content);
    }

    @RequestMapping("/register")
    @ResponseBody
    @Transactional(readOnly = true)
    public ResponseEntity<String> register(@RequestParam(value = "userId") String userId) {
        userService.save(userId);
        return new ResponseEntity<String>(HttpStatus.OK);
    }

    @RequestMapping("/units")
    @ResponseBody
    @Transactional(readOnly = true)
    public String units() {
        return String.valueOf(googleSheetService.MAX_UNIT);
    }


}
