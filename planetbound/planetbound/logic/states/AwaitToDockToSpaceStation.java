package planetbound.logic.states;

import planetbound.logic.data.GameData;

public class AwaitToDockToSpaceStation extends StateAdapter{
    
    public AwaitToDockToSpaceStation(GameData data) {
        super(data);
    }
    
    @Override
    public IStates moveToNextSpaceLocation()
    {
        return new AwaitChoiceOnRedOrbit(getInfo());
    }
    
    @Override
    public IStates upgradeCargo()
    {
        getInfo().upgradeCargo();
        return new AwaitToDockToSpaceStation(getInfo());
    }
    
    @Override
    public IStates convertSingleResource(int resourceOrigin, int resourceFinal)
    {
        getInfo().convertSingleResource(resourceOrigin, resourceFinal);
        return new AwaitToDockToSpaceStation(getInfo());
    }
    
    @Override
    public IStates hireALostCrewMember()
    {
        getInfo().hireALostCrewMember();
        return new AwaitToDockToSpaceStation(getInfo());
    }
    
    @Override
    public IStates repairDrone()
    {
        getInfo().repairDrone();
        return new AwaitToDockToSpaceStation(getInfo());
    }
    
    @Override
    public IStates buyDrone()
    {
        getInfo().buyDrone();
        return new AwaitToDockToSpaceStation(getInfo());
    }
    
}
