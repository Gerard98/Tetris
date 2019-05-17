package sample.Figure;


import javafx.scene.Node;
import javafx.scene.image.Image;
import javafx.scene.paint.Color;
import javafx.scene.paint.ImagePattern;
import javafx.scene.shape.Rectangle;
import javafx.scene.shape.Shape;
import sample.RandomColor;

import java.util.*;
import java.util.concurrent.CopyOnWriteArrayList;

public class Square extends Figure{


    public Square(){
        ImagePattern img = RandomColor.getRandomColor();
        List<Node>listOfRectangles = new LinkedList<>();
        for(int i=0;i<2;i++){
            for(int j=0;j<2;j++){
                Rectangle rectangle = new Rectangle(30,30,img);
                rectangle.setLayoutX((5+i)*30); // i = 0   i =1  i = 2
                rectangle.setLayoutY(j*30);   // X = 5 Y = 0 X =6 Y = 1
                rectangle.setVisible(false);
                listOfRectangles.add(rectangle);
            }
        }
        setListOfRectangles(listOfRectangles);
    }


}
