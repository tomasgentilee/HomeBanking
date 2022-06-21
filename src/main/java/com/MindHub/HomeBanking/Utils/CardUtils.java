package com.MindHub.HomeBanking.Utils;

import static java.lang.Math.random;

public final class CardUtils {

    public static String getCardNumber(int min, int max) {

        String card = ((long) ((Math.random() * (max - min)) + min) + " " + (long) ((Math.random() * (max - min)) + min) + " " + (long) ((Math.random() * (max - min)) + min) + " " + (long) ((Math.random() * (max - min)) + min));

        while(card != card){

            return ((long) ((Math.random() * (max - min)) + min) + " " + (long) ((Math.random() * (max - min)) + min) + " " + (long) ((Math.random() * (max - min)) + min) + " " + (long) ((Math.random() * (max - min)) + min));

        }
        return card;
    }

    public static int getCVV(int min, int max) {
        return (int) ((Math.random() * (max - min)) + min);
    }



}
