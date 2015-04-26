package me.lifenjoy51.latin.domain;

import java.util.List;

/**
 * Created by lifenjoy51 on 2015-03-19.
 */
public class ProblemSet {

    private Problem answer;
    private List<Problem> choices;
    private Info info;

    public ProblemSet() {
    }

    public ProblemSet(Word answer, List<Word> choices) {
        this.answer = new Problem(answer);
        this.choices = Problem.listByWords(choices);
    }

    public ProblemSet(Sentence answer, List<Sentence> choices) {
        this.answer = new Problem(answer);
        this.choices = Problem.listBySentence(choices);
    }

    public ProblemSet(Word answer, List<Word> choices, Info info) {
        this.setAnswer(new Problem(answer));
        this.setChoices(Problem.listByWords(choices));
        this.setInfo(info);
    }

    public ProblemSet(Sentence answer, List<Sentence> choices, Info info) {
        this.setAnswer(new Problem(answer));
        this.setChoices(Problem.listBySentence(choices));
        this.setInfo(info);
    }

    public Problem getAnswer() {
        return answer;
    }

    public void setAnswer(Problem answer) {
        this.answer = answer;
    }

    public List<Problem> getChoices() {
        return choices;
    }

    public void setChoices(List<Problem> choices) {
        this.choices = choices;
    }

    public Info getInfo() {
        return info;
    }

    public void setInfo(Info info) {
        this.info = info;
    }
}
