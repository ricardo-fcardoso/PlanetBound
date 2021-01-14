package planetbound.ui.gui;

import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.image.ImageView;
import javafx.scene.layout.VBox;
import planetbound.logic.data.ObservableGame;
import planetbound.logic.states.AwaitChoiceOnWhiteOrbit;

public class WhiteOrbitPane extends VBox implements PropertyChangeListener, Constants {
    ObservableGame game;
    Button land, leave;
    ComboBox<String> supplies;
    VBox father, buttons;
    ImageView planet;
    
    public WhiteOrbitPane(ObservableGame game, VBox father) {
        this.game = game;
        this.father = father;
        game.addPropertyChangeListener(this);
        
        criaComponentes();
        dispoeVista();
        registaListeners();
    }
    
    protected final void criaComponentes() {
        land = new Button("Land to mine resources");
        leave = new Button("Leave the orbit");

        supplies = new ComboBox<>();
        supplies.setVisibleRowCount(3);
        supplies.getSelectionModel().select("Convert resources into supplies");
        supplies.getItems().addAll( "Convert to shields",
                                    "Convert to ammo",
                                    "Convert to fuel");

        buttons = new VBox(10);
    }
    
    protected final void dispoeVista() {
        land.setMinWidth(220);
        leave.setMinWidth(220);
        supplies.setMinWidth(220);
        
        this.getChildren().add(buttons);
        this.setAlignment(Pos.CENTER);
    }
    
    protected final void registaListeners() {
        land.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                game.landToMineResources();
            }
        });
        
        leave.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                game.moveToNextSpaceLocation();
            }
        });
        
        supplies.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                switch(supplies.getSelectionModel().getSelectedIndex()+1) {
                    case 1:
                        game.convertResourcesIntoSupplies(1); break;
                    case 2:
                        game.convertResourcesIntoSupplies(2); break;
                    case 3:
                        game.convertResourcesIntoSupplies(3); break;
                }
            }
        });
    }
    
    @Override
    public void propertyChange(PropertyChangeEvent evt) {
        if(game.getGame().getState() instanceof AwaitChoiceOnWhiteOrbit) {
            if(!father.getChildren().contains(this)) {
                buttons.getChildren().removeAll(planet, land, leave, supplies);
                father.getChildren().add(this);

                switch(game.getGame().getPlanet().getPlanetColor()) {
                    case "Red":
                        planet = new ImageView(Images.getImagem(RED_PLANET));
                        break;
                    case "Blue":
                        planet = new ImageView(Images.getImagem(BLUE_PLANET));
                        break;
                    case "Green":
                        planet = new ImageView(Images.getImagem(GREEN_PLANET));
                        break;
                    case "Black":
                        planet = new ImageView(Images.getImagem(BLACK_PLANET));
                        break;
                }

                buttons.getChildren().add(planet);
                buttons.setAlignment(Pos.CENTER);
                buttons.getChildren().addAll(land, leave, supplies);

                supplies.getSelectionModel().select("Convert resources into supplies");
            }
        } else {
            father.getChildren().remove(this);
        }
    }
}
