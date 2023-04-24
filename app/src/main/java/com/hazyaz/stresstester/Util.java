package com.hazyaz.stresstester;

public class Util {


    public final String mQuestion;
    public final String mChoice1;
    public final String mChoice2;
    public final String mChoice3;
    public final String mChoice4;
    public final String mChoice5;
    public final int mQuestionCounter;


    public Util(String q, String c1, String c2, String c3, String c4, String c5,int qC) {

        this.mQuestion = q;
        this.mChoice1 = c1;
        this.mChoice2 = c2;
        this.mChoice3 = c3;
        this.mChoice4 = c4;
        this.mChoice5 = c5;
        this.mQuestionCounter = qC;


    }

    public String getmQuestion() {
        return mQuestion;
    }

    public String getmChoice1() {
        return mChoice1;
    }

    public String getmChoice2() {
        return mChoice2;
    }

    public String getmChoice3() {
        return mChoice3;
    }
    public String getmChoice4() {
        return mChoice4;
    }
    public String getmChoice5() {
        return mChoice5;
    }

    public int getmQuestionCounter() {
        return mQuestionCounter;
    }
}
