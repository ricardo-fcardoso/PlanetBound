package planetbound.logic.states;

import planetbound.logic.data.Constants;
import planetbound.logic.data.GameData;

public class AwaitReturnToShip extends StateAdapter implements Constants {
    
    public AwaitReturnToShip(GameData data)
    {
        super(data);
    }
    
    @Override 
    public IStates convertResourcesIntoSupplies(int conversion)
    {
        getInfo().convertResourcesIntoSupplies(conversion);
        
        return this;
    }
    
    @Override
    public IStates moveToNextSpaceLocation()
    {
        return new AwaitToMove(getInfo());
    }
    
    @Override
    public IStates landToMineResources()
    {
        if(getInfo().landToMineResources() == SUCCESS)
            return new AwaitDroneMovement(getInfo());
        else
            return new AwaitToMove(getInfo());
    }
}
