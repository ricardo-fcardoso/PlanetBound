package planetbound.ui.gui;

import planetbound.logic.data.ObservableGame;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.io.File;
import java.io.IOException;
import java.util.Optional;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Pos;
import javafx.scene.control.Alert;
import javafx.scene.control.Menu;
import javafx.scene.control.MenuBar;
import javafx.scene.control.MenuItem;
import javafx.scene.control.TextInputDialog;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundImage;
import javafx.scene.layout.BackgroundPosition;
import javafx.scene.layout.BackgroundRepeat;
import javafx.scene.layout.BackgroundSize;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import planetbound.logic.data.MilitaryShip;
import planetbound.logic.data.MiningShip;
import planetbound.logic.states.*;

import static planetbound.logic.data.Constants.ARTEFACT;

public class TopPane extends VBox implements PropertyChangeListener, Constants {
    ObservableGame game;
    VBox father;
    HBox cardsBox, fuelBox, crewBox, cargoBox, shieldsBox;
    Rectangle fuelRectangle, crewRectangle, cargoRectangle, shieldsRectangle, artefactRectangle;
    BackgroundImage fuelBackground, crewBackground, cargoBackground, shieldsBackground;
    
    MenuBar menuBar;
    Menu menuOptions;
    MenuItem save, load;
    
    public TopPane(ObservableGame game, VBox father) {
        this.game = game;
        this.father = father;
        game.addPropertyChangeListener(this);

        criaComponentes();
        dispoeVista();
        registaListeners();
    }
    
    protected final void criaComponentes() {
        // father box
        cardsBox = new HBox();
        
        // children boxes
        fuelBox = new HBox();
        crewBox = new HBox();
        cargoBox = new HBox();
        shieldsBox = new HBox();
        
        fuelRectangle = new Rectangle(30, 30, Color.RED);
        crewRectangle = new Rectangle(50, 50, Color.RED);
        cargoRectangle = new Rectangle(60, 30, Color.RED);
        shieldsRectangle = new Rectangle(30, 30, Color.RED);
        artefactRectangle = new Rectangle(34, 32, Color.RED);
        
        menuBar = new MenuBar();
        menuOptions = new Menu("Options");
        save = new MenuItem("Save");
        load = new MenuItem("Load");
    }
    
    protected final void dispoeVista() {
        fuelBox.setMinWidth(420);
        fuelBox.getChildren().add(fuelRectangle);
        crewBox.setMinWidth(420);
        crewBox.getChildren().addAll(crewRectangle, artefactRectangle);
        cargoBox.setMinWidth(420);
        cargoBox.getChildren().add(cargoRectangle);
        shieldsBox.setMinWidth(420);
        shieldsBox.getChildren().addAll(shieldsRectangle);
        
        // Rectangles
        fuelRectangle.setFill(Color.TRANSPARENT);
        fuelRectangle.setStroke(Color.RED);
        fuelRectangle.setStrokeWidth(4);
        fuelRectangle.setArcWidth(5);
        fuelRectangle.setArcHeight(5);
        
        crewRectangle.setFill(Color.TRANSPARENT);
        crewRectangle.setStroke(Color.RED);
        crewRectangle.setStrokeWidth(5);
        crewRectangle.setArcWidth(5);
        crewRectangle.setArcHeight(5);
        
        cargoRectangle.setFill(Color.TRANSPARENT);
        cargoRectangle.setStroke(Color.RED);
        cargoRectangle.setStrokeWidth(4);
        cargoRectangle.setArcWidth(5);
        cargoRectangle.setArcHeight(5);
        
        shieldsRectangle.setFill(Color.TRANSPARENT);
        shieldsRectangle.setStroke(Color.YELLOW);
        shieldsRectangle.setStrokeWidth(4);
        shieldsRectangle.setArcWidth(5);
        shieldsRectangle.setArcHeight(5);
        
        artefactRectangle.setFill(Color.TRANSPARENT);
        artefactRectangle.setStroke(Color.RED);
        artefactRectangle.setStrokeWidth(4);
        artefactRectangle.setArcWidth(5);
        artefactRectangle.setArcHeight(5);
        artefactRectangle.setVisible(false);
        
        cardsBox.setMinHeight(250);
        cardsBox.getChildren().addAll(fuelBox, crewBox, cargoBox, shieldsBox);

        menuOptions.getItems().addAll(save, load);
        menuBar.getMenus().addAll(menuOptions);

        this.getChildren().add(menuBar);
        this.getChildren().add(cardsBox);
        this.setSpacing(10);
        this.setAlignment(Pos.CENTER);
    }
    
