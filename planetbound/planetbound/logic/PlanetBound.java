package planetbound.logic;

import java.io.Serializable;
import planetbound.logic.data.Alien;
import planetbound.logic.data.Constants;
import planetbound.logic.states.AwaitBeginning;
import planetbound.logic.data.GameData;
import planetbound.logic.data.Planet;
import planetbound.logic.data.Resource;
import planetbound.logic.data.Ship;
import planetbound.logic.data.Terrain;

import planetbound.logic.states.IStates;

public class PlanetBound implements Constants, Serializable {
    
    private GameData data;
    private IStates state;
    
    public PlanetBound() {
        this.data = new GameData();
        this.state = new AwaitBeginning(data);
    }
    
    public GameData getData() {
        return data;
    }
    
    public void setState(IStates state) {
        this.state = state;
    }
    
    public IStates getState() {
        return state;
    }
    
    public void selectShipType(int type) 
    {
        setState(getState().selectShip(type));
    }
    
    public void moveToNextSpaceLocation()
    {
        setState(getState().moveToNextSpaceLocation());
    }
    
    public void landToMineResources()
    {
        setState(getState().landToMineResources());
    }
    
    public void convertResourcesIntoSupplies(int conversion)
    {
        setState(getState().convertResourcesIntoSupplies(conversion));
    }
    
    public void upgradeCargo()
    {
        setState(getState().upgradeCargo());
    }
    
    public void convertSingleResource(int resourceOrigin, int resourceFinal)
    {
        setState(getState().convertSingleResource(resourceOrigin, resourceFinal));
    }
    
    public void hireALostCrewMember()
    {
        setState(getState().hireALostCrewMember());
    }
    
    public void repairDrone()
    {
        setState(getState().repairDrone());
    }
    
    public void buyDrone()
    {
        setState(getState().buyDrone());
    }
    
    public void moveDrone(int keyPressed)
    {
        setState(getState().moveDrone(keyPressed));
    }
    
    public void dockToSpaceStation()
    {
        setState(getState().dockToSpaceStation());
    }
    
    public void doEventFromReferenceCard() 
    {
        setState(getState().doEventFromReferenceCard());
    }
    
    public void doSpecificEventFromReferenceCard(int event)
    {
        setState(getState().doSpecificEventFromReferenceCard(event));
    }
    
    public void startGame()
    {
        setState(getState().start());
    }
    
    public void replay()
    {
        setState(getState().replay());
    }
    
    public Ship getShip()
    {
        return data.getShip();
    }
    
    public Planet getPlanet()
    {
        return data.getPlanet();
    }
    
    public Alien getAlien()
    {
        return data.getAlien();
    }
    
    public Terrain getTerrain()
    {
        return data.getTerrain();
    }
    
    public Resource getResource()
    {
        return data.getResource();
    }
    
    public String getLog()
    {
        return data.getLog();
    }
    
}