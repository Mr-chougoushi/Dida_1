package com.qf.pojo;

public class Weekly {
    private int wkId;
    private String stuName;
    private String title;
    private String weekDate;
    private String text;
    private Double score;

    @Override
    public String toString() {
        return "Weekly{" +
                "wkId=" + wkId +
                ", stuName='" + stuName + '\'' +
                ", title='" + title + '\'' +
                ", weekDate='" + weekDate + '\'' +
                ", text='" + text + '\'' +
                ", score=" + score +
                '}';
    }

    public int getWkId() {
        return wkId;
    }

    public void setWkId(int wkId) {
        this.wkId = wkId;
    }

    public String getStuName() {
        return stuName;
    }

    public void setStuName(String stuName) {
        this.stuName = stuName;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getWeekDate() {
        return weekDate;
    }

    public void setWeekDate(String weekDate) {
        this.weekDate = weekDate;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public Double getScore() {
        return score;
    }

    public void setScore(Double score) {
        this.score = score;
    }
}
