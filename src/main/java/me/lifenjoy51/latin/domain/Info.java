package me.lifenjoy51.latin.domain;

import me.lifenjoy51.latin.service.GoogleSheetService;

/**
 * Created by lifenjoy51 on 2015-03-23.
 */
public class Info {
    private int totalWordCount = 0;
    private int score = 0;
    private int level = 0;

    public Info() {
    }

    public Info(int userScore) {
        this.totalWordCount = GoogleSheetService.TOTAL_COUNT;
        this.level = userScore / totalWordCount;
        this.score = userScore % totalWordCount;
    }

    public int getTotalWordCount() {
        return totalWordCount;
    }

    public void setTotalWordCount(int totalWordCount) {
        this.totalWordCount = totalWordCount;
    }

    public int getScore() {
        return score;
    }

    public void setScore(int score) {
        this.score = score;
    }

    public int getLevel() {
        return level;
    }

    public void setLevel(int level) {
        this.level = level;
    }

    @Override
    public String toString() {
        return "Info{" +
                "totalWordCount=" + totalWordCount +
                ", score=" + score +
                ", level=" + level +
                '}';
    }
}
