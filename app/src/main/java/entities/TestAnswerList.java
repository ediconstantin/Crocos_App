package entities;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class TestAnswerList implements Serializable {

    List<TestAnswer> testAnswers = new ArrayList<>();

    public List<TestAnswer> getTestAnswers() {
        return testAnswers;
    }

    public void setTestAnswers(List<TestAnswer> testAnswers) {
        this.testAnswers = testAnswers;
    }

    public void append(TestAnswer testAnswer){
        testAnswers.add(testAnswer);
    }
}
