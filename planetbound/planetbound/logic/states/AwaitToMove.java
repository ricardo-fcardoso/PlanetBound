package planetbound.logic.states;

import planetbound.logic.data.Constants;
import planetbound.logic.data.GameData;

public class AwaitToMove extends StateAdapter implements Constants {
    
    public AwaitToMove(GameData data) 
    {
        super(data);
    }
    
    @Override
    public IStates moveToNextSpaceLocation()
    {        
        switch(getInfo().moveToNextSpaceLocation()) 
        {
            case MOVE_TO_NEXT_SPACE_LOCATION:
                if(getInfo().moveToNextSpaceLocation() == ERROR) 
                    return new AwaitGameOver(getInfo());
                break;
                
            case RED_ORBIT_PLANET_USER_CHOICE:
                return new AwaitChoiceOnRedOrbit(getInfo());
                
            case WHITE_ORBIT_PLANET_USER_CHOICE:
                return new AwaitChoiceOnWhiteOrbit(getInfo());
                
            case DO_EVENT_FROM_REFERENCE_CARD:  
                return new AwaitEvent(getInfo());
                
            case MOVE_THROUGH_WORMHOLE:
                switch(getInfo().moveToPlanet()) {
                    case RED_ORBIT_PLANET_USER_CHOICE:
                        return new AwaitChoiceOnRedOrbit(getInfo());
                    case WHITE_ORBIT_PLANET_USER_CHOICE:
                        return new AwaitChoiceOnWhiteOrbit(getInfo());
                }
                break;
                
            case GAME_OVER:
            case GAME_WIN:
                return new AwaitGameOver(getInfo());
        }
        
        return new AwaitEvent(getInfo());
    }
}
