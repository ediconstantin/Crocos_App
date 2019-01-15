package entities;

public class questionAnswer {

    int answer;
    String answerString;

    public questionAnswer(int answer){
        this.answer = answer;

        switch(answer){
            case 1:
                answerString = "ans1";
                break;
            case 2:
                answerString = "ans2";
                break;
            case 3:
                answerString = "ans3";
                break;
            case 4:
                answerString = "ans4";
                break;
            default:
                answerString = "ans1";
        }
    }

    public int getAnswer() {
        return answer;
    }

    public void setAnswer(int answer) {
        this.answer = answer;

        switch(answer){
            case 1:
                answerString = "ans1";
                break;
            case 2:
                answerString = "ans2";
                break;
            case 3:
                answerString = "ans3";
                break;
            case 4:
                answerString = "ans4";
                break;
            default:
                answerString = "ans1";
        }
    }

    public String getAnswerString() {
        return answerString;
    }

    public void setAnswerString(String answerString) {
        this.answerString = answerString;
    }

    @Override
    public String toString() {
        return String.valueOf(answer);
    }
}
