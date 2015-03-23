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

import me.lifenjoy51.latin.domain.User;
import me.lifenjoy51.latin.domain.UserWordHist;
import me.lifenjoy51.latin.domain.Word;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;

@Component("userService")
@Transactional
public class UserService {

    private final UserRepository userRepository;
    
    private final UserWordHistRepository userWordHistRepository;
    
    private final WordRepository wordRepository;

    @Autowired
    public UserService(UserRepository userRepository, UserWordHistRepository userWordHistRepository, WordRepository wordRepository) {
        this.userRepository = userRepository;
        this.userWordHistRepository = userWordHistRepository;
        this.wordRepository = wordRepository;
    }

    public User getUser(String userId) {
        Assert.notNull(userId, "Name must not be null");
        return this.userRepository.findOne(userId);
    }

    public void save(String userId) {
        
        User user = new User(userId);
        user = userRepository.saveAndFlush(user);
        
        //add empty hist.
        for(Word w : wordRepository.findAll()){
            userWordHistRepository.saveAndFlush(new UserWordHist(user, w, 0));
        }
                
    }
}
