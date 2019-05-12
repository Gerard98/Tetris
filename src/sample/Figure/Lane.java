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

    private boolean isUpright = false;

    public Lane(){
        ImagePattern img = RandomColor.getRandomColor();
        List<Node> listOfRectangles = new LinkedList<>();
        for(int i=0;i<4;i++){

            Rectangle rectangle = new Rectangle(30,30,img);
            rectangle.setLayoutX((i+1)*30);
            rectangle.setLayoutY(0);
            listOfRectangles.add(rectangle);

        }

        setListOfRectangles(listOfRectangles);

    }

    public boolean chechGameBoardToRotateByX(int[][] gameBoard, int x , int y){
        if(x+2 >7d || x-1<0) {
            return false;
        }
        return gameBoard[y][x-1] == 0 && gameBoard[y][x+1] == 0 && gameBoard[y][x+2] == 0;
    }

    public boolean chechGameBoardToRotateByY(int[][] gameBoard, int x , int y){
        if(y-2 < 0) {
            return false;
        }
        return gameBoard[y+1][x] == 0 && gameBoard[y-1][x] == 0 && gameBoard[y-2][x] == 0;
    }

    @Override
    public void rotateFigure(int[][] gameBoard){

        if(isUpright) {
            // incoming
            int maxY = (int) getMaxY()/30;
            int minY = (int) getMinY()/30;

            Node node = getListOfRectangles().get(1);
            double y = node.getLayoutY();

            int gameBoardX = getX(node);
            int gameBoardY = getY(node);

            if(chechGameBoardToRotateByX(gameBoard, gameBoardX, gameBoardY)) {
                getListOfRectangles().forEach(m -> {
                    int localY = getY(m);

                    double result = y - m.getLayoutY();
                    if (localY == maxY) {
                        m.relocate(m.getLayoutX() + 60, m.getLayoutY() + result); // x-m.getLayoutX
                    } else if (localY == minY) {
                        m.relocate(m.getLayoutX() - 30, m.getLayoutY() + result);
                    } else if (!m.equals(node)) {
                        m.relocate(m.getLayoutX() + 30, m.getLayoutY() + result);
                    }
                });
                isUpright = false;
            }
        }
        else {
            int maxX = (int) getMaxX()/30;
            int minX = (int) getMinX()/30;

            Node node = getListOfRectangles().get(1);
            //int x = (int) node.getLayoutX();

            int gameBoardX = getX(node);
            int gameBoardY = getY(node);

            if(chechGameBoardToRotateByY(gameBoard, gameBoardX, gameBoardY)) {
                getListOfRectangles().forEach(m -> {
                    int localX = getX(m);

                    if (localX == maxX) {
                        m.relocate(m.getLayoutX() - 60, m.getLayoutY() - 60);
                    } else if (localX == minX) {
                        m.relocate(m.getLayoutX() + 30, m.getLayoutY() - 30);
                    } else if (!m.equals(node)) {
                        m.relocate(m.getLayoutX() - 30, m.getLayoutY() + 30);
                    }
                });
                isUpright = true;
            }
        }
    }

}
