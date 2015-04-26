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
public class UserSentenceHist implements Serializable {

    @Id
    @GeneratedValue
    private Long id;

    @ManyToOne(fetch= FetchType.LAZY)
    @JoinColumn(name="user_id")
    private User user;

    @ManyToOne(fetch= FetchType.LAZY)
    @JoinColumn(name="sentence_id")
    private Sentence sentence;

    @Column
    private int score;

    public UserSentenceHist() {
    }

	public UserSentenceHist(User user, Sentence sentence, int score) {
		this.user = user;
		this.sentence = sentence;
        this.score = score;
	}

    public UserSentenceHist(User user, Sentence sentence, long score) {
        this.user = user;
        this.sentence = sentence;
        this.score = (int) score;
    }

    public UserSentenceHist(String userId, String sentenceId, Long score) {
        this.user = new User(userId);
        this.sentence = new Sentence(sentenceId);
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

    public Sentence getSentence() {
        return sentence;
    }

    public void setSentence(Sentence sentence) {
        this.sentence = sentence;
    }

    public int getScore() {
        return score;
    }

    public void setScore(int score) {
        this.score = score;
    }

    @Override
    public String toString() {
        return "UserSentenceHist{" +
                "id=" + id +
                ", user=" + user +
                ", sentence=" + sentence +
                ", score=" + score +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        UserSentenceHist that = (UserSentenceHist) o;

        if (score != that.score) return false;
        if (user != null ? !user.equals(that.user) : that.user != null) return false;
        if (!sentence.equals(that.sentence)) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = user != null ? user.hashCode() : 0;
        result = 31 * result + sentence.hashCode();
        result = 31 * result + score;
        return result;
    }
}
