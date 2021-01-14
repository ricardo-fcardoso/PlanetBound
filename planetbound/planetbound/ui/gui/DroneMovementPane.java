package planetbound.ui.gui;

import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import javafx.event.EventHandler;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;
import planetbound.logic.data.ObservableGame;
import planetbound.logic.data.Resource;
import planetbound.logic.states.AwaitDroneMovement;
import static planetbound.ui.gui.Constants.*;

public class DroneMovementPane extends VBox implements PropertyChangeListener, planetbound.logic.data.Constants {
    
    ObservableGame game;
    VBox father;
    Scene scene;
    GridPane grid;
    Image droneImg, spaceship;
    ImageView[] terrainView;
    ImageView droneView, resourceView, alienView, spaceshipView;
    private boolean flag = false;
    
    public DroneMovementPane(ObservableGame game, VBox father, Scene scene) {
        this.game = game;
        this.father = father;
        this.scene = scene;
        game.addPropertyChangeListener(this);
        
        criaComponentes();
        dispoeVista();
        registaListeners();
    }
    
    protected final void criaComponentes() {
        grid = new GridPane();

        terrainView = new ImageView[36];
        
        droneImg = Images.getImagem(DRONE);
        droneView = new ImageView(droneImg);
        
        spaceship = Images.getImagem(SPACESHIP);
        spaceshipView = new ImageView(spaceship);
        
        for(int i = 0, j = 1; i < 36; i++, j++) {
            terrainView[i] = new ImageView(new Image(getClass().getResourceAsStream("resources/terrain/" + j + ".jpg")));
        }
    }
    
    protected final void dispoeVista() {
        droneView.setFitWidth(100);
        droneView.setFitHeight(100);

        spaceshipView.setFitHeight(100);
        spaceshipView.setFitWidth(100);
        
        grid.setAlignment(Pos.CENTER);
        grid.setVgap(2);
        grid.setHgap(2);
        
        this.getChildren().add(grid);
        this.setAlignment(Pos.CENTER);
    }
    
    protected final void registaListeners() {
        scene.setOnKeyPressed(new EventHandler<KeyEvent>() {
            @Override
            public void handle(KeyEvent event) {
                if (null != event.getCode() && flag) {
                    switch (event.getCode()) {
                        case W:
                            game.moveDrone(119);
                            grid.getChildren().remove(droneView);
                            grid.add(droneView, game.getGame().getData().getShip().getDrone().getCurrentColumn(), game.getGame().getData().getShip().getDrone().getCurrentLine());
                            break;
                        case A:
                            game.moveDrone(97);
                            grid.getChildren().remove(droneView);
                            grid.add(droneView, game.getGame().getData().getShip().getDrone().getCurrentColumn(), game.getGame().getData().getShip().getDrone().getCurrentLine());
                            break;
                        case S:
                            game.moveDrone(115);
                            grid.getChildren().remove(droneView);
                            grid.add(droneView, game.getGame().getData().getShip().getDrone().getCurrentColumn(), game.getGame().getData().getShip().getDrone().getCurrentLine());
                            break;
                        case D:
                            game.moveDrone(100);
                            grid.getChildren().remove(droneView);
                            grid.add(droneView, game.getGame().getData().getShip().getDrone().getCurrentColumn(), game.getGame().getData().getShip().getDrone().getCurrentLine());
                            break;
                    }
                    
                    grid.getChildren().remove(spaceshipView);
                    grid.add(spaceshipView, game.getGame().getData().getShip().getDrone().getInitialColumn(), game.getGame().getData().getShip().getDrone().getInitialLine());
                   
                    if(game.getGame().getTerrain().getAlien().getAlienAlive()) {
                        grid.getChildren().remove(alienView);
                        grid.add(alienView, game.getGame().getTerrain().getAlien().getCurrentColumn(), game.getGame().getTerrain().getAlien().getCurrentLine());
                    }
                    else {
                        grid.getChildren().remove(alienView);
                    }
                    
                    if(game.getGame().getShip().getDrone().getCurrentColumn() == game.getGame().getTerrain().getResource().getCurrentColumnOnTerrain() &&
                            game.getGame().getShip().getDrone().getCurrentLine() == game.getGame().getTerrain().getResource().getCurrentLineOnTerrain())
                        grid.getChildren().remove(resourceView);
                }
            }
        });
    }
    
    @Override
    public void propertyChange(PropertyChangeEvent evt) {
        if(game.getGame().getState() instanceof AwaitDroneMovement) {
            if(!father.getChildren().contains(this)) {
                father.getChildren().add(this);
                grid.getChildren().clear();
                flag = true;

                for(int x = 0, i = 0; x < 6; x++) {
                    for(int y = 0; y < 6; y++) {
                        grid.add(terrainView[i++], x, y);
                    }
                }
                
                // Drone
                grid.add(droneView, game.getGame().getShip().getDrone().getInitialColumn(), game.getGame().getData().getShip().getDrone().getInitialLine());
                
                // Resource
                for(Resource resource : game.getGame().getPlanet().getPlanetResources()) {
                    if(resource.getNextToBeMined()) {
                        switch(resource.getResourceColor()) {
                            case RED_RESOURCE:
                                resourceView = new ImageView(Images.getImagem(RED_RESOURCE)); break;
                            case BLUE_RESOURCE:
                                resourceView = new ImageView(Images.getImagem(BLUE_RESOURCE)); break;
                            case GREEN_RESOURCE:
                                resourceView = new ImageView(Images.getImagem(GREEN_RESOURCE)); break;
                            case BLACK_RESOURCE:
                                resourceView = new ImageView(Images.getImagem(BLACK_RESOURCE)); break;
                        }
                    }
                }

                resourceView.setFitHeight(100);
                resourceView.setFitWidth(100);
                grid.add(resourceView, game.getGame().getTerrain().getResource().getCurrentColumnOnTerrain(), game.getGame().getResource().getCurrentLineOnTerrain());
                
                // Alien
                switch(game.getGame().getAlien().getAlienColor()) {
                    case RED_ALIEN:
                        alienView = new ImageView(Images.getImagem(RED_ALIEN)); break;
                    case BLUE_ALIEN:
                        alienView = new ImageView(Images.getImagem(BLUE_ALIEN)); break;
                    case GREEN_ALIEN:
                        alienView = new ImageView(Images.getImagem(GREEN_ALIEN)); break;
                    case BLACK_ALIEN:
                        alienView = new ImageView(Images.getImagem(BLACK_ALIEN)); break;
                }

                alienView.setFitHeight(100);
                alienView.setFitWidth(100);
                grid.add(alienView, game.getGame().getTerrain().getAlien().getCurrentColumn(), game.getGame().getTerrain().getAlien().getCurrentLine());
            }
        } else {
            father.getChildren().remove(this);
        }
    }
}
