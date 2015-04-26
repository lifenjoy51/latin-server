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
import me.lifenjoy51.latin.domain.UserSentenceHist;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

interface UserSentenceHistRepository extends JpaRepository<UserSentenceHist, Long> {

    @Query("select new UserSentenceHist(r.user, r.sentence, sum(r.score)) "
            + "from UserSentenceHist r " +
            "where r.user = ?1 " +
            "group by r.user, r.sentence ")
    List<UserSentenceHist> findUserSentenceHist(User user);

    @Query("select sum(r.score) as score " +
            "from UserSentenceHist r " +
            "where r.user = ?1 " +
            "group by r.user ")
    Long getScore(User user);

    @Query("select new UserSentenceHist(r.user, r.sentence, sum(r.score)) "
            + "from UserSentenceHist r " +
            "where r.user = ?1 " +
            "and r.sentence.unit = ?2 " +
            "group by r.user, r.sentence ")
    List<UserSentenceHist> findUserSentenceHistByUnit(User user, Integer unit);
}
