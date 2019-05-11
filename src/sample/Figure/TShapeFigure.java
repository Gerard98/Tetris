package sample.Figure;

import javafx.scene.Node;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import sample.RandomColor;

import java.util.LinkedList;
import java.util.List;

public class TShapeFigure extends Figure {

    // That figure can have a 4 dirent positions
    /*

    0:    x         3
        x x x     0 1 2

    1:  x         3
        x x       1 2
        x         0

    2:  x x x     0 3 2
          x         1

    3:    x         3
        x x       0 1
          x         2

    */
    private int position = 0;

    public TShapeFigure(){
        Color color = RandomColor.getRandomColor();
        List<Node> listOfRectangles = new LinkedList<>();
        for(int i=0;i<3;i++){
            Rectangle rectangle = new Rectangle(30,30,color);
            rectangle.setLayoutX(60+30*i);
            rectangle.setLayoutY(30);
            listOfRectangles.add(rectangle);
        }

        Rectangle rectangle = new Rectangle(30,30,color);
        rectangle.setLayoutX(90.0);
        rectangle.setLayoutY(0);
        listOfRectangles.add(rectangle);

        setListOfRectangles(listOfRectangles);
    }



    @Override
    public void rotateFigure(int[][] gameBoard) {
        Node node;
        Node node2;
        double x1,y1,x2,y2;
        switch (position){
            case 0:

                node = getListOfRectangles().get(0);
                x1 = node.getLayoutX();
                y1 = node.getLayoutY();
                if(checkGameBoardToRotate(x1+30,y1+30,gameBoard)) {
                    node.relocate(x1 + 30, y1 + 30);
                    position = 1;
                }
                break;
            case 1:
                node = getListOfRectangles().get(0);
                node2 = getListOfRectangles().get(2);

                x1 = node.getLayoutX();
                y1 = node.getLayoutY();

                x2 = node2.getLayoutX();
                y2 = node2.getLayoutY();

                if(checkGameBoardToRotate(x1-30,y1-60,x2,y2-30,gameBoard)) {
                    node.relocate(x1-30,y1-60);
                    node2.relocate(x2,y2-30);
                    position = 2;
                }

                break;
            case 2:
                node = getListOfRectangles().get(0);
                node2 = getListOfRectangles().get(2);

                x1 = node.getLayoutX();
                y1 = node.getLayoutY();

                x2 = node2.getLayoutX();
                y2 = node2.getLayoutY();

                if(checkGameBoardToRotate(x1,y1+30,x2-30,y2+60,gameBoard)) {
                    node.relocate(x1,y1+30);
                    node2.relocate(x2-30,y2+60);
                    position = 3;
                }

                break;
            case 3:
                node = getListOfRectangles().get(2);

                x1 = node.getLayoutX();
                y1 = node.getLayoutY();

                if(checkGameBoardToRotate(x1+30,y1-30,gameBoard)) {
                    node.relocate(x1+30,y1-30);
                    position = 0;
                }

                break;
        }


    }

}
