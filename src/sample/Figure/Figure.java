package sample.Figure;

import javafx.scene.Node;

import java.util.Comparator;
import java.util.Iterator;
import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;

public class Figure {

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

    public boolean checkBottomBorder(Double[] bottomBorder){

        return listOfRectangles.stream().anyMatch(m -> {
            if(m.getLayoutY() >= bottomBorder[(int) m.getLayoutX()/30]){
                return true;
            }
            return false;

        });
    }

    public boolean checkBottomBorder(int[][] gameBoard){

        return listOfRectangles.stream().anyMatch(m -> {
            int y = (int) m.getLayoutY()/30;
            int x = (int) m.getLayoutX()/30;
            if((gameBoard[y+1][x] == 1 /*&& m.getLayoutY() == getMaxY()*/) || y == 15){
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
            //System.out.println("X: " + n.getLayoutX()/30 + " Y: " +n.getLayoutY()/30);
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

    public int getX(Node node){
        return (int) node.getLayoutX()/30;
    }

    public int getY(Node node){
        return (int) node.getLayoutY()/30;
    }

    public void rotateFigure(int[][] gameBoard){

    }

    public Node getNodeWithMinX(){
        return listOfRectangles.stream()
                .min(Comparator.comparing(Node::getLayoutX))
                .get();
    }

    public Node getNodeWithMinY(){
        return listOfRectangles.stream()
                .min(Comparator.comparing(Node::getLayoutY))
                .get();
    }


    public Node getNodeWithMaxY(){
        return listOfRectangles.stream()
                .max(Comparator.comparing(Node::getLayoutY))
                .get();
    }

    public double getMaxY(){
        Node node = listOfRectangles.stream().max(Comparator.comparing(Node::getLayoutY)).get();
        System.out.println("Max Y: "+node.getLayoutY());
        return node.getLayoutY();
    }

    public double getMinY(){
        Node node = listOfRectangles.stream().min(Comparator.comparing(Node::getLayoutY)).get();
        System.out.println("Min Y: "+node.getLayoutY());
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
            m.setLayoutX(m.getLayoutX()-30);
        });
    }

    public void relocateToRight(){
        listOfRectangles.forEach(m -> {
            m.setLayoutX(m.getLayoutX()+30);
        });
    }

    public boolean checkGameBoardToRotate(double x1, double y1, double x2, double y2, int[][] gameBoard){
        int newX1 = (int) x1/30;
        int newY1 = (int) y1/30;

        int newX2 = (int) x2/30;
        int newY2 = (int) y2/30;

        if(newX1 > -1 && newX1 < 8 && newX2 > -1 && newX2 < 8  && newY1 < 16 && newY2 < 16){
            return gameBoard[newY1][newX1] == 0 && gameBoard[newY2][newX2] == 0;
        }
        return false;
    }

    public boolean checkGameBoardToRotate(double x1, double y1, int[][] gameBoard){
        int newX1 = (int) x1/30;
        int newY1 = (int) y1/30;
        if(newX1 > -1 && newX1 < 8 && newY1 < 16){
            return gameBoard[newY1][newX1] == 0;
        }
        return false;
    }


}
