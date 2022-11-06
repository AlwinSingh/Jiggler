package main;

import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.input.KeyCode;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import lc.kra.system.keyboard.GlobalKeyboardHook;
import lc.kra.system.keyboard.event.GlobalKeyEvent;
import lc.kra.system.keyboard.event.GlobalKeyListener;

import java.awt.*;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import java.util.Timer;
import java.util.TimerTask;

public class Controller implements Initializable {

    @FXML
    public Label jiggleSpeed;

    @FXML
    public Label timeElapsed;

    @FXML
    public Label screenWidth;

    @FXML
    public Label screenHeight;

    @FXML
    public Text jiggleStopText;

    @FXML
    public Button startJiggling;

    @FXML
    public AnchorPane parentAnchorPane;

    GraphicsDevice gd = GraphicsEnvironment.getLocalGraphicsEnvironment().getDefaultScreenDevice();
    MouseMover mouseMover = new MouseMover();
    Timer updateElapsedTimeTimer = new Timer();

    public static GlobalKeyboardHook keyboardHook = new GlobalKeyboardHook(false); // Use false here to switch to hook instead of raw input
    public void startKeyboardKeyListener() {
        GlobalKeyListener globalKeyListener = new GlobalKeyListener() {
            @Override
            public void keyPressed(GlobalKeyEvent globalKeyEvent) {
                if (globalKeyEvent.getVirtualKeyCode() == GlobalKeyEvent.VK_BACK) {
                    System.out.println("BACKSPACE");
                    MouseMover.runProgram = false;
                    Platform.runLater(new Runnable() {
                        @Override
                        public void run() {
                            stopJiggleVisualUpdate();
                        }
                    });
                }
            }

            @Override
            public void keyReleased(GlobalKeyEvent globalKeyEvent) {
            }
        };

        keyboardHook.addKeyListener(globalKeyListener);
    }

    //This runs every minute once the Jiggling Program runs
    public void startOrStopUpdatingElapsedTime() {
        if (!MouseMover.runProgram) {
            updateElapsedTimeTimer.schedule(new TimerTask() {
                public void run() {
                    Platform.runLater(new Runnable() {
                        @Override
                        public void run() {
                            int timeElapsedInt = Integer.valueOf(timeElapsed.getText().replace("m", "")); //Janky, not recommended way of doing it
                            timeElapsed.setText((timeElapsedInt + 1) + "m");
                        }
                    });
                }
            }, 60000, 60 * 1000); //Start 1 minute later and for every min after
        } else {
            if (updateElapsedTimeTimer != null) {
                updateElapsedTimeTimer.cancel();
                updateElapsedTimeTimer = new Timer();
            }
        }
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        startKeyboardKeyListener();

        //checking to see whether these buttons were loaded from the FXML file
        assert jiggleSpeed != null : "fx:id=\"jiggleSpeed\" was not injected: Check your FXML file 'FX-Timer.fxml'.";
        assert timeElapsed != null : "fx:id=\"timeElapsed\" INJECTION ERROR";
        assert screenWidth != null : "fx:id=\"screenWidth\" INJECTION ERROR";
        assert screenHeight != null : "fx:id=\"screenHeight\" INJECTION ERROR";
        assert startJiggling != null : "fx:id=\"startJiggling\" INJECTION ERROR";
        assert jiggleStopText != null : "fx:id=\"jiggleStopText\" INJECTION ERROR";
        assert parentAnchorPane != null : "fx:id=\"parentAnchorPane\" INJECTION ERROR";

        jiggleSpeed.setText("0 Pixel");
        timeElapsed.setText("0m");
        screenWidth.setText(String.valueOf(gd.getDisplayMode().getWidth()));
        screenHeight.setText(String.valueOf(gd.getDisplayMode().getHeight()));
    }

    public void stopJiggleVisualUpdate() {
        startJiggling.setText("Start Jiggling!");
        jiggleStopText.setText("Press to start Jiggling.");
        jiggleSpeed.setText("0 Pixel");
        startJiggling.setDisable(false);
    }

    public void startJiggleVisualUpdate() {
        startJiggling.setText("Jiggling...");
        jiggleStopText.setText("Press backspace to stop Jiggling.");
        jiggleSpeed.setText("1 Pixel");
        timeElapsed.setText("0m");
        startJiggling.setDisable(true);
    }

    public void handleClicks(ActionEvent actionEvent) {
        if (actionEvent.getSource() == startJiggling) {
            int screenWidth = gd.getDisplayMode().getWidth(); //X Value
            int screenHeight = gd.getDisplayMode().getHeight(); //Y Value

            startJiggleVisualUpdate();
            startOrStopUpdatingElapsedTime();

            //The scheduler below is required so that the UI updates can occur first
            new java.util.Timer().schedule(
                    new java.util.TimerTask() {
                        @Override
                        public void run() {
                            mouseMover.startJiggling(screenWidth,screenHeight);
                        }
                    },
                    100
            );
        }
    }
}