package planetbound.logic.data;

import java.io.Serializable;

public class MilitaryShip extends Ship implements Constants, Serializable {
        
    public MilitaryShip()
    {
        super();    // calls the Ship constructor
        setWeapons(MILITARY_SHIP_WEAPONS_MAX);     
        setShields(MILITARY_SHIP_SHIELDS_MAX);      
        setFuel(MILITARY_SHIP_FUEL_MAX);
    }
    
    @Override
    public String toString() {
        String os = "";
        
        os += "\nMilitary Ship created successfully with the following specs: " +
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
                    if(resource.getResourceColor().equals(ARTEFACT))
                        os += "\n\t> " + resource.getResourceColor() + ": " + resource.getResourceAmount() + "/5"; 
                    else
                        os += "\n\t> " + resource.getResourceColor() + ": " + resource.getResourceAmount() + "/6"; 
                    break;
                case 1:
                    if(resource.getResourceColor().equals(ARTEFACT))
                        os += "\n\t> " + resource.getResourceColor() + ": " + resource.getResourceAmount() + "/5"; 
                    else
                        os += "\n\t> " + resource.getResourceColor() + ": " + resource.getResourceAmount() + "/12"; 
                    break;
                case 2:
                    if(resource.getResourceColor().equals(ARTEFACT))
                        os += "\n\t> " + resource.getResourceColor() + ": " + resource.getResourceAmount() + "/5"; 
                    else
                        os += "\n\t> " + resource.getResourceColor() + ": " + resource.getResourceAmount() + "/18"; 
                    break;
                case 3:
                    if(resource.getResourceColor().equals(ARTEFACT))
                        os += "\n\t> " + resource.getResourceColor() + ": " + resource.getResourceAmount() + "/5"; 
                    else
                        os += "\n\t> " + resource.getResourceColor() + ": " + resource.getResourceAmount() + "/24"; 
                    break;
            }
        }
        
        return os;
    }
    
    public int getMaxFuelCapacity() {
        return MILITARY_SHIP_FUEL_MAX;
    }
    
    public int getMaxShieldsCapacity() {
        return MILITARY_SHIP_SHIELDS_MAX;
    }
    
    public int getMaxWeaponsCapacity() {
        return MILITARY_SHIP_WEAPONS_MAX;
    }
    
    public Ship getShip() {
        return this;
    }
    
}