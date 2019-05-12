package sample;

import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.Label;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.shape.*;
import javafx.util.Duration;
import sample.Figure.*;

import java.util.Arrays;
import java.util.Comparator;
import java.util.NoSuchElementException;
import java.util.Random;
import java.util.concurrent.CopyOnWriteArrayList;

public class Controller {

    private Figure figure;
    private Boolean playerIsPlaying;
    private int gameBoard[][];
    private boolean stop = false;
    private int points = 0;
    private int lanesDeleted = 0;

    @FXML
    private Pane pane, gamePane;
    private Timeline timeline;

    @FXML
    private Label pointsLabel, lanesLabel;

    @FXML
    public void initialize(){
        gamePane.setStyle("-fx-background-color: black;");
        gameBoard = new int[17][8];

        playerIsPlaying = true;
        gamePane.setBorder(new Border(new BorderStroke(Color.BLACK, BorderStrokeStyle.SOLID, CornerRadii.EMPTY, BorderWidths.DEFAULT)));
        refreshLabels();
        newFigure();
        newTimeline();

    }

    @FXML
    public void reset(){
        deleteFill();
        for(int i=0;i<17;i++){
            Arrays.fill(gameBoard[i],0);
        }
        newFigure();
        newTimeline();
    }

    public void deleteFill(){
        gamePane.getChildren().clear();
    }

    public void endGame(){
        System.out.println("Koniec gry");
        playerIsPlaying = false;
    }

    @FXML
    public void stop(){
        if(!stop){
            timeline.stop();
            stop =true;
        }
        else {
            timeline.play();
            stop = false;
        }
    }

    public void newFigure(){

        Random random = new Random();
        Figure figure = RandomFigure.getRandomFigure();

        gamePane.getChildren().addAll(figure.getListOfRectangles());

        this.figure = figure;

    }

    // Deleting Row

    public void gameBoardToString(){
        for(int i=6;i<17;i++){
            for(int j=0;j<8;j++){
                System.out.print(gameBoard[i][j] + " ");
            }
            System.out.println();
        }
        System.out.println();
    }

    public void showY(){
        gamePane.getChildren().forEach(m -> {
            System.out.println(m.getLayoutY());
        });
    }

    public int getMinY(){
        try {
            Node node = gamePane
                    .getChildren()
                    .stream()
                    .min(Comparator.comparing(Node::getLayoutY))
                    .get();
            return (int) node.getLayoutY() / 30;
        }
        catch (NoSuchElementException ex){
            return 15;
        }
    }

    public void refreshLabels(){
        pointsLabel.setText(Integer.toString(points));
        lanesLabel.setText(Integer.toString(lanesDeleted));
    }

    public void deleteRow(int row){

        CopyOnWriteArrayList<Node> list = new CopyOnWriteArrayList<>(gamePane.getChildren());
        list.forEach(m -> {
            int y = (int) m.getLayoutY()/30;
            if(y == row) {
                int x = (int) m.getLayoutX()/30;
                gameBoard[y][x] = 0;
                gamePane.getChildren().remove(m);
            }
        });
            for (int i = row; i >= getMinY(); i--) {
                for (Node node : gamePane.getChildren()) {
                    int y = (int) node.getLayoutY() / 30;
                    int x = (int) node.getLayoutX() / 30;
                    if (y < row && y == i) {
                        gameBoard[y][x] = 0;
                        gameBoard[y + 1][x] = 1;
                        node.setLayoutY(node.getLayoutY() + 30);
                    }
                }
            }


        points += 100;
        lanesDeleted += 1;
        refreshLabels();
        System.out.println("Po usuwaniu");
        gameBoardToString();
    }

    public void checkForDeleteRow(){
        boolean rowDeleted = false;
        for(int i=1;i<16;i++){
            for(int j=0;j<8;j++){
                if(gameBoard[i][j] == 0){
                    break;
                }
                if(j == 7){
                    deleteRow(i);
                    rowDeleted = true;
                    break;
                }
            }
            if(rowDeleted) break;
        }

        if(rowDeleted)checkForDeleteRow();
    }


    // Timeline


    public void newTimeline(){

        timeline = new Timeline(new KeyFrame(Duration.millis(10), new EventHandler<ActionEvent>() {
            double deltaY = 3;
            @Override
            public void handle(ActionEvent event) {

                figure.setLayoutY(deltaY);

                if(figure.checkBottomBorder(gameBoard)){
                    if(figure.getMinY() <= 29){
                        endGame();
                        timeline.stop();
                    }
                    else {
                        gameBoard = figure.changeGameBoard(gameBoard);
                        //gameBoardToString();
                        checkForDeleteRow();
                        newFigure();

                        timeline.playFromStart();
                    }
                }



            }
        }));
        timeline.setAutoReverse(true);
        timeline.setCycleCount(900);
        timeline.play();

    }

    // Rest

    public Double getSpecificBottomBorder(int j){
        for(int i=0;i<=15;i++){
            if(gameBoard[i][j] == 1){
                return i*30D;
            }
        }
        return 450.0;
    }

    public boolean isAvaibleToMoveD(){
        int x = (int) figure.getMaxX()/30;

        if(figure.getMaxX() >180) return false;

        int minY = (int) figure.getMinY()/30 + 1;
        int maxY = (int) figure.getMaxY()/30 + 1;

        for(int i=minY;i<=maxY;i++){
            if(gameBoard[i][x+1] == 1) return false;
        }
        return true;
    }

    public boolean isAvaibleToMoveA(){
        int x = (int) figure.getMinX()/30;

        if(figure.getMinX() <= 25) return false;

        int minY = (int) figure.getMinY()/30;
        int maxY = (int) figure.getMaxY()/30;

        for(int i=minY;i<=maxY;i++){
            if(gameBoard[i][x-1] == 1) return false;
        }
        return true;
    }



    public Boolean getPlayerIsPlaying(){
        return playerIsPlaying;
    }

    public Figure getFigure(){
        return figure;
    }

    public int[][] getGameBoard() {
        return gameBoard;
    }


}
