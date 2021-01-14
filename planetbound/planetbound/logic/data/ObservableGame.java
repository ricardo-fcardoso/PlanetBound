package planetbound.logic.data;

import java.beans.PropertyChangeSupport;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import planetbound.logic.PlanetBound;

public class ObservableGame extends PropertyChangeSupport {
    
    PlanetBound game;
    
    public ObservableGame(PlanetBound planetbound) {
        super(planetbound);
        this.game = new PlanetBound();
    }
    
    public PlanetBound getGame() {
        return game;
    }
    
    public void setGame(PlanetBound game) {
        this.game = game;
    }
    
    public void start() {
        game.startGame();
        firePropertyChange(null, null, null);
    }
    
    public void replay() {
        game.replay();
        firePropertyChange(null, null, null);
    }
    
    public void selectShipType(int type) {
        game.selectShipType(type);
        firePropertyChange(null, null, null);
    }
    
    public void moveToNextSpaceLocation() {
        game.moveToNextSpaceLocation();
        firePropertyChange(null, null, null);
    }
    
    public void landToMineResources() {
        game.landToMineResources();
        firePropertyChange(null, null, null);
    }
    
    public void dockToSpaceStation() {
        game.dockToSpaceStation();
        firePropertyChange(null, null, null);
    }
    
    public void doEventFromReferenceCard() {
        game.doEventFromReferenceCard();
        firePropertyChange(null, null, null);
    }
    
    public void doSpecificEventFromReferenceCard(int event) {
        game.doSpecificEventFromReferenceCard(event);
        firePropertyChange(null, null, null);
    }
    
    public void upgradeCargo() {
        game.upgradeCargo();
        firePropertyChange(null, null, null);
    }
    
    public void convertSingleResource(int resourceOrigin, int resourceFinal) {
        game.convertSingleResource(resourceOrigin, resourceFinal);
        firePropertyChange(null, null, null);
    }
    
    public void convertResourcesIntoSupplies(int supply) {
        game.convertResourcesIntoSupplies(supply);
        firePropertyChange(null, null, null);
    }
    
    public void hireALostCrewMember() {
        game.hireALostCrewMember();
        firePropertyChange(null, null, null);
    }
    
    public void repairDrone() {
        game.repairDrone();
        firePropertyChange(null, null, null);
    }
    
    public void buyDrone() {
        game.buyDrone();
        firePropertyChange(null, null, null);
    }
    
    public void moveDrone(int keyPressed) {
        game.moveDrone(keyPressed);
        firePropertyChange(null, null, null);
    }
    
    public void retrieveGameFromFile(File selectedFile) throws IOException, ClassNotFoundException {
        ObjectInputStream obj = null;
        
        try {
            obj = new ObjectInputStream(new FileInputStream(selectedFile));
            game = (PlanetBound) obj.readObject();
        } finally {
            if(obj != null) {
                obj.close();
            }
            firePropertyChange(null, null, null);
        }
    }
    
    public void saveGameToFile(String selectedFile) throws IOException, ClassNotFoundException {
        ObjectOutputStream obj = null;
        
        try {
            obj = new ObjectOutputStream(new FileOutputStream(selectedFile));
            obj.writeObject(game);
        } finally {
            if(obj != null) {
                obj.close();
            }
            firePropertyChange(null, null, null);
        }
    }
}
