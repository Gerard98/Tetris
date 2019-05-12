package sample;

import javafx.scene.image.Image;
import javafx.scene.paint.Color;
import javafx.scene.paint.ImagePattern;

import java.util.Random;

public class RandomColor {


    public static ImagePattern getRandomColor(){
        Random random = new Random();
        int a = random.nextInt(6);
        Image img;
        switch (a){
            case 0:
                img = new Image("sample/Images/1.png");
                return new ImagePattern(img);
            case 1:
                img = new Image("sample/Images/2.png");
                return new ImagePattern(img);
            case 2:
                img = new Image("sample/Images/3.png");
                return new ImagePattern(img);
            case 3:
                img = new Image("sample/Images/4.png");
                return new ImagePattern(img);
            case 4:
                img = new Image("sample/Images/5.png");
                return new ImagePattern(img);
            case 5:
                img = new Image("sample/Images/6.png");
                return new ImagePattern(img);
        }

        img = new Image("sample/Images/1.png");
        return new ImagePattern(img);
    }
}
