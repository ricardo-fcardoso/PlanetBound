package planetbound.logic.states;

import planetbound.logic.data.GameData;

public class AwaitGameOver extends StateAdapter {
    
    public AwaitGameOver(GameData data)
    {
        super(data);
    }
    
    @Override
    public IStates replay()
    {
        getInfo().resetLog();
        return new AwaitBeginning(getInfo());
    }
    
}
