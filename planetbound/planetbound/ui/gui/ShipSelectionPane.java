package planetbound.ui.gui;

import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import planetbound.logic.data.ObservableGame;
import planetbound.logic.states.AwaitShipSelection;

public class ShipSelectionPane extends VBox implements PropertyChangeListener, Constants {
    ObservableGame game;
    Button miningShipButton, militaryShipButton;
    Label miningShipLabel, militaryShipLabel;
    VBox father, militaryShipVBox, miningShipVBox;
    HBox hBox;
    Image militaryShip, miningShip;
    ImageView militaryShipView, miningShipView;
    
    public ShipSelectionPane(ObservableGame game, VBox father) {
        this.game = game;
        this.father = father;
        game.addPropertyChangeListener(this);
        
        criaComponentes();
        dispoeVista();
        registaListeners();
    }
    
    protected final void criaComponentes() {
        hBox = new HBox(10);

        militaryShipVBox = new VBox(10);
        miningShipVBox = new VBox(10);

        miningShipButton = new Button();
        militaryShipButton = new Button();

        miningShipLabel = new Label("Mining Ship");
        militaryShipLabel = new Label("Military Ship");

        miningShipVBox.setId("shipButtonsVBox");
        militaryShipVBox.setId("shipButtonsVBox");

        militaryShip = Images.getImagem(MILITARY_SHIP);
        militaryShipView = new ImageView(militaryShip);

        miningShip = Images.getImagem(MINING_SHIP);
        miningShipView = new ImageView(miningShip);
    }
    
    protected final void dispoeVista() {
        militaryShipView.setFitHeight(250);
        militaryShipView.setFitWidth(400);

        miningShipView.setFitHeight(250);
        miningShipView.setFitWidth(400);

        militaryShipVBox.getChildren().add(militaryShipView);
        militaryShipVBox.getChildren().add(militaryShipLabel);
        militaryShipVBox.setAlignment(Pos.CENTER);
        militaryShipButton.setGraphic(militaryShipVBox);

        miningShipVBox.getChildren().add(miningShipView);
        miningShipVBox.getChildren().add(miningShipLabel);
        miningShipVBox.setAlignment(Pos.CENTER);
        miningShipButton.setGraphic(miningShipVBox);
        
        hBox.setAlignment(Pos.CENTER);
        hBox.getChildren().add(militaryShipButton);
        hBox.getChildren().add(miningShipButton);

        this.getChildren().add(hBox);
        this.setAlignment(Pos.CENTER);
    }
    
    protected final void registaListeners() {
        militaryShipButton.setOnAction(event -> game.selectShipType(1));
        miningShipButton.setOnAction(event -> game.selectShipType(2));
    }
    
    @Override
    public void propertyChange(PropertyChangeEvent evt) {
        if(game.getGame().getState() instanceof AwaitShipSelection) {
            if(!father.getChildren().contains(this)) {
                father.getChildren().add(this);
            }
        } else {
            father.getChildren().remove(this);
        }
    }
}
