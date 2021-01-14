package planetbound.logic.states;

import planetbound.logic.data.Constants;
import planetbound.logic.data.GameData;

public class AwaitEvent extends StateAdapter implements Constants {

    public AwaitEvent(GameData data) 
    {
        super(data);
    }
    
    @Override
    public IStates doEventFromReferenceCard()
    {
        switch(getInfo().doEventFromReferenceCard()) {
            case SUCCESS:
                return new AwaitToMove(getInfo());
            case ERROR:
                return new AwaitGameOver(getInfo());
        }   
        
        return this;
    }
    
    @Override 
    public IStates doSpecificEventFromReferenceCard(int event)
    {
        switch(getInfo().doSpecificEventFromReferenceCard(event)) {
            case SUCCESS:
                return new AwaitToMove(getInfo());
            case ERROR:
                return new AwaitGameOver(getInfo());
        }
        
        return this;
    }
    
}
