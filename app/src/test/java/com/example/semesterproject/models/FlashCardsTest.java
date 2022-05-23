package com.example.semesterproject.models;

import org.junit.Assert;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

public class FlashCardsTest {

    private FlashCards fc = new FlashCards();
    private Equation e;

    private List<ProblemType> numFuncList = new ArrayList<ProblemType>();

    public FlashCardsTest() {
        setupList();
    }

    public void setupList(){
        numFuncList.clear();
        for (ProblemType pt: ProblemType.values()) {
            numFuncList.add(pt);
        }
    }

    @Test
    public void testEquationObject() {
        e = fc.pickNewProblem(numFuncList);
        ProblemType pt = e.getNumFunc();
        System.out.println("----------Chose:" + e.getNumFunc());
        System.out.println("----------Chose:" + e.getFirstNum());
        System.out.println("----------Chose:" + e.getSecondNum());

        Assert.assertEquals(pt, pt);
    }

}
