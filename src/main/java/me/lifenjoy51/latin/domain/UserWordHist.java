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

package me.lifenjoy51.latin.domain;

import javax.persistence.*;
import java.io.Serializable;

@Entity
public class UserWordHist implements Serializable {

    @Id
    @GeneratedValue
    private Long id;

    @ManyToOne(fetch= FetchType.LAZY)
    @JoinColumn(name="user_id")
    private User user;

    @ManyToOne(fetch= FetchType.LAZY)
    @JoinColumn(name="word_id")
    private Word word;
    
    @Column
    private int score;

    public UserWordHist() {
    }

	public UserWordHist(User user, Word word, int score) {
		this.user = user;
		this.word = word;
        this.score = score;
	}

    public UserWordHist(User user, Word word, long score) {
        this.user = user;
        this.word = word;
        this.score = (int) score;
    }

    public UserWordHist(String userId, String titleWord, Long score) {
        this.user = new User(userId);
        this.word = new Word(titleWord);
        this.score = score.intValue();
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public Word getWord() {
        return word;
    }

    public void setWord(Word word) {
        this.word = word;
    }

    public int getScore() {
        return score;
    }

    public void setScore(int score) {
        this.score = score;
    }

    @Override
    public String toString() {
        return "UserWordHist{" +
                "id=" + id +
                ", user=" + user +
                ", word=" + word +
                ", score=" + score +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        UserWordHist that = (UserWordHist) o;

        if (score != that.score) return false;
        if (user != null ? !user.equals(that.user) : that.user != null) return false;
        if (!word.equals(that.word)) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = user != null ? user.hashCode() : 0;
        result = 31 * result + word.hashCode();
        result = 31 * result + score;
        return result;
    }
}
