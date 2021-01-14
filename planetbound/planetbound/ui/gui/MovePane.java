package planetbound.ui.gui;

import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Pos;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import planetbound.logic.data.ObservableGame;
import planetbound.logic.states.AwaitToMove;

public class MovePane extends VBox implements PropertyChangeListener {
    
    ObservableGame game;
    VBox father;
    HBox hBox;
    Button move, quit;
    
    public MovePane(ObservableGame game, VBox father) {
        this.game = game;
        this.father = father;
        game.addPropertyChangeListener(this);
        
        criaComponentes();
        dispoeVista();
        registaListeners();
    }
    
    protected final void criaComponentes() {
        move = new Button("Move");
        quit = new Button("Quit");
        hBox = new HBox(10);
    }
    
    protected final void dispoeVista() {
        move.setMinWidth(50);
        quit.setMinWidth(50);
        
        hBox.setAlignment(Pos.CENTER);
        hBox.getChildren().add(move);
        hBox.getChildren().add(quit);
        
        this.getChildren().add(hBox);
        this.setAlignment(Pos.CENTER);
    }
    
    protected final void registaListeners() {
        move.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                game.moveToNextSpaceLocation();
            }
        });
        
        quit.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                Alert alert = new Alert(Alert.AlertType.WARNING, "Are you sure you want to quit the game?", ButtonType.YES, ButtonType.NO);
                alert.setTitle("Confirmation Dialog");
                alert.showAndWait();

                if(alert.getResult() == ButtonType.YES) {
                    System.exit(0);
                } else {
                    alert.close();
                }
            }
        });
    }
    
    @Override
    public void propertyChange(PropertyChangeEvent evt) {
        if(game.getGame().getState() instanceof AwaitToMove) {
            if(!father.getChildren().contains(this)) {
                father.getChildren().add(this);
            }
        } else {
            father.getChildren().remove(this);
        }
    }
}
