package me.lifenjoy51.latin.domain;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

/**
 * Created by lifenjoy51 on 2015-04-26.
 */
public class QuizChoice {

    private int unit;   //몇과인지.

    private String audio;    //음성.

    private String korean;  //한글풀이

    private String latin;   //표제어. verb의 경우 present active.

    private String english; //영어풀이

    private String etc;    //기타정보 (단어의 경우 파생어).


    /**
     * 단어를 갖고 문제를 만듬.*
     *
     * @param answer
     */
    public QuizChoice(Word answer) {
        this.setUnit(answer.getUnit());
        this.setAudio(answer.getAudio());
        this.setKorean(answer.getKorean());
        this.setLatin(answer.getTitleWord());
        this.setEnglish(answer.getEnglish());
        this.setEtc(answer.getDerivation());
    }

    /**
     * 문장으로 문제를 만듬.*
     *
     * @param answer
     */
    public QuizChoice(Sentence answer) {
        this.setUnit(answer.getUnit());
        this.setLatin(answer.getLatin());
        this.setEnglish(answer.getEnglish());
    }

    public static List<QuizChoice> listByWords(Collection<Word> choices) {
        List<QuizChoice> list = new ArrayList<QuizChoice>();
        for (Word w : choices) {
            list.add(new QuizChoice(w));
        }
        return list;
    }

    public static List<QuizChoice> listBySentence(Collection<Sentence> choices) {
        List<QuizChoice> list = new ArrayList<QuizChoice>();
        for (Sentence s : choices) {
            list.add(new QuizChoice(s));
        }
        return list;
    }

    public int getUnit() {
        return unit;
    }

    public void setUnit(int unit) {
        this.unit = unit;
    }

    public String getAudio() {
        return audio;
    }

    public void setAudio(String audio) {
        this.audio = audio;
    }

    public String getKorean() {
        return korean;
    }

    public void setKorean(String korean) {
        this.korean = korean;
    }

    public String getLatin() {
        return latin;
    }

    public void setLatin(String latin) {
        this.latin = latin;
    }

    public String getEnglish() {
        return english;
    }

    public void setEnglish(String english) {
        this.english = english;
    }

    public String getEtc() {
        return etc;
    }

    public void setEtc(String etc) {
        this.etc = etc;
    }

    @Override
    public String toString() {
        return "QuizChoice{" +
                "unit=" + unit +
                ", audio='" + audio + '\'' +
                ", korean='" + korean + '\'' +
                ", latin='" + latin + '\'' +
                ", english='" + english + '\'' +
                ", etc='" + etc + '\'' +
                '}';
    }
}
