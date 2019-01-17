package entities;

import java.io.Serializable;

import utils.JSONifier;

public class Question implements Serializable {

    private int id;
    private String question;
    private String ans1;
    private String ans2;
    private String ans3;
    private String ans4;
    private String correct;
    private String feedback;
    private String photo;
    private int multiple;
    private int open;
    private int duration;
    private Category category;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getQuestion() {
        return question;
    }

    public void setQuestion(String question) {
        this.question = question;
    }

    public String getAns1() {
        return ans1;
    }

    public void setAns1(String ans1) {
        this.ans1 = ans1;
    }

    public String getAns2() {
        return ans2;
    }

    public void setAns2(String ans2) {
        this.ans2 = ans2;
    }

    public String getAns3() {
        return ans3;
    }

    public void setAns3(String ans3) {
        this.ans3 = ans3;
    }

    public String getAns4() {
        return ans4;
    }

    public void setAns4(String ans4) {
        this.ans4 = ans4;
    }

    public String getCorrect() {
        return correct;
    }

    public void setCorrect(String correct) {
        this.correct = correct;
    }

    public String getFeedback() {
        return feedback;
    }

    public void setFeedback(String feedback) {
        this.feedback = feedback;
    }

    public String getPhoto() {
        return photo;
    }

    public void setPhoto(String photo) {
        this.photo = photo;
    }

    public int getMultiple() {
        return multiple;
    }

    public void setMultiple(int multiple) {
        this.multiple = multiple;
    }

    public int getOpen() {
        return open;
    }

    public void setOpen(int open) {
        this.open = open;
    }

    public int getDuration() {
        return duration;
    }

    public void setDuration(int duration) {
        this.duration = duration;
    }

    public Category getCategory() {
        return category;
    }

    public void setCategory(Category category) {
        this.category = category;
    }

    @Override
    public boolean equals(Object obj) {
        return (((Question)obj).getId() == this.getId()) ? true : false;
    }

    public String toJSON(){
        return JSONifier.StringToJSON(new String[]{"question", "ans1", "ans2", "ans3", "ans4",
                "correct", "feedback", "category_id", "multiple","open","duration"},
                new String[]{question, ans1,
                ans2, ans3, ans4, correct, feedback,
                String.valueOf(category.getId()),
                String.valueOf(multiple),
                String.valueOf(open),
                String.valueOf(duration)});
    }

    @Override
    public String toString() {
        return "Question{" +
                "id=" + id +
                ", question='" + question + '\'' +
                ", ans1='" + ans1 + '\'' +
                ", ans2='" + ans2 + '\'' +
                ", ans3='" + ans3 + '\'' +
                ", ans4='" + ans4 + '\'' +
                ", correct='" + correct + '\'' +
                ", feedback='" + feedback + '\'' +
                ", photo='" + photo + '\'' +
                ", multiple=" + multiple +
                ", open=" + open +
                ", duration=" + duration +
                ", category=" + category.getId() +
                '}';
    }
}
