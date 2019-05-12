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
import javafx.util.Duration;
import sample.Figure.*;

import java.util.Arrays;
import java.util.Comparator;
import java.util.NoSuchElementException;
import java.util.concurrent.CopyOnWriteArrayList;

public class Controller {

    private Figure figure;
    private Boolean playerIsPlaying;
    private int[][] gameBoard;
    private boolean stop = false;
    private int lvl = 1;
    private int points = 0;
    private int lanesDeleted = 0;

    private final int GAME_BOARD_WIDTH = 10;
    private final int GAME_BOARD_HEIGHT = 20;


    @FXML
    private Pane gamePane;
    private Timeline timeline;

    @FXML
    private Label pointsLabel, lanesLabel,lvlLabel;

    @FXML
    public void initialize(){
        gamePane.setStyle("-fx-background-color: black;");
        gameBoard = new int[GAME_BOARD_HEIGHT][GAME_BOARD_WIDTH];

        playerIsPlaying = true;
        gamePane.setBorder(new Border(new BorderStroke(Color.BLACK, BorderStrokeStyle.SOLID, CornerRadii.EMPTY, BorderWidths.DEFAULT)));
        refreshLabels();
        newFigure();
        newTimeline();

    }

    @FXML
    public void reset(){
        timeline.stop();
        deleteFill();
        for(int i=0;i<GAME_BOARD_HEIGHT;i++){
            Arrays.fill(gameBoard[i],0);
        }
        lvl = 1;
        points = 0;
        lanesDeleted = 0;
        refreshLabels();
        playerIsPlaying = true;
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

        Figure figure = RandomFigure.getRandomFigure();

        gamePane.getChildren().addAll(figure.getListOfRectangles());

        this.figure = figure;

    }

    // Deleting Row

    public void gameBoardToString(){
        for(int i=6;i<20;i++){
            for(int j=0;j<10;j++){
                System.out.print(gameBoard[i][j] + " ");
            }
            System.out.println();
        }
        System.out.println();
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
            return GAME_BOARD_HEIGHT;
        }
    }

    public void refreshLabels(){
        if(points/100 > lvl && lvl < 11) {
            lvl++;
        }
        pointsLabel.setText(Integer.toString(points));
        lanesLabel.setText(Integer.toString(lanesDeleted));
        lvlLabel.setText(Integer.toString(lvl));
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


        points += 100*lvl;
        lanesDeleted += 1;
        refreshLabels();
        System.out.println("Po usuwaniu");
        gameBoardToString();
    }

    public void checkForDeleteRow(){
        boolean rowDeleted = false;
        for(int i=1;i<GAME_BOARD_HEIGHT;i++){
            for(int j=0;j<GAME_BOARD_WIDTH;j++){
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

        KeyFrame keyFrame = new KeyFrame(Duration.millis(550-lvl*50));

        timeline = new Timeline(new KeyFrame(Duration.millis(550-lvl*50), new EventHandler<ActionEvent>() {
            double deltaY = 30;
            @Override
            public void handle(ActionEvent event) {

                figure.setLayoutY(deltaY);

                if(figure.checkBottomBorder(gameBoard)){
                    figure.getListOfRectangles().forEach(m -> {
                        System.out.println("X: "+m.getLayoutX()+" Y: " +m.getLayoutY() + " // X: " + (int) m.getLayoutY()/30 + " Y: " + (int) m.getLayoutX()/30);

                    });
                    if(figure.getMinY() <= 29){
                        endGame();
                        timeline.stop();
                    }
                    else {
                        gameBoard = figure.changeGameBoard(gameBoard);
                        checkForDeleteRow();
                        newFigure();
                        timeline.playFromStart();
                    }
                }

            }
        }));
        timeline.setDelay(Duration.ONE);
        timeline.setAutoReverse(true);
        timeline.setCycleCount(40);
        timeline.play();

    }

    // Rest

    public boolean isAvailableToMoveD(){
        int x = (int) figure.getMaxX()/30;

        if(figure.getMaxX() >= GAME_BOARD_WIDTH*30 - 30) return false;

        int minY = (int) figure.getMinY()/30 + 1;
        int maxY = (int) figure.getMaxY()/30 + 1;

        for(int i=minY;i<=maxY;i++){
            if(gameBoard[i][x+1] == 1) return false;
        }
        return true;
    }

    public boolean isAvailableToMoveA(){
        int x = (int) figure.getMinX()/30;

        if(figure.getMinX() <= 25) return false;

        int minY = (int) figure.getMinY()/30 + 1;
        int maxY = (int) figure.getMaxY()/30 + 1;

        for(int i=minY;i<=maxY;i++){
            if(gameBoard[i][x-1] == 1) return false;
        }
        return true;
    }

    public void userUseS(){
        if(isSAvailable()){
            points+=5;
            refreshLabels();
            figure.setLayoutY(30);
        }
    }


    public boolean isSAvailable(){

        return getFigure().getListOfRectangles()
                .stream()
                .allMatch(m -> {
                    int x =(int) m.getLayoutX()/30;
                    int y = (int) m.getLayoutY()/30;
                    if(y == GAME_BOARD_HEIGHT -2 || y == GAME_BOARD_HEIGHT - 1){
                        return false;
                    }
                    else if(gameBoard[y+2][x] == 1){
                        return false;
                    }
                    return true;
        });
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
