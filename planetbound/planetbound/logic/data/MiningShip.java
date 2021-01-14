package planetbound.logic.data;

import java.io.Serializable;

public class MiningShip extends Ship implements Constants, Serializable {
    
    public MiningShip()
    {
        super();    // calls the Ship constructor
        setWeapons(MINING_SHIP_WEAPONS_MAX);     
        setShields(MINING_SHIP_SHIELDS_MAX);      
        setFuel(MINING_SHIP_FUEL_MAX);
    }
    
    @Override
    public String toString() {
        String os = "";
        
        os += "\nMining Ship created successfully with the following specs: " +
                "\n > Weapons: " + getWeapons() +
                "\n > Shields: " + getShields() +
                "\n > Fuel: " + getFuel() +
                "\n > Drones: " + getNDrones() + 
                "\n > Space Location: " + getSpaceLocation() +
                "\n > Crew members: ";
        
        for(int i = 0, j = 1; i < getCrew().size(); i++, j++)
            os += "\n\t" + j + ". " + getCrew().get(i);
        
        os += "\n > Cargo Level: " + getCargo().getLevel() +
                "\n > Cargo Resources: ";

        for(Resource resource : getCargo().getResources()) {
            switch(getCargo().getLevel()) {
                case 0:
                    if(resource.equals(ARTEFACT))
                        os += "\n\t> " + resource.getResourceColor() + ": " + resource.getResourceAmount() + "/5"; 
                    else
                        os += "\n\t> " + resource.getResourceColor() + ": " + resource.getResourceAmount() + "/6"; 
                    break;
                case 1:
                    if(resource.equals(ARTEFACT))
                        os += "\n\t> " + resource.getResourceColor() + ": " + resource.getResourceAmount() + "/5"; 
                    else
                        os += "\n\t> " + resource.getResourceColor() + ": " + resource.getResourceAmount() + "/12"; 
                    break;
                case 2:
                    if(resource.equals(ARTEFACT))
                        os += "\n\t> " + resource.getResourceColor() + ": " + resource.getResourceAmount() + "/5"; 
                    else
                        os += "\n\t> " + resource.getResourceColor() + ": " + resource.getResourceAmount() + "/18"; 
                    break;
                case 3:
                    if(resource.equals(ARTEFACT))
                        os += "\n\t> " + resource.getResourceColor() + ": " + resource.getResourceAmount() + "/5"; 
                    else
                        os += "\n\t> " + resource.getResourceColor() + ": " + resource.getResourceAmount() + "/24"; 
                    break;
            }
        }
        
        return os;
    }
    
    public int getMaxFuelCapacity() {
        return MINING_SHIP_FUEL_MAX;
    }
    
    public int getMaxShieldsCapacity() {
        return MINING_SHIP_SHIELDS_MAX;
    }
    
    public int getMaxWeaponsCapacity() {
        return MINING_SHIP_WEAPONS_MAX;
    }
    
    public Ship getShip() {
        return this;
    }
}
