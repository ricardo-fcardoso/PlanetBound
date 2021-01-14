package planetbound.logic.data;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class Ship implements Constants, Serializable {
    
    private int weapons;
    private int shields;
    private int fuel;
    private int nDrones;
    private Drone drone;
    private final Cargo cargo;
    private final List<String> crewMembers;    // ArrayList que armazena 6 officers
    private int spaceLocation;      // 1 - se está em red dot
                                    // 2 - se está em planeta
                                    // 3 - se está em planeta c/ estação
    
    public Ship()
    {
        this.cargo = new Cargo();
        this.crewMembers = new ArrayList<>();
        this.spaceLocation = 0;
        this.nDrones = 1;
        this.drone = new Drone();
        
        this.crewMembers.add(CAPTAIN);
        this.crewMembers.add(NAVIGATION_OFFICER);
        this.crewMembers.add(LANDING_PARTY_OFFICER);
        this.crewMembers.add(SHIELDS_OFFICER);
        this.crewMembers.add(WEAPONS_OFFICER);
        this.crewMembers.add(CARGO_HOLD_OFFICER);
        
        this.cargo.setLevel(0);
    }
    
    public final int getWeapons() {
        return weapons;
    }
    
    public final int getShields() {
        return shields;
    }
    
    public final int getFuel() {
        return fuel;
    }
    
    public final int getNDrones() {
        return nDrones;
    }
    
    public final Drone getDrone() {
        return drone;
    }
    
    public final Cargo getCargo() {
        return cargo;
    }
    
    public final int getSpaceLocation() {
        return spaceLocation;
    }
    
    public final List<String> getCrew() {
        return crewMembers;
    }
    
    public final void setWeapons(int weapons) {
        this.weapons = weapons;
    }
    
    public final void setShields(int shields) {
        this.shields = shields;
    }
    
    public final void setFuel(int fuel) {
        this.fuel = fuel;
    }
    
    public final void setResourceUnits(String resourceName, int units) {
        this.cargo.setResourceUnits(resourceName, units);
    }
    
    public final void setShipLocation(int spaceLocation) {
        this.spaceLocation = spaceLocation;
    }
    
    public final void setNDrones(int nDrones) {
        this.nDrones = nDrones;
    }
    
    public void createNewDrone() {
        drone = new Drone();
    }
   
}
