package entities;

import java.io.Serializable;
import java.util.List;

import utils.JSONifier;

public class Test implements Serializable {

    private String name;
    private Category category;
    private String duration;
    private String questionsNo;
    private String retries;
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

    public String getDuration() {
        return duration;
    }

    public void setDuration(String duration) {
        this.duration = duration;
    }

    public String getQuestionsNo() {
        return questionsNo;
    }

    public void setQuestionsNo(String questionsNo) {
        this.questionsNo = questionsNo;
    }

    public String getRetries() {
        return retries;
    }

    public void setRetries(String retries) {
        this.retries = retries;
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

    public String toJSON(){
        return JSONifier.StringToJSON(new String[]{"name", "category", "duration", "questionsNo",
        "retries", "backwards", "privacy"}, new String[]{name, category.getName(), duration, questionsNo, retries,
        backwards.toString(), privacy.toString()});
    }
}
