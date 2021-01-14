package planetbound.ui.gui;

import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import planetbound.logic.data.ObservableGame;
import planetbound.logic.states.AwaitToDockToSpaceStation;

public class SpaceStationPane extends VBox implements PropertyChangeListener {
    ObservableGame game;
    Button submit;
    ComboBox<String> option;
    ComboBox<HBox> resourceOrigin, resourceFinal;
    VBox father, vBox;
    HBox comboBoxes;
    Label from, to;
    Label[] resourcesFromLabel;
    Label[] resourcesToLabel;
    Image emerald, saphire, ruby, onyx;
    ImageView greenFrom, blueFrom, redFrom, blackFrom;
    ImageView greenTo, blueTo, redTo, blackTo;
    
    public SpaceStationPane(ObservableGame game, VBox father) {
        this.game = game;
        this.father = father;
        game.addPropertyChangeListener(this);
        
        criaComponentes();
        dispoeVista();
        registaListeners();
    }
    
    protected final void criaComponentes() {
        vBox = new VBox(10);
        comboBoxes = new HBox(10);
        submit = new Button("Submit");
        submit.setDisable(true);
        
        createImages();
        
        option = new ComboBox<>();
        option.setVisibleRowCount(6);
        option.getSelectionModel().select("Select an option and submit");
        option.getItems().add("Leave the space station");
        option.getItems().add("Upgrade cargo");
        option.getItems().add("Convert a single resource");
        option.getItems().add("Hire a lost crew member");
        option.getItems().add("Fix mining drone");
        option.getItems().add("Buy a new mining drone");
        
        createResourcesDropdown();
    }
    
    protected final void dispoeVista() {
        option.setMinWidth(400);
        option.setMaxWidth(400);
        
        submit.setMinWidth(400);
        submit.setMaxWidth(400);
        
        vBox.setAlignment(Pos.CENTER);
        vBox.getChildren().add(option);
        vBox.getChildren().add(submit);
        
        comboBoxes.setAlignment(Pos.CENTER);
        comboBoxes.setSpacing(18);
        comboBoxes.getChildren().add(resourceOrigin);
        comboBoxes.getChildren().add(resourceFinal);
        comboBoxes.setVisible(false);
        
        this.setAlignment(Pos.CENTER);
        this.setSpacing(10);
        this.getChildren().add(vBox);
    }
    
    protected final void registaListeners() {
        submit.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                switch(option.getSelectionModel().getSelectedIndex()+1) {
                    case 1:
                        game.moveToNextSpaceLocation(); break;
                    case 2:
                        game.upgradeCargo(); break;
                    case 3:
                        game.convertSingleResource(resourceOrigin.getSelectionModel().getSelectedIndex()+1, 
                                resourceFinal.getSelectionModel().getSelectedIndex()+1);
                        break;
                    case 4:
                        game.hireALostCrewMember(); break;
                    case 5:
                        game.repairDrone(); break;
                    case 6:
                        game.buyDrone(); break;
                }
            }
        });
        
        option.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                if(option.getSelectionModel().getSelectedIndex()+1 != 3) {
                    submit.setDisable(false);
                    getChildren().remove(comboBoxes);
                }
                else {
                    getChildren().remove(submit);
                    getChildren().add(comboBoxes);
                    getChildren().add(submit);
                    submit.setDisable(false);
                    comboBoxes.setVisible(true);
                }
            }
        });
        
        resourceOrigin.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                resourceFinal.setDisable(false);
            }
        });
    }
    
    protected final void createImages() {
        emerald = new Image(getClass().getResourceAsStream("resources/emerald.png"));
        saphire = new Image(getClass().getResourceAsStream("resources/saphire.png"));
        ruby = new Image(getClass().getResourceAsStream("resources/ruby.png"));
        onyx = new Image(getClass().getResourceAsStream("resources/onyx.png"));
        
        greenFrom = new ImageView(emerald);
        blueFrom = new ImageView(saphire);
        redFrom = new ImageView(ruby);
        blackFrom = new ImageView(onyx);
        
        greenTo = new ImageView(emerald);
        blueTo = new ImageView(saphire);
        redTo = new ImageView(ruby);
        blackTo = new ImageView(onyx);
        
        greenFrom.setFitHeight(20);
        greenFrom.setFitWidth(20);
        blueFrom.setFitHeight(20);
        blueFrom.setFitWidth(20);
        redFrom.setFitHeight(20);
        redFrom.setFitWidth(20);
        blackFrom.setFitHeight(20);
        blackFrom.setFitWidth(20);
        
        greenTo.setFitHeight(20);
        greenTo.setFitWidth(20);
        blueTo.setFitHeight(20);
        blueTo.setFitWidth(20);
        redTo.setFitHeight(20);
        redTo.setFitWidth(20);
        blackTo.setFitHeight(20);
        blackTo.setFitWidth(20);
    }
    
    public void createResourcesDropdown() {
        resourcesFromLabel = new Label[4];
        resourcesToLabel = new Label[4];
        
        resourcesFromLabel[0] = new Label(" Red resource");
        resourcesFromLabel[1] = new Label(" Blue resource");
        resourcesFromLabel[2] = new Label(" Green resource");
        resourcesFromLabel[3] = new Label(" Black resource");
        
        resourcesToLabel[0] = new Label(" Red resource");
        resourcesToLabel[1] = new Label(" Blue resource");
        resourcesToLabel[2] = new Label(" Green resource");
        resourcesToLabel[3] = new Label(" Black resource");

        from = new Label("From");
        resourceOrigin = new ComboBox<>();
        resourceOrigin.setVisibleRowCount(4);
        resourceOrigin.getSelectionModel().select(new HBox(from));
        resourceOrigin.getItems().addAll(
                new HBox(redFrom, resourcesFromLabel[0]),
                new HBox(blueFrom, resourcesFromLabel[1]),
                new HBox(greenFrom, resourcesFromLabel[2]),
                new HBox(blackFrom, resourcesFromLabel[3]));
        
        to = new Label("To");
        resourceFinal = new ComboBox<>();
        resourceFinal.setVisibleRowCount(4);
        resourceFinal.getSelectionModel().select(new HBox(to));
        resourceFinal.getItems().addAll(
                new HBox(redTo, resourcesToLabel[0]),
                new HBox(blueTo, resourcesToLabel[1]),
                new HBox(greenTo, resourcesToLabel[2]),
                new HBox(blackTo, resourcesToLabel[3]));
        resourceFinal.setDisable(true);
    }
    
    @Override
    public void propertyChange(PropertyChangeEvent evt) {
        if(game.getGame().getState() instanceof AwaitToDockToSpaceStation) {
            if(!father.getChildren().contains(this)) {
                father.getChildren().add(this);
                option.getSelectionModel().select("Select an option and submit");
                resourceOrigin.getSelectionModel().select(new HBox(from));
                resourceFinal.getSelectionModel().select(new HBox(to));
            }
        } else {
            father.getChildren().remove(this);
        }
    }
}
