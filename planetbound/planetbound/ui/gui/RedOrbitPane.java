package planetbound.ui.gui;

import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.image.ImageView;
import javafx.scene.layout.VBox;
import planetbound.logic.data.ObservableGame;
import planetbound.logic.states.AwaitChoiceOnRedOrbit;

public class RedOrbitPane extends VBox implements PropertyChangeListener, Constants {
    
    ObservableGame game;
    Button land, leave, dock;
    VBox father, vBox;
    ImageView planet, spaceStation;
    
    public RedOrbitPane(ObservableGame game, VBox father) {
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
        dock = new Button("Dock to space station");
        vBox = new VBox(10);
    }
    
    protected final void dispoeVista() {
        land.minWidth(150);
        leave.minWidth(150);
        dock.minWidth(150);

        spaceStation = new ImageView(Images.getImagem(STATION));

        spaceStation.setTranslateX(300);
        spaceStation.setTranslateY(100);

        this.getChildren().add(spaceStation);
        this.getChildren().add(vBox);
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
        
        dock.setOnAction(new EventHandler<ActionEvent>() { 
            @Override
            public void handle(ActionEvent event) {
                game.dockToSpaceStation();
            }
        });
    }
    
    @Override
    public void propertyChange(PropertyChangeEvent evt) {
        if(game.getGame().getState() instanceof AwaitChoiceOnRedOrbit) {
            if(!father.getChildren().contains(this)) {
                vBox.getChildren().removeAll(planet, spaceStation, land, leave, dock);
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

                vBox.getChildren().addAll(planet);
                vBox.setAlignment(Pos.CENTER);
                vBox.getChildren().addAll(land, leave, dock);
            }
        } else {
            father.getChildren().remove(this);
        }
    }
}
