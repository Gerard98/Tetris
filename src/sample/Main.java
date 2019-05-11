package sample;

import javafx.animation.Animation;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.stage.Stage;

public class Main extends Application {

    @Override
    public void start(Stage primaryStage) throws Exception{
        primaryStage.setTitle("Hello World");
        FXMLLoader loader = new FXMLLoader(getClass().getResource("sample.fxml"));
        Parent root = loader.load();


        Controller controller = loader.getController();
        primaryStage.addEventHandler(KeyEvent.KEY_PRESSED, (KeyEvent event) -> {
            if (KeyCode.D == event.getCode() && controller.getPlayerIsPlaying()) {
                if(controller.getFigure().getMaxX() <=180 && controller.isAvaibleToMoveD()){
                    controller.getFigure().relocateToRight();
                }

            }
            if (KeyCode.A == event.getCode() && controller.getPlayerIsPlaying()) {
                if(controller.getFigure().getMinX() >= 25 && controller.isAvaibleToMoveA()){
                    controller.getFigure().relocateToLeft();
                }

            }
            if(KeyCode.S == event.getCode() && controller.getPlayerIsPlaying()){
                //if(controller.getRectangle().getLayoutY() <= 420)
                    //controller.getRectangle().setLayoutY(controller.getRectangle().getLayoutY() + 30);
            }
            if(KeyCode.W == event.getCode() && controller.getPlayerIsPlaying()){
                controller.getFigure().rotateFigure(controller.getGameBoard());
            }
        });


        Scene scene = new Scene(root,800,800);
        primaryStage.setScene(scene);
        primaryStage.show();
    }


    public static void main(String[] args) {
        launch(args);
    }
}
