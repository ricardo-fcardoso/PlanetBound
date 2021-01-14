package planetbound.logic.states;

import planetbound.logic.data.Constants;
import planetbound.logic.data.GameData;

public class AwaitDroneMovement extends StateAdapter implements Constants {
    
    public AwaitDroneMovement(GameData data)
    {
        super(data);
    }
    
    @Override
    public IStates moveDrone(int keyPressed) 
    {
        switch(getInfo().moveDrone(keyPressed)) {
            case SUCCESS:
                return this;
            case RETURNED_TO_SHIP_WHITE_ORBIT:
                return new AwaitChoiceOnWhiteOrbit(getInfo());
            case RETURNED_TO_SHIP_RED_ORBIT:
                return new AwaitChoiceOnRedOrbit(getInfo());
            case DRONE_DESTROYED:
                return new AwaitToMove(getInfo());
            case GAME_WIN:
                return new AwaitGameOver(getInfo());
            default:
                return this;
        }
    }
}
