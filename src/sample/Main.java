package sample;

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
        primaryStage.setTitle("Tetris");
        FXMLLoader loader = new FXMLLoader(getClass().getResource("sample.fxml"));
        Parent root = loader.load();


        Controller controller = loader.getController();
        primaryStage.addEventHandler(KeyEvent.KEY_PRESSED, (KeyEvent event) -> {
            if (KeyCode.D == event.getCode() && !controller.getStop()) {
                controller.userUseD();
            }
            if (KeyCode.A == event.getCode() && !controller.getStop()) {
                controller.userUseA();
            }
            if(KeyCode.S == event.getCode() && !controller.getStop()){
                if(controller.getFigure().getMaxY() <= 570 && controller.getFigure().getMinY() >= 60){
                    controller.userUseS();
                }

            }
            if(KeyCode.W == event.getCode() && !controller.getStop()){
                controller.getFigure().rotateFigure(controller.getGameBoard());
                if(controller.getMinY() < 60){
                    controller.getFigure().getListOfRectangles().forEach(m -> {
                        if(m.getLayoutY()<= 30){
                            m.setVisible(false);
                        }
                        else{
                            m.setVisible(true);
                        }
                    });
                }
            }
        });


        Scene scene = new Scene(root,560,900);
        primaryStage.setScene(scene);
        primaryStage.show();
    }


    public static void main(String[] args) {
        launch(args);
    }
}
