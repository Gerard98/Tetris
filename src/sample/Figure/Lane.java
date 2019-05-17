package sample.Figure;

import javafx.scene.Node;
import javafx.scene.paint.Color;
import javafx.scene.paint.ImagePattern;
import javafx.scene.shape.Rectangle;
import sample.RandomColor;

import java.util.LinkedList;
import java.util.List;

public class Lane extends Figure {
    // That figure can have a 2 dirent positions
    /*

    0:  x x x x     0 1 2 3

    1:  x           0
        x           1
        x           2
        x           3

    */


    private int position = 0;

    public Lane(){
        ImagePattern img = RandomColor.getRandomColor();
        List<Node> listOfRectangles = new LinkedList<>();
        for(int i=0;i<4;i++){

            Rectangle rectangle = new Rectangle(30,30,img);
            rectangle.setLayoutX((i+1)*30);
            rectangle.setLayoutY(0);
            rectangle.setVisible(false);
            listOfRectangles.add(rectangle);

        }

        setListOfRectangles(listOfRectangles);

    }


    @Override
    public void rotateFigure(int[][] gameBoard){

        Node node, node2, node3;
        double x1,y1,x2,y2,x3,y3;
        switch (position){
            case 0:
                node = getListOfRectangles().get(0);
                node2 = getListOfRectangles().get(1);
                node3 = getListOfRectangles().get(3);

                x1 = node.getLayoutX();
                y1 = node.getLayoutY();

                x2 = node2.getLayoutX();
                y2 = node2.getLayoutY();

                x3 = node3.getLayoutX();
                y3 = node3.getLayoutY();

                if(checkGameBoardToRotate(x1+60,y1-60,x2+30,y2-30, x3-30,y3+30 ,gameBoard)) {
                    node.relocate(x1+60, y1 - 60);
                    node2.relocate(x2 + 30,y2 - 30);
                    node3.relocate(x3 - 30, y3+30);
                    position = 1;
                }
                break;
            case 1:
                node = getListOfRectangles().get(0);
                node2 = getListOfRectangles().get(1);
                node3 = getListOfRectangles().get(3);

                x1 = node.getLayoutX();
                y1 = node.getLayoutY();

                x2 = node2.getLayoutX();
                y2 = node2.getLayoutY();

                x3 = node3.getLayoutX();
                y3 = node3.getLayoutY();

                if(checkGameBoardToRotate(x1-60,y1+60,x2-30,y2+30,x3+30,y3-30,gameBoard)) {
                    node.relocate(x1-60,y1+60);
                    node2.relocate(x2-30,y2+30);
                    node3.relocate(x3+30,y3-30);
                    position = 0;
                }

                break;

        }

    }

}
