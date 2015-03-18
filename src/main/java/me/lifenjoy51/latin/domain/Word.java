package me.lifenjoy51.latin.domain;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import java.io.Serializable;

@Entity
public class Word implements Serializable {

	@Id
	private String titleWord;   //표제어. verb의 경우 present active.

	@Column(nullable = true)
	private int unit;   //몇과인지.

	@Column(nullable = true)
	private String partOfSpeech;    //품사.

	@Column(nullable = true)
	private String presentInfinitive;   //현재 부정형.

	@Column(nullable = true)
	private String perfectActive;   //과거능동.

    @Column(nullable = true)
    private String supine;  //?? 동명사?

    @Column(nullable = false)
    private String korean;  //한글풀이

    @Column(nullable = false)
    private String english; //영어풀이

    @Column(nullable = true)
    private String more;    //연관어들.

	public Word() {
	}

	public Word(String titleWord, String korean, String english) {
		super();
		this.titleWord = titleWord;
		this.korean = korean;
        this.english = english;
	}

    public String getTitleWord() {
        return titleWord;
    }

    public void setTitleWord(String titleWord) {
        this.titleWord = titleWord;
    }

    public int getUnit() {
        return unit;
    }

    public void setUnit(int unit) {
        this.unit = unit;
    }

    public String getPartOfSpeech() {
        return partOfSpeech;
    }

    public void setPartOfSpeech(String partOfSpeech) {
        this.partOfSpeech = partOfSpeech;
    }

    public String getPresentInfinitive() {
        return presentInfinitive;
    }

    public void setPresentInfinitive(String presentInfinitive) {
        this.presentInfinitive = presentInfinitive;
    }

    public String getPerfectActive() {
        return perfectActive;
    }

    public void setPerfectActive(String perfectActive) {
        this.perfectActive = perfectActive;
    }

    public String getSupine() {
        return supine;
    }

    public void setSupine(String supine) {
        this.supine = supine;
    }

    public String getKorean() {
        return korean;
    }

    public void setKorean(String korean) {
        this.korean = korean;
    }

    public String getEnglish() {
        return english;
    }

    public void setEnglish(String english) {
        this.english = english;
    }

    public String getMore() {
        return more;
    }

    public void setMore(String more) {
        this.more = more;
    }

    @Override
    public String toString() {
        return "Word{" +
                "titleWord='" + titleWord + '\'' +
                ", unit=" + unit +
                ", partOfSpeech='" + partOfSpeech + '\'' +
                ", presentInfinitive='" + presentInfinitive + '\'' +
                ", perfectActive='" + perfectActive + '\'' +
                ", supine='" + supine + '\'' +
                ", korean='" + korean + '\'' +
                ", english='" + english + '\'' +
                ", more='" + more + '\'' +
                '}';
    }
}
