package planetbound.logic.states;

import java.io.Serializable;

public interface IStates extends Serializable
{
    IStates start();
    IStates selectShip(int type);
    IStates moveToNextSpaceLocation();
    IStates landToMineResources();
    IStates convertResourcesIntoSupplies(int conversion);
    IStates moveDrone(int keyPressed);
    IStates upgradeCargo();
    IStates convertSingleResource(int resourceOrigin, int resourceFinal);
    IStates hireALostCrewMember();
    IStates repairDrone();
    IStates buyDrone();
    IStates dockToSpaceStation();
    IStates doEventFromReferenceCard();
    IStates doSpecificEventFromReferenceCard(int event);
    IStates replay();
}
