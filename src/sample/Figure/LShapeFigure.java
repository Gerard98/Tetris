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

    public boolean checkGameBoardToRotate(double x1, double y1, double x2, double y2, int[][] gameBoard){
        int newX1 = (int) x1/30;
        int newY1 = (int) y1/30;

        int newX2 = (int) x2/30;
        int newY2 = (int) y2/30;

        if(newX1 > -1 && newX1 < 8 && newX2 > -1 && newX2 < 8  && newY1 < 16 && newY2 < 16){
            return gameBoard[newY1][newX1] == 0 && gameBoard[newY2][newX2] == 0 || newX1 < 0;
        }
         return false;
    }

    @Override
    public void rotateFigure(int[][] gameBoard) {
        Node node;
        Node node2;
        switch (position){
            case 0:

                node = getListOfRectangles().get(3);
                node2 = getListOfRectangles().get(0);
                if(checkGameBoardToRotate(node.getLayoutX()-30,node.getLayoutY()-30,node2.getLayoutX()+30, node2.getLayoutY()-30, gameBoard)) {
                    node.relocate(node.getLayoutX() - 30, node.getLayoutY() - 30);
                    node2.relocate(node2.getLayoutX() + 30, node2.getLayoutY() - 30);
                    position = 1;
                }

                break;
            case 1:
                node = getListOfRectangles().get(3);
                node2 = getListOfRectangles().get(0);
                if(checkGameBoardToRotate(node.getLayoutX()+60,node.getLayoutY()+60,node2.getLayoutX(), node2.getLayoutY()+60, gameBoard)) {
                    node.relocate(node.getLayoutX() + 60, node.getLayoutY() + 60);
                    node2.relocate(node2.getLayoutX(), node2.getLayoutY() + 60);
                    position = 2;
                }

                break;
            case 2:
                node = getListOfRectangles().get(3);
                node2 = getListOfRectangles().get(0);

                if(checkGameBoardToRotate(node.getLayoutX()-30,node.getLayoutY()+30,node2.getLayoutX() + 30, node2.getLayoutY()+30, gameBoard)) {
                    node.relocate(node.getLayoutX() - 30, node.getLayoutY() + 30);
                    node2.relocate(node2.getLayoutX() + 30, node2.getLayoutY() + 30);
                    position = 3;
                }

                break;
            case 3:
                node = getListOfRectangles().get(3);
                node2 = getListOfRectangles().get(0);
                if(checkGameBoardToRotate(node.getLayoutX(),node.getLayoutY()-60,node2.getLayoutX() - 60, node2.getLayoutY()-60, gameBoard)) {
                    node.relocate(node.getLayoutX(), node.getLayoutY() - 60);
                    node2.relocate(node2.getLayoutX() - 60, node2.getLayoutY() - 60);
                    position = 0;
                }
                break;
        }


    }
}
