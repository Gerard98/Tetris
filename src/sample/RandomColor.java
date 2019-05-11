package sample;

import javafx.scene.paint.Color;

import java.util.Random;

public class RandomColor {


    public static Color getRandomColor(){
        Random random = new Random();
        int a = random.nextInt(4);

        switch (a){
            case 0:
                return Color.LIGHTBLUE;
            case 1:
                return Color.LIGHTGREEN;
            case 2:
                return Color.YELLOW;
            case 3:
                return Color.LIGHTGREY;
        }

        return Color.ORANGE;
    }
}
