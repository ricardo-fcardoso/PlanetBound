package planetbound.ui.gui;

import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.layout.VBox;
import planetbound.logic.data.ObservableGame;
import planetbound.logic.states.AwaitEvent;

public class RedDotPane extends VBox implements PropertyChangeListener {
    
    ObservableGame game;
    Button random;
    ComboBox<String> specificEvent;
    VBox father, vBox;
    
    public RedDotPane(ObservableGame game, VBox father) {
        this.game = game;
        this.father = father;
        game.addPropertyChangeListener(this);
        
        criaComponentes();
        dispoeViste();
        registaListeners();
    }
    
    protected final void criaComponentes() {
        random = new Button("Random Event");
        vBox = new VBox(10);
        specificEvent = new ComboBox<>();
        specificEvent.setVisibleRowCount(6);
        specificEvent.getSelectionModel().select("Specific Event");
        specificEvent.getItems().addAll("Crew Death", 
                                        "Salvage ship",
                                        "Cargo Loss",
                                        "Fuel loss",
                                        "No event",
                                        "Crew Rescue");
    }
    
    protected final void dispoeViste() {
        specificEvent.minWidth(300);
        random.minWidth(300);
        
        vBox.setAlignment(Pos.CENTER);
        vBox.getChildren().add(random);
        vBox.getChildren().add(specificEvent);
        
        this.getChildren().add(vBox);
        this.setAlignment(Pos.CENTER);
        this.setSpacing(10);
    }
    
    protected final void registaListeners() {
        random.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                game.doEventFromReferenceCard();
            }
        });
        
        specificEvent.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                game.doSpecificEventFromReferenceCard(specificEvent.getSelectionModel().getSelectedIndex()+1);
            }
        });
    }
    
    @Override
    public void propertyChange(PropertyChangeEvent evt) {
        if(game.getGame().getState() instanceof AwaitEvent) {
            if(!father.getChildren().contains(this)) {
                father.getChildren().add(this);
                specificEvent.getSelectionModel().select("Specific Event");
            }
        } else {
            father.getChildren().remove(this);
        }
    }
}
