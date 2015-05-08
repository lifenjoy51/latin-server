package me.lifenjoy51.latin.domain;

import java.util.List;
import java.util.Random;

/**
 * Created by lifenjoy51 on 2015-03-19.
 */
public class Quiz {

    private QuizChoice answer;  //답
    private List<QuizChoice> choices;   //선택지
    private QuizType quizType;  //문제유형

    public Quiz() {
    }

    public Quiz(QuizType quizType, List<QuizChoice> choices, QuizChoice answer) {
        this.setQuizType(quizType);
        this.setAnswer(answer);
        this.setChoices(choices);
    }

    public QuizChoice getAnswer() {
        return answer;
    }

    public void setAnswer(QuizChoice answer) {
        this.answer = answer;
    }

    public List<QuizChoice> getChoices() {
        return choices;
    }

    public void setChoices(List<QuizChoice> choices) {
        this.choices = choices;
    }

    public QuizType getQuizType() {
        return quizType;
    }

    public void setQuizType(QuizType quizType) {
        this.quizType = quizType;
    }

    @Override
    public String toString() {
        return "Quiz{" +
                "answer=" + answer +
                ", choices=" + choices +
                ", quizType=" + quizType +
                '}';
    }
}
