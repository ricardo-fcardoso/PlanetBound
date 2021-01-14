package planetbound.ui.gui;

import java.io.IOException;
import javafx.application.Application;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.*;
import javafx.stage.Stage;
import planetbound.logic.PlanetBound;
import planetbound.logic.data.ObservableGame;

public class MainPane extends Application {
    
    ObservableGame game;
    Scene scene;
    StartingPane starting;
    ShipSelectionPane shipSelection;
    MovePane move;
    WhiteOrbitPane whiteOrbit;
    RedOrbitPane redOrbit;
    RedDotPane redDot;
    SpaceStationPane spaceStation;
    DroneMovementPane droneMovement;
    VBox middlePane, rightPane, topPane;
    HBox menuBox;
    RightPane rightBar;
    TopPane topBar;
    GameOverPane gameOverPane;

    @Override
    public void start(Stage primaryStage) throws IOException {
        BorderPane bPane = new BorderPane();
        Image background = new Image(getClass().getResourceAsStream("resources/space.jpg"));
        ImageView space = new ImageView(background);
        bPane.getChildren().add(space);
        scene = new Scene(bPane);
        scene.getStylesheets().add("https://fonts.googleapis.com/css2?family=Anton&display=swap");
        scene.getStylesheets().add(getClass().getResource("resources/style.css").toExternalForm());
        
        primaryStage.setTitle("Planet Bound");
        primaryStage.setScene(scene);
        primaryStage.setMinWidth(1700);
        primaryStage.setMaxWidth(1700);
        primaryStage.setMinHeight(1000);
        primaryStage.setMaxHeight(1000);
        primaryStage.setResizable(false);
        
        criaComponentes(primaryStage);
        dispoeVista(bPane);
        registaListeners(primaryStage);
        
        primaryStage.show();
        primaryStage.getIcons().add(new Image(getClass().getResourceAsStream("resources/rocket_icon.png")));
    }
    
    protected final void criaComponentes(Stage stage) throws IOException {
        middlePane = new VBox(8);
        rightPane = new VBox(8);
        topPane = new VBox(8);
        menuBox = new HBox();
        game = new ObservableGame(new PlanetBound());
        
        rightBar = new RightPane(game, rightPane);
        topBar = new TopPane(game, topPane);
        
        shipSelection = new ShipSelectionPane(game, middlePane);
        starting = new StartingPane(game, middlePane, stage);
        move = new MovePane(game, middlePane);
        whiteOrbit = new WhiteOrbitPane(game, middlePane);
        redOrbit = new RedOrbitPane(game, middlePane);
        redDot = new RedDotPane(game, middlePane);
        spaceStation = new SpaceStationPane(game, middlePane);
        droneMovement = new DroneMovementPane(game, middlePane, scene);

        gameOverPane = new GameOverPane(game, middlePane);
    }
    
    protected final void dispoeVista(BorderPane bPane) {
        middlePane.setAlignment(Pos.CENTER);
        middlePane.getChildren().add(starting);

        bPane.setCenter(middlePane);
        bPane.setRight(rightPane);
        bPane.setTop(topPane);
    }
    
    protected final void registaListeners(Stage stage) {
        stage.setOnCloseRequest(e-> {
            e.consume();
            Alert alert = new Alert(Alert.AlertType.WARNING, "Are you sure you want to quit the game?", ButtonType.YES, ButtonType.NO);
            alert.setTitle("Confirmation Dialog");
            alert.showAndWait();

            if(alert.getResult() == ButtonType.YES) {
                System.exit(0);
            } else {
                alert.close();
            }
        });
    }
    
    public static void main(String args[]) {
        launch(args);
    }
    
    public void Launch() {
        main(null);
    }
}
