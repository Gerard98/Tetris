package sample.Figure;

import javafx.scene.Node;
import javafx.scene.paint.Color;
import javafx.scene.paint.ImagePattern;
import javafx.scene.shape.Rectangle;
import sample.RandomColor;

import java.util.LinkedList;
import java.util.List;

public class ZShapeFigure extends Figure {

    // That figure can have a 2 dirent positions
    /*

    0:  x x       0 1
          x x       2 3

    1:    x         1
        x x       0 2
        x         3

    */
    private int position = 0;

    public ZShapeFigure(){
        ImagePattern img = RandomColor.getRandomColor();
        List<Node> listOfRectangles = new LinkedList<>();
        for(int i=0;i<2;i++){
            Rectangle rectangle = new Rectangle(30,30,img);
            rectangle.setLayoutX(60+30*i);
            rectangle.setLayoutY(0);
            rectangle.setVisible(false);
            listOfRectangles.add(rectangle);
        }

        for(int i=0;i<2;i++){
            Rectangle rectangle = new Rectangle(30,30,img);
            rectangle.setLayoutX(90+30*i);
            rectangle.setLayoutY(30);
            rectangle.setVisible(false);
            listOfRectangles.add(rectangle);
        }

        setListOfRectangles(listOfRectangles);
    }



    public ZShapeFigure(int maxY){
        ImagePattern img = RandomColor.getRandomColor();
        double paragraph = (getNEXT_FIGURE_PANE_WIDTH() - 90)/2;
        List<Node> listOfRectangles = new LinkedList<>();
        for(int i=0;i<2;i++){
            Rectangle rectangle = new Rectangle(30,30,img);
            rectangle.setLayoutX(paragraph+30*i);
            rectangle.setLayoutY(maxY);
            listOfRectangles.add(rectangle);
        }

        for(int i=0;i<2;i++){
            Rectangle rectangle = new Rectangle(30,30,img);
            rectangle.setLayoutX(paragraph+30+30*i);
            rectangle.setLayoutY(maxY+30);
            listOfRectangles.add(rectangle);
        }

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
                node2 = getListOfRectangles().get(3);

                x1 = node.getLayoutX();
                y1 = node.getLayoutY();

                x2 = node2.getLayoutX();
                y2 = node2.getLayoutY();

                if(checkGameBoardToRotate(x1,y1+30,x2-60,y2-30,gameBoard)) {
                    node.relocate(x1, y1 + 30);
                    node2.relocate(x2 - 60,y2 + 30);
                    position = 1;
                }
                break;
            case 1:
                node = getListOfRectangles().get(0);
                node2 = getListOfRectangles().get(3);

                x1 = node.getLayoutX();
                y1 = node.getLayoutY();

                x2 = node2.getLayoutX();
                y2 = node2.getLayoutY();

                if(checkGameBoardToRotate(x1,y1-30,x2+60,y2-30,gameBoard)) {
                    node.relocate(x1,y1-30);
                    node2.relocate(x2+60,y2-30);
                    position = 0;
                }

                break;

        }


    }

}
