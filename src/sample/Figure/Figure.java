package sample.Figure;

import javafx.scene.Node;

import java.util.Comparator;
import java.util.Iterator;
import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;

public class Figure {

    private final int GAME_BOARD_WIDTH = 10;
    private final int GAME_BOARD_HEIGHT = 22;

    private final double NEXT_FIGURE_PANE_WIDTH = 140;

    private List<Node> listOfRectangles;

    public List<Node> getListOfRectangles() {
        return listOfRectangles;
    }

    public void setListOfRectangles(List<Node> listOfRectangles) {
        this.listOfRectangles = listOfRectangles;
    }

    public void setLayoutY(double deltaY){
        listOfRectangles.forEach(m -> {
            m.setLayoutY(m.getLayoutY() + deltaY);
        });
    }



    public void printGameBoard(int[][] gameBoard){
        for(int i=0;i<7;i++){
            for(int j=0;j<GAME_BOARD_WIDTH;j++){
                System.out.print(gameBoard[i][j] + " ");
            }
            System.out.println();
        }
        System.out.println();
    }

    public boolean checkBottomBorder(int[][] gameBoard){

        return listOfRectangles.stream().anyMatch(m -> {
            printGameBoard(gameBoard);
            int y = (int) m.getLayoutY()/30;
            int x = (int) m.getLayoutX()/30;
            System.out.println(x + " " + y);
            if(y < 0){
                return false;
            }
            if (y == GAME_BOARD_HEIGHT-1) {
                return true;
            } else if (gameBoard[y + 1][x] == 1) {
                return true;
            }


            return false;
        });
    }

    private int[][] localGameBoard;

    public int[][] changeGameBoard(int[][] gameBoard){

        CopyOnWriteArrayList<Node> array = new CopyOnWriteArrayList<>(listOfRectangles);
        Iterator<Node> it = array.iterator();
        localGameBoard = gameBoard;
        while(it.hasNext()){
            Node n = it.next();
            int x = (int) n.getLayoutX()/30;
            int y = (int) n.getLayoutY()/30;

            setLocalGameBoard(y,x);
        }

        return getLocalGameBoard();
    }

    public int[][] getLocalGameBoard() {
        return localGameBoard;
    }

    public void setLocalGameBoard(int x, int y){
        localGameBoard[x][y] = 1;
    }

    public void rotateFigure(int[][] gameBoard){

    }

    public double getMaxY(){
        Node node = listOfRectangles.stream().max(Comparator.comparing(Node::getLayoutY)).get();
        return node.getLayoutY();
    }

    public double getMinY(){
        Node node = listOfRectangles.stream().min(Comparator.comparing(Node::getLayoutY)).get();
        return node.getLayoutY();
    }

    public double getMaxX(){
        Node node = listOfRectangles.stream().max(Comparator.comparing(Node::getLayoutX)).get();
        return node.getLayoutX();
    }

    public double getMinX(){
        Node node = listOfRectangles.stream().min(Comparator.comparing(Node::getLayoutX)).get();
        return node.getLayoutX();
    }

    public void relocateToLeft(){
        listOfRectangles.forEach(m -> {
            m.relocate(m.getLayoutX()-30, m.getLayoutY());
        });
    }

    public void relocateToRight(){
        listOfRectangles.forEach(m -> {
            m.relocate(m.getLayoutX()+30, m.getLayoutY());
        });
    }

    public boolean checkGameBoardToRotate(double x1, double y1, double x2, double y2, double x3, double y3, int[][] gameBoard) {
        return checkGameBoardToRotate(x1, y1, gameBoard) && checkGameBoardToRotate(x2, y2, gameBoard) && checkGameBoardToRotate(x3,y3,gameBoard);
    }

    public boolean checkGameBoardToRotate(double x1, double y1, double x2, double y2, int[][] gameBoard){
        return checkGameBoardToRotate(x1, y1, gameBoard) && checkGameBoardToRotate(x2,y2, gameBoard);
    }

    public boolean checkGameBoardToRotate(double x1, double y1, int[][] gameBoard){
        int newX1 = (int) x1/30;
        int newY1 = (int) y1/30;
        if(newX1 > -1 && newX1 < GAME_BOARD_WIDTH && newY1 < GAME_BOARD_HEIGHT && newY1>-1){
            return gameBoard[newY1][newX1] == 0;
        }
        else{
            if(newX1 < 0){
                if(checkGameBoardToRotate( newX1*(-30) , newY1 ,gameBoard )){
                    for(int i=0;i<(-1)*newX1;i++){
                        relocateToRight();
                    }
                    rotateFigure(gameBoard);
                    return false;
                }
            }
            else if(newX1 >= GAME_BOARD_WIDTH-1){
                if(checkGameBoardToRotate(newX1*30 -(newX1 - (GAME_BOARD_WIDTH-1))*30 , newY1,gameBoard )){
                    for(int i=0;i<(newX1 - (GAME_BOARD_WIDTH-1));i++){
                        relocateToLeft();
                    }
                    rotateFigure(gameBoard);
                    return false;
                }
            }
        }
        return false;
    }

    // Moving

    public boolean isAvailableToMoveD(int[][] gameBoard){

        if(getMaxX() >= GAME_BOARD_WIDTH*30 - 30) return false;

        return listOfRectangles
                .stream()
                .allMatch(m -> checkRightPosition(gameBoard,m));
    }

    public boolean isAvailableToMoveA(int[][] gameBoard){

        if(getMinX() <= 25) return false;

        return listOfRectangles
                .stream()
                .allMatch(m -> checkLeftPosition(gameBoard,m));
    }

    public boolean isAvailableToMoveS(int[][] gameBoard){

        System.out.println(getMinY());
        if(getMinY() >= 630 || getMinY() <= 0) return false;

        return listOfRectangles
                .stream()
                .allMatch(m -> checkDownPosition(gameBoard, m));

    }

    public boolean checkLeftPosition(int [][] gameBoard, Node node){
        int x = (int) node.getLayoutX() / 30;
        int y = (int) node.getLayoutY() / 30;
        return gameBoard[y][x-1] == 0;
    }

    public boolean checkRightPosition(int [][] gameBoard, Node node){
        int x = (int) node.getLayoutX() / 30;
        int y = (int) node.getLayoutY() / 30;
        return gameBoard[y][x+1] == 0;
    }

    public boolean checkDownPosition(int[][] gameBoard, Node node){
        int x = (int) node.getLayoutX() / 30;
        int y = (int) node.getLayoutY() / 30;
        return gameBoard[y+1][x] == 0;
    }

    public void figureChangedPositionInQuene(){
        listOfRectangles.forEach(m -> {
            m.setLayoutY(m.getLayoutY() - 70);
        });
    }

    public double getNEXT_FIGURE_PANE_WIDTH() {
        return NEXT_FIGURE_PANE_WIDTH;
    }

    public void setLayoutForGamePane(){
        listOfRectangles.forEach(m -> {
            double paragraph = m.getLayoutX()%30;
            m.relocate(m.getLayoutX()-paragraph + 90, m.getLayoutY());
            m.setVisible(false);
        });
    }
}
