package planetbound.logic.states;

import planetbound.logic.data.Constants;
import planetbound.logic.data.GameData;

public class AwaitChoiceOnRedOrbit extends StateAdapter implements Constants {
    
    public AwaitChoiceOnRedOrbit(GameData data) 
    {
        super(data);
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
    
    @Override
    public IStates dockToSpaceStation() 
    {
        return new AwaitToDockToSpaceStation(getInfo());
    }
    
    @Override
    public IStates convertResourcesIntoSupplies(int supply)
    {
        getInfo().convertResourcesIntoSupplies(supply);
        return this;
    }
}