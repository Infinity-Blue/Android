package com.bawp.trivia.model;

public class Question {
    private String question;
    private int ansOpt;
    private String correctAnswer;
    private String opt1;
    private String opt2;
    private String opt3;
    private String opt4;


    public Question(int ansOpt, String correctAnswer) {
        this.ansOpt=ansOpt;
        this.correctAnswer=correctAnswer;
    }

    public Question(String question, String opt1, String opt2,
                    String opt3, String opt4) {
        this.question=question;
        this.opt1 = opt1;
        this.opt2= opt2;
        this.opt3=opt3;
        this.opt4=opt4;
    }

    public String getQuestion() {
        return question;
    }

    public void setQuestion(String question) {
        this.question = question;
    }

    public int getAnsOpt() {return ansOpt;}

    public void setAnsOpt(int ansOpt) { this.ansOpt = ansOpt; }

    public String getCorrectAnswer() { return correctAnswer; }

    public void setCorrectAnswer(String correctAnswer) { this.correctAnswer = correctAnswer; }

    public String getOpt1() { return opt1; }

    public void setOpt1(String opt1) { this.opt1 = opt1; }

    public String getOpt2() { return opt2; }

    public void setOpt2(String opt2) { this.opt2 = opt2; }

    public String getOpt3() { return opt3; }

    public void setOpt3(String opt3) { this.opt3 = opt3; }

    public void setOpt4(String opt4) { this.opt4 = opt4; }

    public String getOpt4() { return opt4; }

}
