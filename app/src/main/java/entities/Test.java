package entities;

import java.io.Serializable;
import java.util.List;

import utils.JSONifier;

public class Test implements Serializable {

    private int id;
    private String name;
    private String description;
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

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
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

    @Override
    public String toString() {
        return name;
    }

    public String toJSON(){
        if(id != 0){
            return JSONifier.StringToJSON(new String[]{"id", "name", "description", "category_id", "duration", "questionsNumber",
                    "retries", "backwards", "privacy", "feedback"},
                    new String[]{String.valueOf(id), name, description,
                    String.valueOf(category.getId()),
                    String.valueOf(duration),
                    String.valueOf(questionsNo),
                    String.valueOf(retries),
                    backwards.toString(),
                    privacy.toString(),
                    String.valueOf(feedback)});
        }
        return JSONifier.StringToJSON(new String[]{"name", "description", "category_id", "duration", "questionsNumber",
        "retries", "backwards", "privacy", "feedback"}, new String[]{name, description,
                String.valueOf(category.getId()),
                String.valueOf(duration),
                String.valueOf(questionsNo),
                String.valueOf(retries),
                backwards.toString(),
                privacy.toString(),
                String.valueOf(feedback)});
    }

    @Override
    public String toString() {
        return "Test{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", description='" + description + '\'' +
                ", category=" + category.getId() +
                ", duration=" + duration +
                ", questionsNo=" + questionsNo +
                ", retries=" + retries +
                ", feedback=" + feedback +
                ", backwards=" + backwards +
                ", privacy=" + privacy +
                '}';
    }
}
