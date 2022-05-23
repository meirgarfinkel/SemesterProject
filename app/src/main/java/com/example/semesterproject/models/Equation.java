package com.example.semesterproject.models;

public class Equation {
    private int firstNum;
    private int secondNum;
    private ProblemType numFunc;

    public int getFirstNum() {
        return firstNum;
    }

    public int getSecondNum() {
        return secondNum;
    }

    public ProblemType getNumFunc() {
        return numFunc;
    }

    public void setFirstNum(int firstNum) {
        this.firstNum = firstNum;
    }

    public void setSecondNum(int secondNum) {
        this.secondNum = secondNum;
    }

    public void setNumFunc(ProblemType numFunc) {
        this.numFunc = numFunc;
    }
}
