package planetbound.ui.gui;

import javafx.scene.control.Label;
import planetbound.logic.data.ObservableGame;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Pos;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.layout.VBox;
import planetbound.logic.states.AwaitGameOver;

public class GameOverPane extends VBox implements PropertyChangeListener{
    ObservableGame game;
    VBox father, vBox;
    Button tryAgain, quit;
    Label gameOver;

    public GameOverPane(ObservableGame game, VBox father) {
        this.game = game;
        this.father = father;
        game.addPropertyChangeListener(this);

        criaComponentes();
        dispoeVista();
        registaListeners();
    }

    protected final void criaComponentes() {
        vBox = new VBox(10);
        tryAgain = new Button("Try Again");
        quit = new Button("Quit");
        gameOver = new Label("Game Over");
        gameOver.setId("gameover");
    }

    protected final void dispoeVista() {
        vBox.getChildren().addAll(gameOver, tryAgain, quit);
        vBox.setAlignment(Pos.CENTER);

        this.getChildren().addAll(vBox);
        this.setAlignment(Pos.CENTER);
    }

    protected final void registaListeners() {
        tryAgain.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                game.replay();
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
        if(game.getGame().getState() instanceof AwaitGameOver) {
            if(!father.getChildren().contains(this)) {
                father.getChildren().add(this);
            }
        }
        else {
            father.getChildren().remove(this);
        }
    }
}
