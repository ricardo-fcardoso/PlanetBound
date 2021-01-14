package planetbound.logic.states;

import planetbound.logic.data.GameData;

public class AwaitShipSelection extends StateAdapter {
    
    public AwaitShipSelection(GameData data) {
        super(data);
    }
    
    @Override
    public IStates selectShip(int type)
    {
        getInfo().selectShipType(type);
        return new AwaitToMove(getInfo());
    }
    
}
