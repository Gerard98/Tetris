package sample;

import sample.Figure.*;

import java.util.Random;

public class RandomFigure {

    public static Figure getRandomFigure(int quene){
        Random random = new Random();
        int a = random.nextInt(7);
        switch (a){
            case 0:
                return new ZShapeFigure(quene*90 - 60);
            case 1:
                return new InvertedZShapeFigure(quene*90 - 60);
            case 2:
                return new TShapeFigure(quene*90 - 60);
            case 3:
                return new LShapeFigure(quene*90 - 60);
            case 4:
                return new InvertedLShapeFigure(quene*90 - 60);
            case 5:
                return new Square(quene*90 - 60);
            case 6:
                return new Lane(quene*90 - 60);
            default:
                return new Square(quene*90 - 60);
        }
    }

}
