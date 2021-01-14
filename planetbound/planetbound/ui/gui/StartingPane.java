package planetbound.ui.gui;

import javafx.stage.FileChooser;
import javafx.stage.Stage;
import planetbound.logic.data.ObservableGame;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.io.File;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Pos;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.layout.VBox;
import planetbound.logic.states.AwaitBeginning;

public class StartingPane extends VBox implements PropertyChangeListener{
    ObservableGame game;
    Button start, quit, load;
    VBox father;
    
    public StartingPane(ObservableGame game, VBox father, Stage stage) {
        this.game = game;
        this.father = father;
        game.addPropertyChangeListener(this);
        
        criaComponentes();
        dispoeVista();
        registaListeners(stage);
    }
    
    protected final void criaComponentes() {
        start = new Button("Start Game");
        load = new Button("Load Game");
        quit = new Button("Quit");
    }
    
    protected final void dispoeVista() {
        start.setMinWidth(100);
        load.setMinWidth(100);
        quit.setMinWidth(100);
        start.setId("start");
        load.setId("load");
        quit.setId("quit");

        this.setSpacing(5);
        this.getChildren().addAll(start, load, quit);
        this.setAlignment(Pos.CENTER);
    }
    
    protected final void registaListeners(Stage stage) {
        start.setOnAction(event -> game.start());

        load.setOnAction((event -> {
            FileChooser chooser = new FileChooser();
            chooser.setInitialDirectory(new File("."));
            File selectedFile = chooser.showOpenDialog(stage);

            if (selectedFile != null) {
                try {
                    game.retrieveGameFromFile(selectedFile);
                } catch (IOException | ClassNotFoundException exception) {
                    Logger.getLogger(MainPane.class.getName()).log(Level.SEVERE, null, exception);
                }
            }
        }));
        
        quit.setOnAction(event -> {
            Alert alert = new Alert(AlertType.WARNING, "Are you sure you want to quit the game?", ButtonType.YES, ButtonType.NO);
            alert.setTitle("Confirmation Dialog");
            alert.showAndWait();

            if(alert.getResult() == ButtonType.YES) {
                System.exit(0);
            } else {
                alert.close();
            }
        });
    }
    
    @Override
    public void propertyChange(PropertyChangeEvent evt) {
        if(game.getGame().getState() instanceof AwaitBeginning) {
            father.getChildren().add(this);
        }
        else {
            father.getChildren().remove(this);
        }
    }
}
