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

import me.lifenjoy51.latin.domain.Word;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Collection;
import java.util.List;

interface WordRepository extends JpaRepository<Word, String> {
    
/*
    @Query("select new me.lifenjoy51.data.jpa.domain.RatingCount(r.rating, count(r)) "
			+ "from Review r where r.hotel = ?1 group by r.rating order by r.rating DESC")
	List<UserWordHist> findRatingCounts(Sentence sentence);*/
    
    List<Word> findByTitleWordIn(Collection<String> titleWords);
}
