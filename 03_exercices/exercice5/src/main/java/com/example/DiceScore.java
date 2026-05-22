package com.example;

public class DiceScore {

    private Ide de;

    public DiceScore(Ide de) {
        this.de = de;
    }

    public int getScore (){
        int scoreFirst = de.getRoll();
        int scoreSecond = de.getRoll();

        if(scoreFirst == scoreSecond){
            if(scoreFirst == 6){
                return 30;
            }
            return scoreFirst*2 + 10;
        }else{
            return scoreFirst < scoreSecond ? scoreSecond : scoreFirst;
        }
    }
}
