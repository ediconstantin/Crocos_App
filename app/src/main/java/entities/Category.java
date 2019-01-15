package entities;


import java.io.Serializable;

public class Category implements Serializable {

    private int id;
    private String name;
    private String description;
    private String photo;
    private Test[] tests;
    private Question[] questions;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public Test[] getTests() {
        return tests;
    }

    public void setTests(Test[] tests) {
        this.tests = tests;
    }

    public Question[] getQuestions() {
        return questions;
    }

    public void setQuestions(Question[] questions) {
        this.questions = questions;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getPhoto() {
        return photo;
    }

    public void setPhoto(String photo) {
        this.photo = photo;
    }

    @Override
    public boolean equals(Object obj) {
        return ((Category)obj).getId() == this.getId() ? true : false;
    }

    @Override
    public String toString() {
        return "\"" + name + "\"";
    }
}
