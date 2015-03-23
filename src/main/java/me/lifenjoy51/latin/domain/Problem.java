package me.lifenjoy51.latin.domain;

import java.util.List;

/**
 * Created by lifenjoy51 on 2015-03-19.
 */
public class Problem {

    private Word answer;
    private List<Word> choices;
    private Info info;

    public Problem() {
    }

    public Problem(Word answer, List<Word> choices) {
        this.answer = answer;
        this.choices = choices;
    }

    public Problem(Word answer, List<Word> choices, Info info) {
        this(answer, choices);
        this.info = info;
    }

    public Word getAnswer() {
        return answer;
    }

    public void setAnswer(Word answer) {
        this.answer = answer;
    }

    public List<Word> getChoices() {
        return choices;
    }

    public void setChoices(List<Word> choices) {
        this.choices = choices;
    }

    public Info getInfo() {
        return info;
    }

    public void setInfo(Info info) {
        this.info = info;
    }
}
