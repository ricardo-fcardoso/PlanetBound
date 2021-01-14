package planetbound.logic.states;

import planetbound.logic.data.GameData;

public class StateAdapter implements IStates {
    
    GameData info;
    
    public StateAdapter(GameData data) {
        info = data;
    }
    
    public GameData getInfo() {
        return info;
    }
    
    public void setData(GameData data) {
        this.info = data;
    }
    
    @Override
    public IStates start() {
        return this;
    }
    
    @Override
    public IStates selectShip(int type)
    {
        return this;
    }
    
    @Override
    public IStates moveToNextSpaceLocation()
    {
        return this;
    }
    
    @Override 
    public IStates doEventFromReferenceCard()
    {
        return this;
    }
    
    @Override
    public IStates doSpecificEventFromReferenceCard(int event)
    {
        return this;
    }
    
    @Override
    public IStates landToMineResources()
    {
        return this;
    }
    
    @Override
    public IStates moveDrone(int keyPressed)
    {
        return this;
    }
    
    @Override
    public IStates dockToSpaceStation()
    {
        return this;
    }
    
    @Override
    public IStates convertResourcesIntoSupplies(int conversion)
    {
        return this;
    }
    
    @Override
    public IStates upgradeCargo()
    {
        return this;
    }
    
    @Override
    public IStates convertSingleResource(int resourceOrigin, int resourceFinal)
    {
        return this;
    }
    
    @Override
    public IStates hireALostCrewMember()
    {
        return this;
    }
    
    @Override
    public IStates repairDrone()
    {
        return this;
    }
    
    @Override
    public IStates buyDrone()
    {
        return this;
    }
    
    @Override
    public IStates replay()
    {
        return this;
    }

}