    protected final void registaListeners() {
        load.setOnAction((new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                FileChooser chooser = new FileChooser();
                chooser.setInitialDirectory(new File("."));
                File selectedFile = chooser.showOpenDialog(new Stage());
                
                if (selectedFile != null) {
                    try {
                        game.retrieveGameFromFile(selectedFile);
                    } catch (IOException | ClassNotFoundException ex) {
                        Logger.getLogger(MainPane.class.getName()).log(Level.SEVERE, null, ex);
                    }
                }
            }
        }));
        
        save.setOnAction((new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                TextInputDialog dialog = new TextInputDialog();
                dialog.setTitle("Choose a name for the file");
                dialog.setContentText("Name:");
                dialog.setHeaderText(null);
                Optional<String> result = dialog.showAndWait();
                if (result.isPresent()) {
                    try {
                        game.saveGameToFile(result.get());
                    } catch (IOException | ClassNotFoundException exception) {
                        Alert dialogResult = new Alert(Alert.AlertType.ERROR);
                        dialogResult.setHeaderText("Save Failed");
                        dialogResult.setContentText("Operation failed: " + exception);
                        dialogResult.showAndWait();
                    }
                }
            }
        }));
    }
    
    @Override
    public void propertyChange(PropertyChangeEvent evt) {
        if( game.getGame().getState() instanceof AwaitToMove || 
            game.getGame().getState() instanceof AwaitEvent || 
            game.getGame().getState() instanceof AwaitChoiceOnWhiteOrbit || 
            game.getGame().getState() instanceof AwaitChoiceOnRedOrbit || 
            game.getGame().getState() instanceof AwaitToDockToSpaceStation)  {
            
            cardsBox.getChildren().clear();
            father.getChildren().clear();

            if(game.getGame().getShip() instanceof MilitaryShip) {
                fuelBackground = new BackgroundImage(   Images.getImagem(MILITARY_FUEL), 
                                                        BackgroundRepeat.NO_REPEAT, 
                                                        BackgroundRepeat.NO_REPEAT, 
                                                        BackgroundPosition.DEFAULT, 
                                                        BackgroundSize.DEFAULT);

                crewBackground = new BackgroundImage(   Images.getImagem(MILITARY_CREW), 
                                                        BackgroundRepeat.NO_REPEAT, 
                                                        BackgroundRepeat.NO_REPEAT, 
                                                        BackgroundPosition.DEFAULT, 
                                                        BackgroundSize.DEFAULT);

                cargoBackground = new BackgroundImage(  Images.getImagem(MILITARY_CARGO), 
                                                        BackgroundRepeat.NO_REPEAT, 
                                                        BackgroundRepeat.NO_REPEAT, 
                                                        BackgroundPosition.DEFAULT, 
                                                        BackgroundSize.DEFAULT);

                shieldsBackground = new BackgroundImage(    Images.getImagem(MILITARY_SHIELDS_WEAPONS), 
                                                            BackgroundRepeat.NO_REPEAT, 
                                                            BackgroundRepeat.NO_REPEAT, 
                                                            BackgroundPosition.DEFAULT, 
                                                            BackgroundSize.DEFAULT);
                
                // Fuel Card
                switch(game.getGame().getShip().getFuel()) {
                    case 35:
                        fuelRectangle.setTranslateX(28);
                        fuelRectangle.setTranslateY(183);
                        break;
                    case 34:
                        fuelRectangle.setTranslateX(67);
                        fuelRectangle.setTranslateY(183);
                        break;
                    case 33:
                        fuelRectangle.setTranslateX(106);
                        fuelRectangle.setTranslateY(183);
                        break;
                    case 32:
                        fuelRectangle.setTranslateX(147);
                        fuelRectangle.setTranslateY(183);
                        break;
                    case 31:
                        fuelRectangle.setTranslateX(186);
                        fuelRectangle.setTranslateY(183);
                        break;
                    case 30:
                        fuelRectangle.setTranslateX(225);
                        fuelRectangle.setTranslateY(183);
                        break;
                    case 29:
                        fuelRectangle.setTranslateX(265);
                        fuelRectangle.setTranslateY(183);
                        break;
                    case 28:
                        fuelRectangle.setTranslateX(305);
                        fuelRectangle.setTranslateY(183);
                        break;
                    case 27:
                        fuelRectangle.setTranslateX(344);
                        fuelRectangle.setTranslateY(183);
                        break;
                    case 26:
                        fuelRectangle.setTranslateX(344);
                        fuelRectangle.setTranslateY(150);
                        break;
                    case 25:
                        fuelRectangle.setTranslateX(305);
                        fuelRectangle.setTranslateY(150); 
                        break;
                    case 24:
                        fuelRectangle.setTranslateX(265);
                        fuelRectangle.setTranslateY(150);
                        break;
                    case 23:
                        fuelRectangle.setTranslateX(225);
                        fuelRectangle.setTranslateY(150);
                        break;
                    case 22:
                        fuelRectangle.setTranslateX(186);
                        fuelRectangle.setTranslateY(150);
                        break;
                    case 21:
                        fuelRectangle.setTranslateX(147);
                        fuelRectangle.setTranslateY(150);
                        break;
                    case 20:
                        fuelRectangle.setTranslateX(106);
                        fuelRectangle.setTranslateY(150);
                        break;  
                    case 19:
                        fuelRectangle.setTranslateX(67);
                        fuelRectangle.setTranslateY(150);
                        break;
                    case 18:
                        fuelRectangle.setTranslateX(28);
                        fuelRectangle.setTranslateY(150);
                        break;
                    case 17:
                        fuelRectangle.setTranslateX(28);
                        fuelRectangle.setTranslateY(117);
                        break;
                    case 16:
                        fuelRectangle.setTranslateX(67);
                        fuelRectangle.setTranslateY(117);
                        break;
                    case 15:
                        fuelRectangle.setTranslateX(106);
                        fuelRectangle.setTranslateY(117);
                        break;
                    case 14:
                        fuelRectangle.setTranslateX(147);
                        fuelRectangle.setTranslateY(117);
                        break;
                    case 13:
                        fuelRectangle.setTranslateX(186);
                        fuelRectangle.setTranslateY(117);
                        break;
                    case 12:
                        fuelRectangle.setTranslateX(225);
                        fuelRectangle.setTranslateY(117);
                        break;
                    case 11:
                        fuelRectangle.setTranslateX(265);
                        fuelRectangle.setTranslateY(117);
                        break;
                    case 10:
                        fuelRectangle.setTranslateX(305);
                        fuelRectangle.setTranslateY(117);
                        break;
                    case 9:
                        fuelRectangle.setTranslateX(344);
                        fuelRectangle.setTranslateY(117);
                        break;
                    case 8:
                        fuelRectangle.setTranslateX(344);
                        fuelRectangle.setTranslateY(84);
                        break;
                    case 7:
                        fuelRectangle.setTranslateX(305);
                        fuelRectangle.setTranslateY(84);
                        break;
                    case 6:
                        fuelRectangle.setTranslateX(265);
                        fuelRectangle.setTranslateY(84);
                        break;
                    case 5:
                        fuelRectangle.setTranslateX(225);
                        fuelRectangle.setTranslateY(84);
                        break;
                    case 4:
                        fuelRectangle.setTranslateX(186);
                        fuelRectangle.setTranslateY(84);
                        break;
                    case 3:
                        fuelRectangle.setTranslateX(147);
                        fuelRectangle.setTranslateY(84);
                        break;
                    case 2:
                        fuelRectangle.setTranslateX(106);
                        fuelRectangle.setTranslateY(84);
                        break;
                    case 1:
                        fuelRectangle.setTranslateX(67);
                        fuelRectangle.setTranslateY(84);
                        break;
                    case 0:
                        fuelRectangle.setTranslateX(28);
                        fuelRectangle.setTranslateY(84);
                        break;
                } // switch
                
                // Crew and Artifacts Card
                switch(game.getGame().getShip().getCrew().size()) {
                    case 6:
                        crewRectangle.setTranslateX(23);
                        crewRectangle.setTranslateY(9);
                        break;
                    case 5:
                        crewRectangle.setTranslateX(86);
                        crewRectangle.setTranslateY(10);
                        break;
                    case 4:
                        crewRectangle.setTranslateX(150);
                        crewRectangle.setTranslateY(10);
                        break;
                    case 3: 
                        crewRectangle.setTranslateX(214);
                        crewRectangle.setTranslateY(10);
                        break;
                    case 2:
                        crewRectangle.setTranslateX(280);
                        crewRectangle.setTranslateY(10);
                        break;
                    case 1:
                        crewRectangle.setTranslateX(340);
                        crewRectangle.setTranslateY(10);
                        break;
                }
                
                if(game.getGame().getShip().getCargo().getResourceUnits(ARTEFACT) > 0) {
                    artefactRectangle.setVisible(true);
                    switch(game.getGame().getShip().getCargo().getResourceUnits(ARTEFACT)) {
                        case 1:
                            artefactRectangle.setTranslateX(305);
                            artefactRectangle.setTranslateY(200);
                            break;
                        case 2:
                            artefactRectangle.setTranslateX(265);
                            artefactRectangle.setTranslateY(200);
                            break;
                        case 3:
                            artefactRectangle.setTranslateX(228);
                            artefactRectangle.setTranslateY(200);
                            break;
                        case 4:
                            artefactRectangle.setTranslateX(190);
                            artefactRectangle.setTranslateY(200);
                            break;
                        case 5:
                            artefactRectangle.setTranslateX(152);
                            artefactRectangle.setTranslateY(200);
                            break;
                    }
                }
                
                // Cargo Card
                switch(game.getGame().getShip().getCargo().getLevel()) {
                    case 0:
                        cargoRectangle.setTranslateX(340);
                        cargoRectangle.setTranslateY(199);
                        break;
                    case 1:
                        cargoRectangle.setTranslateX(340);
                        cargoRectangle.setTranslateY(143);
                        break;
                }
                
                // Shields Card
                switch(game.getGame().getShip().getShields()) {
                    case 9:
                        shieldsRectangle.setTranslateX(347);
                        shieldsRectangle.setTranslateY(40);
                        break;
                    case 8:
                        shieldsRectangle.setTranslateX(308);
                        shieldsRectangle.setTranslateY(40);
                        break;
                    case 7:
                        shieldsRectangle.setTranslateX(268);
                        shieldsRectangle.setTranslateY(40);
                        break;
                    case 6:
                        shieldsRectangle.setTranslateX(229);
                        shieldsRectangle.setTranslateY(40);
                        break;
                    case 5:
                        shieldsRectangle.setTranslateX(188);
                        shieldsRectangle.setTranslateY(40);
                        break;
                    case 4:
                        shieldsRectangle.setTranslateX(149);
                        shieldsRectangle.setTranslateY(40);
                        break;
                    case 3:
                        shieldsRectangle.setTranslateX(110);
                        shieldsRectangle.setTranslateY(40);
                        break;
                    case 2:
                        shieldsRectangle.setTranslateX(71);
                        shieldsRectangle.setTranslateY(40);
                        break;
                    case 1:
                        shieldsRectangle.setTranslateX(30);
                        shieldsRectangle.setTranslateY(40);
                        break;
                    case 0:
                        shieldsRectangle.setVisible(false);
                }
                
            } // if
            else if(game.getGame().getShip() instanceof MiningShip) {
                fuelBackground = new BackgroundImage(   Images.getImagem(MINING_FUEL), 
                                                        BackgroundRepeat.NO_REPEAT, 
                                                        BackgroundRepeat.NO_REPEAT, 
                                                        BackgroundPosition.DEFAULT, 
                                                        BackgroundSize.DEFAULT);

                crewBackground = new BackgroundImage(   Images.getImagem(MINING_CREW), 
                                                        BackgroundRepeat.NO_REPEAT, 
                                                        BackgroundRepeat.NO_REPEAT, 
                                                        BackgroundPosition.DEFAULT, 
                                                        BackgroundSize.DEFAULT);

                cargoBackground = new BackgroundImage(  Images.getImagem(MINING_CARGO), 
                                                        BackgroundRepeat.NO_REPEAT, 
                                                        BackgroundRepeat.NO_REPEAT, 
                                                        BackgroundPosition.DEFAULT, 
                                                        BackgroundSize.DEFAULT);

                shieldsBackground = new BackgroundImage(    Images.getImagem(MINING_SHIELDS_WEAPONS), 
                                                            BackgroundRepeat.NO_REPEAT, 
                                                            BackgroundRepeat.NO_REPEAT, 
                                                            BackgroundPosition.DEFAULT, 
                                                            BackgroundSize.DEFAULT);
                
                // Fuel Card
                switch(game.getGame().getShip().getFuel()) {
                    case 53:
                        fuelRectangle.setTranslateX(33);
                        fuelRectangle.setTranslateY(203);
                        break;
                    case 52:
                        fuelRectangle.setTranslateX(73);
                        fuelRectangle.setTranslateY(203);
                        break;
                    case 51:
                        fuelRectangle.setTranslateX(113);
                        fuelRectangle.setTranslateY(203);
                        break;
                    case 50:
                        fuelRectangle.setTranslateX(152);
                        fuelRectangle.setTranslateY(203);
                        break;
                    case 49:
                        fuelRectangle.setTranslateX(192);
                        fuelRectangle.setTranslateY(203);
                        break;
                    case 48:
                        fuelRectangle.setTranslateX(232);
                        fuelRectangle.setTranslateY(203);
                        break;
                    case 47:
                        fuelRectangle.setTranslateX(271);
                        fuelRectangle.setTranslateY(203);
                        break;
                    case 46:
                        fuelRectangle.setTranslateX(311);
                        fuelRectangle.setTranslateY(203);
                        break;
                    case 45:
                        fuelRectangle.setTranslateX(350);
                        fuelRectangle.setTranslateY(203);
                        break;
                    case 44:
                        fuelRectangle.setTranslateX(350);
                        fuelRectangle.setTranslateY(170);
                        break;
                    case 43:
                        fuelRectangle.setTranslateX(311);
                        fuelRectangle.setTranslateY(170);
                        break;
                    case 42:
                        fuelRectangle.setTranslateX(271);
                        fuelRectangle.setTranslateY(170);
                        break;
                    case 41:
                        fuelRectangle.setTranslateX(232);
                        fuelRectangle.setTranslateY(170);
                        break;
                    case 40:
                        fuelRectangle.setTranslateX(192);
                        fuelRectangle.setTranslateY(170);
                        break;
                    case 39:
                        fuelRectangle.setTranslateX(152);
                        fuelRectangle.setTranslateY(170);
                        break;
                    case 38:
                        fuelRectangle.setTranslateX(113);
                        fuelRectangle.setTranslateY(170);
                        break;
                    case 37:
                        fuelRectangle.setTranslateX(73);
                        fuelRectangle.setTranslateY(170);
                        break;
                    case 36:
                        fuelRectangle.setTranslateX(33);
                        fuelRectangle.setTranslateY(170);
                        break;
                    case 35:
                        fuelRectangle.setTranslateX(33);
                        fuelRectangle.setTranslateY(136);
                        break;
                    case 34:
                        fuelRectangle.setTranslateX(73);
                        fuelRectangle.setTranslateY(136);
                        break;
                    case 33:
                        fuelRectangle.setTranslateX(113);
                        fuelRectangle.setTranslateY(136);
                        break;
                    case 32:
                        fuelRectangle.setTranslateX(152);
                        fuelRectangle.setTranslateY(136);
                        break;
                    case 31:
                        fuelRectangle.setTranslateX(192);
                        fuelRectangle.setTranslateY(136);
                        break;
                    case 30:
                        fuelRectangle.setTranslateX(232);
                        fuelRectangle.setTranslateY(136);
                        break;
                    case 29:
                        fuelRectangle.setTranslateX(271);
                        fuelRectangle.setTranslateY(136);
                        break;
                    case 28:
                        fuelRectangle.setTranslateX(311);
                        fuelRectangle.setTranslateY(136);
                        break;
                    case 27:
                        fuelRectangle.setTranslateX(350);
                        fuelRectangle.setTranslateY(136);
                        break;
                    case 26:
                        fuelRectangle.setTranslateX(350);
                        fuelRectangle.setTranslateY(103);
                        break;
                    case 25:
                        fuelRectangle.setTranslateX(311);
                        fuelRectangle.setTranslateY(103);
                        break;
                    case 24:
                        fuelRectangle.setTranslateX(271);
                        fuelRectangle.setTranslateY(103);
                        break;
                    case 23:
                        fuelRectangle.setTranslateX(232);
                        fuelRectangle.setTranslateY(103);
                        break;
                    case 22:
                        fuelRectangle.setTranslateX(192);
                        fuelRectangle.setTranslateY(103);
                        break;
                    case 21:
                        fuelRectangle.setTranslateX(152);
                        fuelRectangle.setTranslateY(103);
                        break;
                    case 20:
                        fuelRectangle.setTranslateX(113);
                        fuelRectangle.setTranslateY(103);
                        break;
                    case 19:
                        fuelRectangle.setTranslateX(73);
                        fuelRectangle.setTranslateY(103);
                        break;
                    case 18:
                        fuelRectangle.setTranslateX(33);
                        fuelRectangle.setTranslateY(103);
                        break;
                    case 17:
                        fuelRectangle.setTranslateX(33);
                        fuelRectangle.setTranslateY(70);
                        break;
                    case 16:
                        fuelRectangle.setTranslateX(73);
                        fuelRectangle.setTranslateY(70);
                        break;
                    case 15:
                        fuelRectangle.setTranslateX(113);
                        fuelRectangle.setTranslateY(70);
                        break;
                    case 14:
                        fuelRectangle.setTranslateX(152);
                        fuelRectangle.setTranslateY(70);
                        break;
                    case 13:
                        fuelRectangle.setTranslateX(192);
                        fuelRectangle.setTranslateY(70);
                        break;
                    case 12:
                        fuelRectangle.setTranslateX(232);
                        fuelRectangle.setTranslateY(70);
                        break;
                    case 11:
                        fuelRectangle.setTranslateX(271);
                        fuelRectangle.setTranslateY(70);
                        break;
                    case 10:
                        fuelRectangle.setTranslateX(311);
                        fuelRectangle.setTranslateY(70);
                        break;
                    case 9:
                        fuelRectangle.setTranslateX(350);
                        fuelRectangle.setTranslateY(70);
                        break;
                    case 8:
                        fuelRectangle.setTranslateX(350);
                        fuelRectangle.setTranslateY(37);
                        break;
                    case 7:
                        fuelRectangle.setTranslateX(311);
                        fuelRectangle.setTranslateY(37);
                        break;
                    case 6:
                        fuelRectangle.setTranslateX(271);
                        fuelRectangle.setTranslateY(37);
                        break;
                    case 5:
                        fuelRectangle.setTranslateX(232);
                        fuelRectangle.setTranslateY(37);
                        break;
                    case 4:
                        fuelRectangle.setTranslateX(192);
                        fuelRectangle.setTranslateY(37);
                        break;
                    case 3:
                        fuelRectangle.setTranslateX(152);
                        fuelRectangle.setTranslateY(37);
                        break;
                    case 2:
                        fuelRectangle.setTranslateX(113);
                        fuelRectangle.setTranslateY(37);
                        break;
                    case 1:
                        fuelRectangle.setTranslateX(73);
                        fuelRectangle.setTranslateY(37);
                        break;
                    case 0:
                        fuelRectangle.setTranslateX(33);
                        fuelRectangle.setTranslateY(37);
                        break;
                } // switch
                
                // Crew and Artefact Card
                switch(game.getGame().getShip().getCrew().size()) {
                    case 6:
                        crewRectangle.setTranslateX(23);
                        crewRectangle.setTranslateY(9);
                        break;
                    case 5:
                        crewRectangle.setTranslateX(86);
                        crewRectangle.setTranslateY(10);
                        break;
                    case 4:
                        crewRectangle.setTranslateX(150);
                        crewRectangle.setTranslateY(10);
                        break;
                    case 3: 
                        crewRectangle.setTranslateX(214);
                        crewRectangle.setTranslateY(10);
                        break;
                    case 2:
                        crewRectangle.setTranslateX(278);
                        crewRectangle.setTranslateY(10);
                        break;
                    case 1:
                        crewRectangle.setTranslateX(340);
                        crewRectangle.setTranslateY(10);
                        break;
                }
                
                if(game.getGame().getShip().getCargo().getResourceUnits(ARTEFACT) > 0) {
                    artefactRectangle.setVisible(true);
                    switch(game.getGame().getShip().getCargo().getResourceUnits(ARTEFACT)) {
                        case 1:
                            artefactRectangle.setTranslateX(305);
                            artefactRectangle.setTranslateY(200);
                            break;
                        case 2:
                            artefactRectangle.setTranslateX(267);
                            artefactRectangle.setTranslateY(200);
                            break;
                        case 3:
                            artefactRectangle.setTranslateX(230);
                            artefactRectangle.setTranslateY(200);
                            break;
                        case 4:
                            artefactRectangle.setTranslateX(192);
                            artefactRectangle.setTranslateY(200);
                            break;
                        case 5:
                            artefactRectangle.setTranslateX(154);
                            artefactRectangle.setTranslateY(200);
                            break;
                    }
                }
                
                switch(game.getGame().getShip().getCargo().getLevel()) {
                    case 0:
                        cargoRectangle.setTranslateX(341);
                        cargoRectangle.setTranslateY(200);
                        break;
                    case 1:
                        cargoRectangle.setTranslateX(341);
                        cargoRectangle.setTranslateY(145);
                        break;
                    case 2:
                        cargoRectangle.setTranslateX(341);
                        cargoRectangle.setTranslateY(88);
                        break;
                    case 3:
                        cargoRectangle.setTranslateX(341);
                        cargoRectangle.setTranslateY(31);
                        break;
                }
                
                switch(game.getGame().getShip().getShields()) {
                    case 18:
                        shieldsRectangle.setTranslateX(35);
                        shieldsRectangle.setTranslateY(97);
                        break;
                    case 17:
                        shieldsRectangle.setTranslateX(75);
                        shieldsRectangle.setTranslateY(97);
                        break;
                    case 16:
                        shieldsRectangle.setTranslateX(115);
                        shieldsRectangle.setTranslateY(97);
                        break;
                    case 15:
                        shieldsRectangle.setTranslateX(155);
                        shieldsRectangle.setTranslateY(97);
                        break;
                    case 14:
                        shieldsRectangle.setTranslateX(193);
                        shieldsRectangle.setTranslateY(97);
                        break;
                    case 13:
                        shieldsRectangle.setTranslateX(235);
                        shieldsRectangle.setTranslateY(97);
                        break;
                    case 12:
                        shieldsRectangle.setTranslateX(273);
                        shieldsRectangle.setTranslateY(97);
                        break;
                    case 11:
                        shieldsRectangle.setTranslateX(315);
                        shieldsRectangle.setTranslateY(97);
                        break;
                    case 10:
                        shieldsRectangle.setTranslateX(350);
                        shieldsRectangle.setTranslateY(97);
                        break;
                    case 9:
                        shieldsRectangle.setTranslateX(355);
                        shieldsRectangle.setTranslateY(61);
                        break;
                    case 8:
                        shieldsRectangle.setTranslateX(312);
                        shieldsRectangle.setTranslateY(61);
                        break;
                    case 7:
                        shieldsRectangle.setTranslateX(275);
                        shieldsRectangle.setTranslateY(61);
                        break;
                    case 6:
                        shieldsRectangle.setTranslateX(232);
                        shieldsRectangle.setTranslateY(61);
                        break;
                    case 5:
                        shieldsRectangle.setTranslateX(195);
                        shieldsRectangle.setTranslateY(61);
                        break;
                    case 4:
                        shieldsRectangle.setTranslateX(153);
                        shieldsRectangle.setTranslateY(61);
                        break;
                    case 3:
                        shieldsRectangle.setTranslateX(115);
                        shieldsRectangle.setTranslateY(61);
                        break;
                    case 2:
                        shieldsRectangle.setTranslateX(75);
                        shieldsRectangle.setTranslateY(61);
                        break;
                    case 1:
                        shieldsRectangle.setTranslateX(35);
                        shieldsRectangle.setTranslateY(61);
                        break;
                    case 0:
                        shieldsRectangle.setVisible(false);
                }
            }

            fuelBox.setBackground(new Background(fuelBackground));
            crewBox.setBackground(new Background(crewBackground));
            cargoBox.setBackground(new Background(cargoBackground));
            shieldsBox.setBackground((new Background(shieldsBackground)));
            cardsBox.getChildren().addAll(fuelBox, crewBox, cargoBox, shieldsBox);

            father.getChildren().add(this);
        }
        if(game.getGame().getState() instanceof AwaitGameOver) {
            father.getChildren().remove(this);
        }
    }
}
