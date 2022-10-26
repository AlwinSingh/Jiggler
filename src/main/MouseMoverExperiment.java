package main;

import java.awt.*;

public class MouseMoverExperiment {
    public void createRectangleBoundary() {

    }

    public void moveMouse(int xStart, int xEnd, int yStart, int yEnd) {
        try {
            String xDirectionOfMovement = "right";
            Robot robot = new Robot();

            /*
            Get the difference between xStart and xEnd
             There are 2 possibilities, xEnd > xStart and xEnd < xStart
            */
            if (xStart < xEnd) xDirectionOfMovement = "left";

            for (int i = 1; i <= (xStart - xEnd); i++) {
                robot.mouseMove(xStart, 940);
                System.out.println("Moving..." + i);
                xStart += 1;
                System.out.println("Thread Sleep");
                Thread.sleep(300);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {
        GraphicsDevice gd = GraphicsEnvironment.getLocalGraphicsEnvironment().getDefaultScreenDevice();
        int screenWidth = gd.getDisplayMode().getWidth(); //X Value
        int screenHeight = gd.getDisplayMode().getHeight(); //Y Value

        //System.out.println(screenWidth);
        //System.out.println(screenHeight);

        /* Screen pixel values
        Top left: 0,0
        Top right: 1920,0
        Middle: 940,540
        Bottom left: 0,1080
        Bottom right: 1920,1080

        To move diagonally from bottom left to top right.....0, 1080 must translate to 1920,0
        move 1080 pixels to the right while moving 1920 pixels to the top

        1920/1080 = ~ 1.778

        After some experimentation, it is possible but not exactly the best..
        The alternative is we create square boundaries within the user's screen and run the mouse naturally within them
         */

        try {
            int startX = 0;
            int startY = 1080;
            new Robot().mouseMove(startX,startY);
            for (int i = 0; i < screenWidth; i++) {
                startX += 1;
                if (i % 2 == 0) startY -= 1;
                new Robot().mouseMove(startX,startY);
            }
            System.out.println(startX);
            System.out.println(startY);
        } catch (Exception e) {
            e.printStackTrace();
        }

        //new MouseMover().moveMouse(0,0,0,0);
    }
}
