package sample.Figure;

import javafx.scene.Node;
import javafx.scene.paint.Color;
import javafx.scene.paint.ImagePattern;
import javafx.scene.shape.Rectangle;
import sample.RandomColor;

import java.util.LinkedList;
import java.util.List;

public class LShapeFigure extends Figure {

    // That figure can have a 4 dirent positions
    /*

    0:      x         3
        x x x     0 1 2

    1:  x         3
        x         0
        x x       1 2

    2:  x x x     1 2 3
        x         0

    3:  x x       1 2
          x         3
          x         0

    */
    private int position = 0;

    public LShapeFigure(){
        ImagePattern img = RandomColor.getRandomColor();
        List<Node> listOfRectangles = new LinkedList<>();
        for(int i=0;i<3;i++){
            Rectangle rectangle = new Rectangle(30,30,img);
            rectangle.setLayoutX(60+30*i);
            rectangle.setLayoutY(30);
            listOfRectangles.add(rectangle);
        }

        Rectangle rectangle = new Rectangle(30,30,img);
        rectangle.setLayoutX(120.0);
        rectangle.setLayoutY(0);
        listOfRectangles.add(rectangle);

        setListOfRectangles(listOfRectangles);
    }

    @Override
    public void rotateFigure(int[][] gameBoard) {
        Node node = getListOfRectangles().get(3);
        Node node2 = getListOfRectangles().get(0);
        int x1 = (int) node.getLayoutX();
        int y1 = (int) node.getLayoutY();

        int x2 = (int) node2.getLayoutX();
        int y2 = (int) node2.getLayoutY();
        switch (position){
            case 0:
                if(checkGameBoardToRotate(x1-30,y1-30,x2+30, y2-30, gameBoard)) {
                    node.relocate(x1 - 30, y1 - 30);
                    node2.relocate(x2 + 30, y2 - 30);
                    position = 1;
                }

                break;
            case 1:
                if(checkGameBoardToRotate(x1+60,y1+60,x2, y2+60, gameBoard)) {
                    node.relocate(x1 + 60, y1 + 60);
                    node2.relocate(x2, y2 + 60);
                    position = 2;
                }

                break;
            case 2:

                if(checkGameBoardToRotate(x1-30,y1+30,x2+30, y2+30, gameBoard)) {
                    node.relocate(x1 - 30, y1 + 30);
                    node2.relocate(x2 + 30, y2 + 30);
                    position = 3;
                }

                break;
            case 3:

                if(checkGameBoardToRotate(x1,y1-60,x2-60, y2-60, gameBoard)) {
                    node.relocate(x1, y1 - 60);
                    node2.relocate(x2 - 60, y2 - 60);
                    position = 0;
                }
                break;
        }


    }
}
