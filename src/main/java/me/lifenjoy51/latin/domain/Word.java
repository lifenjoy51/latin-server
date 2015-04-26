package me.lifenjoy51.latin.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import java.io.Serializable;

@Entity
@JsonIgnoreProperties(value = {"handler", "hibernateLazyInitializer"})
public class Word implements Serializable {

    @Column(nullable = true)
    private int unit;   //몇과인지.

    @Column(nullable = true)
    private String audio;    //음성.

    @Column(nullable = true)
    private String korean;  //한글풀이

    @Id
    private String titleWord;   //표제어. verb의 경우 present active.

    @Column(nullable = false)
    private String english; //영어풀이

    @Column(nullable = true)
    private String derivation;    //연관어들.

    public Word() {
    }

    public Word(String titleWord) {
        super();
        this.titleWord = titleWord;
    }

    public Word(String titleWord, String english) {
        super();
        this.titleWord = titleWord;
        this.english = english;
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

    public String getTitleWord() {
        return titleWord;
    }

    public void setTitleWord(String titleWord) {
        this.titleWord = titleWord;
    }

    public String getEnglish() {
        return english;
    }

    public void setEnglish(String english) {
        this.english = english;
    }

    public String getDerivation() {
        return derivation;
    }

    public void setDerivation(String derivation) {
        this.derivation = derivation;
    }

    @Override
    public String toString() {
        return "Word{" +
                "unit=" + unit +
                ", audio='" + audio + '\'' +
                ", korean='" + korean + '\'' +
                ", titleWord='" + titleWord + '\'' +
                ", english='" + english + '\'' +
                ", derivation='" + derivation + '\'' +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Word word = (Word) o;

        if (!titleWord.equals(word.titleWord)) return false;

        return true;
    }

    @Override
    public int hashCode() {
        return titleWord.hashCode();
    }
}
