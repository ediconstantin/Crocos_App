package entities;

import java.io.Serializable;
import java.util.List;

import utils.JSONifier;

public class Test implements Serializable {

    private int id;
    private String name;
    private Category category;
    private int duration;
    private int questionsNo;
    private int retries;
    private int feedback;
    private Boolean backwards;
    private Boolean privacy;
    private List<Question> questions;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Category getCategory() {
        return category;
    }

    public void setCategory(Category category) {
        this.category = category;
    }

    public int getDuration() {
        return duration;
    }

    public void setDuration(int duration) {
        this.duration = duration;
    }

    public Boolean getBackwards() {
        return backwards;
    }

    public void setBackwards(Boolean backwards) {
        this.backwards = backwards;
    }

    public Boolean getPrivacy() {
        return privacy;
    }

    public void setPrivacy(Boolean privacy) {
        this.privacy = privacy;
    }

    public int getQuestionsNo() {
        return questionsNo;
    }

    public void setQuestionsNo(int questionsNo) {
        this.questionsNo = questionsNo;
    }

    public int getRetries() {
        return retries;
    }

    public void setRetries(int retries) {
        this.retries = retries;
    }

    public int getFeedback() {
        return feedback;
    }

    public void setFeedback(int feedback) {
        this.feedback = feedback;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public List<Question> getQuestions() {
        return questions;
    }

    public void setQuestions(List<Question> questions) {
        this.questions = questions;
    }

    public String toJSON(){
        return JSONifier.StringToJSON(new String[]{"name", "category", "duration", "questionsNo",
        "retries", "backwards", "privacy"}, new String[]{name, category.getName(), String.valueOf(duration),
                String.valueOf(questionsNo), String.valueOf(retries),
        backwards.toString(), privacy.toString()});
    }
}