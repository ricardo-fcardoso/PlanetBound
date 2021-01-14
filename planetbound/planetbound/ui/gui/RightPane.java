package planetbound.ui.gui;

import planetbound.logic.data.ObservableGame;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.layout.VBox;
import planetbound.logic.states.*;

public class RightPane extends VBox implements PropertyChangeListener {
    ObservableGame game;
    VBox father;
    ScrollPane logScroll;
    Label message, label;
    
    public RightPane(ObservableGame game, VBox father) {
        this.game = game;
        this.father = father;
        game.addPropertyChangeListener(this);
        
        criaComponentes();
        dispoeVista();
    }
    
    protected final void criaComponentes() {
        logScroll = new ScrollPane();
        message = new Label();
        label = new Label("Log Data");
    }
    
    protected final void dispoeVista() {
        message.setId("log");
        message.setPadding(new Insets(10, 10, 10, 10));

        logScroll.setContent(message);
        logScroll.setMinHeight(450);
        logScroll.setMaxWidth(400);
        logScroll.setMaxHeight(500);
        logScroll.setHbarPolicy(ScrollPane.ScrollBarPolicy.NEVER);
        logScroll.setVbarPolicy(ScrollPane.ScrollBarPolicy.AS_NEEDED);

        this.setPadding(new Insets(20, 0, 0, 0));
        this.setMinWidth(500);
        this.setMinHeight(500);
        this.setAlignment(Pos.CENTER);
        this.getChildren().add(label);
        this.getChildren().add(logScroll);
    }
    
    @Override
    public void propertyChange(PropertyChangeEvent evt) {
            if( game.getGame().getState() instanceof AwaitToMove || 
                game.getGame().getState() instanceof AwaitEvent || 
                game.getGame().getState() instanceof AwaitChoiceOnWhiteOrbit || 
                game.getGame().getState() instanceof AwaitChoiceOnRedOrbit || 
                game.getGame().getState() instanceof AwaitToDockToSpaceStation ||
                game.getGame().getState() instanceof AwaitDroneMovement ||
                game.getGame().getState() instanceof AwaitGameOver) {
                
                father.getChildren().clear();
                message.setText(game.getGame().getLog());
                father.getChildren().add(this);
                logScroll.setVvalue(1D);
            } 
            else {
                father.getChildren().remove(this);
            }
        }
}
