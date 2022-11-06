package main;

import lc.kra.system.keyboard.GlobalKeyboardHook;
import lc.kra.system.keyboard.event.GlobalKeyEvent;
import lc.kra.system.keyboard.event.GlobalKeyListener;

import java.awt.*;

public class MouseMover {
    private int generateNumWithinRange(int min, int max) {
        return min + (int)(Math.random() * ((max - min) + 1));
    }
    public static boolean runProgram = false;

    public void startJiggling(int screenWidth,int screenHeight) {
        int loopValue = 0;

        //Our very first starting position is always bottom left. User can change to bottom right or the app can randomise this.
        int startX = 0;
        int startY = screenHeight;
        runProgram = true;

        while (runProgram) {
            System.out.println("runProgram: " + runProgram);
            try {
                int destinationX = new MouseMover().generateNumWithinRange(0,screenWidth); //Anything between 0 to SCREEN WIDTH
                int destinationY = new MouseMover().generateNumWithinRange(0,screenHeight); //Anything between 0 to SCREEN HEIGHT

                new Robot().mouseMove(startX, startY);

                int xPaneDifference = destinationX >= startX ? destinationX - startX : startX - destinationX;
                int yPaneDifference = destinationY >= startY ? destinationY - startY : startY - destinationY;
                int maxPixelValue = xPaneDifference >= yPaneDifference ? xPaneDifference : yPaneDifference;

                if (xPaneDifference == 0 && yPaneDifference == 0)
                    maxPixelValue = screenWidth >= screenHeight ? screenWidth : screenHeight;

                System.out.println("Moving mouse to (" + destinationX + ", " + destinationY + ")");

                for (int i = 0; i <= maxPixelValue; i++) {
                    startX = destinationX > startX ? startX + 1 : startX -1; //Under the assumption that destination x will never be start x?
                    startY = destinationY > startY ? startY + 1 : startY -1;
                    new Robot().mouseMove(startX, startY);
                    Thread.sleep(1);
                }

                //Our new start point should be our last destination
                System.out.println("Start X: " + startX);
                System.out.println("Start Y: " + startY);

            } catch (Exception e) {
                e.printStackTrace();
            }
            loopValue++;
        }
    }
}
