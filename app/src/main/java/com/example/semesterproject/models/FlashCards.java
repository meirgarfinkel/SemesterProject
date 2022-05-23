package com.example.semesterproject.models;

import java.util.List;
import java.util.Random;

public class FlashCards {
    Random rand = new Random();
    Equation e = new Equation();

    public Equation pickNewProblem(List<ProblemType> enums) {
        e.setFirstNum(getRandomNum());
        e.setSecondNum(getRandomNum());
        e.setNumFunc(enums.get(rand.nextInt(enums.size()-1) + 1));

        return e;
    }

    private int getRandomNum() {
        return rand.nextInt(10) + 1;
    }

}
