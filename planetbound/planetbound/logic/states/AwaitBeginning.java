package planetbound.logic.states;

import planetbound.logic.data.GameData;

public class AwaitBeginning extends StateAdapter {
    
    public AwaitBeginning(GameData data) 
    {
        super(data);
    }
    
    @Override
    public IStates start() 
    {
        return new AwaitShipSelection(getInfo());
    }
    
}