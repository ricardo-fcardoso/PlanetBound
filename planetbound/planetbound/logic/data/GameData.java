package planetbound.logic.data;

import java.io.Serializable;
import java.util.Arrays;
import java.util.Random;

public class GameData implements Constants, Serializable {
    
    Ship ship;
    Planet planet;
    Terrain terrain;
    StringBuilder log;
    
    public GameData()
    {
        planet = new Planet();
        terrain = new Terrain();
        log = new StringBuilder("");
    }

    public void resetLog() {
        log.setLength(0);
    }
    
    /**
     * Creates a new ship depending on the ship type
     */
    public void selectShipType(int type)
    {
        if(type==1) {
            ship = new MilitaryShip();
            log.append("MILITARY ship selected\n");
        }
        if(type==2) {
            ship = new MiningShip();
            log.append("MINING ship selected\n");
        }
    }
    
    /*
     *
     * SHIP_LOCATION_IS_NULL -> when the game started and the ship is not on a planet neither a red dot
     * SHIP_LOCATION_IS_RED_DOT -> when the last space location of the ship was a red dot
     * SHIP_LOCATION_IS_WHITE_PLANET -> when the last space location of the ship was a planet with white circle
     * SHIP_LOCATION_IS_RED_PLANET -> when the last space location of the ship was a planet with red circle
     *  
     */
    public int moveToNextSpaceLocation()
    {
        for(Resource resource : ship.getCargo().getResources()) {
            if(resource.getResourceColor().equals(ARTEFACT)) {
                if(resource.getResourceAmount() == 5) {
                    log.append("GAME WIN, you found all the 5 artifacts\n");
                    return GAME_WIN;
                }
            }
        }
        
        switch (ship.getSpaceLocation()) {
            case SHIP_LOCATION_IS_NULL:
            case SHIP_LOCATION_IS_RED_DOT:
                return moveToPlanet();
                
            case SHIP_LOCATION_IS_WHITE_PLANET:
            case SHIP_LOCATION_IS_RED_PLANET:
                int result = (int)(Math.random() * (PLANET_RAND_MAX-PLANET_RAND_MIN) + 1);
                planet = new Planet();
                
                /* PROBABILITY OF PLANET W/ STATION (3/10) */
                planet.setHasStation(result > 0 && result < 30);
                log.append("Dice d100 rolled -> ").append(result).append("\n");
                
                /* PROBABILITY TO TRAVEL THROUGH WORMHOLE (1/8) */
                Random rand = new Random();
                int randomInt = rand.nextInt(8) + 1;
                log.append("Dice d8 rolled -> ").append(randomInt).append("\n");
                if(randomInt == 1) {
                    return moveThroughWormhole();
                }
                else {
                    return moveToRedDot();
                }
                
            default:
                return GAME_OVER;
        }
        
    }
    
    /**
     * Moves the ship through a wormhole, losing 3 fuel cells and 2 shield cells
     */
    public int moveThroughWormhole()
    {
        if(ship.getFuel() > 3 && ship.getShields() >= 2 && ship.getCrew().contains(SHIELDS_OFFICER)) {      // if meets all requirements
            ship.setFuel(ship.getFuel() - 3);
            ship.setShields(ship.getShields() - 2);
            
            log.append("You travelled through a WORMHOLE\n");
            log.append("Your ship consumed 3 fuel and 2 shield\n");
            
            if(ship.getFuel() > 0)
                log.append("Fuel available: ").append(ship.getFuel()).append("\n");
            else {
                log.append("GAME OVER, your ship runned out of fuel\n");
                
                return GAME_OVER;
            }
            
            if(ship.getShields() > 0)
                log.append("Shields available: ").append(ship.getShields()).append("\n");
            else
                log.append("Your ship lost the shields\n");
            
            return MOVE_THROUGH_WORMHOLE;
        }
        else if(ship.getFuel() > 3 && ship.getShields() > 4 && !ship.getCrew().contains(SHIELDS_OFFICER)) {  // if has fuel but not shields officer
            
            ship.setFuel(ship.getFuel() - 4);
            ship.setShields(ship.getShields() - 4);
            
            log.append("You don't have a Shields Officer\n");
            log.append("So you consumed 4 fuel and 4 shields\n");

            if(ship.getFuel() > 0)
                log.append("Fuel available: ").append(ship.getFuel()).append("\n");
            else {
                log.append("GAME OVER, your ship runned out of fuel\n");
                return GAME_OVER;
            }
            
            if(ship.getShields() > 0)
                log.append("Shields available: ").append(ship.getShields()).append("\n");
            else
                log.append("Your ship lost all the shields\n");
            
            return MOVE_THROUGH_WORMHOLE;
        }
        else if(ship.getFuel() > 3 && ship.getShields() < 2) {     // if has fuel but not shields
            log.append("You don't have enough shields to travel through the wormhole\n");
            log.append("So you lost a crew member\n");
            eventCrewDeath();
            
            return MOVE_THROUGH_WORMHOLE;
        }
        else {    // if has shields but not fuel
            log.append("You don't have enough fuel to travel through a wormhole\n");
            log.append("Fuel available: ").append(ship.getFuel()).append("\n");
            
            return MOVE_TO_NEXT_SPACE_LOCATION;
        }
    }
    
    /**
     * Moves the ship to the next planet (white orbit planet or red orbit planet) 
     */
    public int moveToPlanet()
    {        
        /* PLANET SECTOR WITH RED CIRCLE */
        if(planet.getHasStation())
        {
            ship.setShipLocation(3);
            
            if(ship.getFuel() > 0)
                ship.setFuel(ship.getFuel() - 1);
            else {
                log.append("GAME OVER, your ship runned out of fuel\n");
                return GAME_OVER;
            }
            
            log.append("The ship is orbiting the ").append(planet.getPlanetColor()).append(" Planet with a space station\n");
            log.append("You can land to mine the following resources: \n");
            
            for(Resource resource : planet.getPlanetResources())
                log.append(" > ").append(resource.getResourceColor()).append("\n");
        
            return RED_ORBIT_PLANET_USER_CHOICE;
        }
        /* PLANET SECTOR WITH WHITE CIRCLE */
        else {
            ship.setShipLocation(2);
            
            if(ship.getFuel() > 0)
                ship.setFuel(ship.getFuel() - 1);
            else {
                log.append("GAME OVER, your ship runned out of fuel\n");
                return GAME_OVER;
            }
            
            log.append("The ship is orbiting the ").append(planet.getPlanetColor()).append(" Planet\n");
            log.append("You can land to mine the following resources\n");
            
            for(Resource resource : planet.getPlanetResources())
                log.append(" > ").append(resource.getResourceColor()).append("\n");
            
            return WHITE_ORBIT_PLANET_USER_CHOICE;
        }
    }
    
    /**
     * Moves the ship to a red dot
     */
    public int moveToRedDot()
    {
        if(ship.getSpaceLocation() == 2 || ship.getSpaceLocation() == 3) {  // if ship was on a planet last iter, must go to red dot
            
            if(ship.getFuel() > 0) {
                ship.setFuel(ship.getFuel() - 1);
                ship.setShipLocation(1);
                log.append("The ship entered a hazard zone (Red Dot)\n");
            
                return DO_EVENT_FROM_REFERENCE_CARD;
            }
            else {
                log.append("GAME OVER, your ship runned out of fuel\n");
                return GAME_OVER;
            }
        }
        return MOVE_TO_NEXT_SPACE_LOCATION;
    }
    
    /**
     * Land the ship on the planet surface to mine resources 
     * Sets all the positions for the ship, resource to mine and the alien
     */
    public int landToMineResources()
    {
        int droneLine, droneColumn;
        boolean overlay = false;
        
        if(ship.getCrew().contains(LANDING_PARTY_OFFICER)) {
            if(ship.getNDrones() != 0) {
                if(planet.getPlanetResources().size() > 0) {

                    log.append("Drone's landing the planet surface\n");
                    ship.setFuel(ship.getFuel() - 1);

                    /* SETS THE RESOURCE POSITION ON THE TERRAIN RANDOMLY */
                    terrain.setResourceLine(this.d6Roll());
                    terrain.setResourceColumn(this.d6Roll());

                    /* SETS THE DRONE AND THE SHIP POSITION ON THE TERRAIN RANDOMLY */
                    droneLine = this.d6Roll();
                    droneColumn = this.d6Roll();

                    ship.getDrone().setLine(droneLine);
                    ship.getDrone().setInitialLine(droneLine);
                    ship.getDrone().setColumn(droneColumn);
                    ship.getDrone().setInitialColumn(droneColumn);
                    
                    /* SETS THE ALIEN POSITION ON THE TERRAIN RANDOMLY */
                    terrain.setAlienLine(this.d6Roll());
                    terrain.setAlienColumn(this.d6Roll());

                    do {
                        overlay = false;

                        /* RESOURCE AND DRONE OVERLAY */
                        if( terrain.getResourceCurrentLine() == ship.getDrone().getCurrentLine() && terrain.getResourceCurrentColumn() == ship.getDrone().getCurrentColumn() )
                        {
                            overlay = true;
                            terrain.setResourceLine(this.d6Roll());
                            terrain.setResourceColumn(this.d6Roll());
                        }

                        /* RESOURCE AND ALIEN OVERLAY */
                        if( terrain.getResourceCurrentLine() == terrain.getAlienCurrentLine() && terrain.getResourceCurrentColumn() == terrain.getAlienCurrentColumn() )
                        {
                            overlay = true;
                            terrain.setAlienLine(this.d6Roll());
                            terrain.setAlienColumn(this.d6Roll());
                        }

                        /* DRONE AND ALIEN OVERLAY */
                        if( ship.getDrone().getCurrentLine() == terrain.getAlienCurrentLine() && ship.getDrone().getCurrentColumn() == terrain.getAlienCurrentColumn() )
                        {
                            overlay = true;
                            droneLine = this.d6Roll();
                            droneColumn = this.d6Roll();

                            ship.getDrone().setLine(droneLine);
                            ship.getDrone().setColumn(droneLine);
                            ship.getDrone().setInitialLine(droneLine);
                            ship.getDrone().setInitialColumn(droneColumn);
                        }

                    } while(overlay);

                    /* FILLS THE 2D ARRAY WITH '-' */
                    for(char[] row : terrain.getTerrain())
                        Arrays.fill(row, '-');

                    /* SELECT RANDOMLY THE RESOURCE TO MINE */
                    if(planet.getPlanetResources().size() > 1) {
                        int randomResource = (int)(Math.random() * planet.getPlanetResources().size() + 1);

                        switch(planet.getPlanetColor()) {
                            case RED_PLANET:
                            case GREEN_PLANET:
                            case BLACK_PLANET:
                                switch(randomResource) {
                                    case 1:
                                        planet.getPlanetResources().get(0).setNextToBeMined(true); break;
                                    case 2:
                                        planet.getPlanetResources().get(1).setNextToBeMined(true); break;
                                }
                                break;

                            case BLUE_PLANET:
                                switch(randomResource) {
                                    case 1:
                                        planet.getPlanetResources().get(0).setNextToBeMined(true); break;
                                    case 2:
                                        planet.getPlanetResources().get(1).setNextToBeMined(true); break;
                                    case 3:
                                        planet.getPlanetResources().get(2).setNextToBeMined(true); break;
                                    case 4:
                                        planet.getPlanetResources().get(3).setNextToBeMined(true); break;
                                }
                                break;
                        }
                    }
                    else
                        planet.getPlanetResources().get(0).setNextToBeMined(true);  /* THE ONLY RESOURCE LEFT IS THE NEXT TO BE MINED */

                    /* INDICATES WHICH RESOURCE WILL THE DRONE TRY TO MINE */
                    for(Resource resource : planet.getPlanetResources())
                        if(resource.getNextToBeMined())
                            log.append("Drone is trying to mine the ").append(resource.getResourceColor()).append("\n");

                    /* SETS AN ID LETTER ON THE TERRAIN ARRAY FOR THE ALIEN, RESOURCE AND DRONE*/
                    terrain.getTerrain()[ship.getDrone().getCurrentLine()][ship.getDrone().getCurrentColumn()] = 'D';
                    terrain.getTerrain()[terrain.getResourceCurrentLine()][terrain.getResourceCurrentColumn()] = 'R'; 
                    terrain.getTerrain()[terrain.getAlienCurrentLine()][terrain.getAlienCurrentColumn()] = 'A';

                    /* SHOWS THE TERRAIN WITH DRONE AND RESOURCE SPAWNED *//*
                    for (char[] terrain1 : terrain.getTerrain()) {
                        for (int j = 0; j < terrain.getTerrain()[0].length; j++) {
                            System.out.print(terrain1[j]);
                        }
                        System.out.println();
                    }*/
                    
                    verifyAdjacentCells();

                    log.append("Please use the [W] [A] [S] [D] to move the drone\n");

                    return SUCCESS;
                }
                log.append("The planet does not have more resources to be mined\n");
                return ERROR;
            }
            else {
                log.append("You don't have drones to mine resources\n");
                return ERROR;
            }
        }
        else {
            log.append("You don't have a Landing Party Officer to mine resources\n");
            return ERROR;
        }
    }
    
    public int moveDrone(int keyPressed)
    {        
        switch(keyPressed) {
            case W_UPPERCASE:
            case W_LOWERCASE:
                if(ship.getDrone().getCurrentLine() == 0)
                    ship.getDrone().setLine(ship.getDrone().getCurrentLine());
                else
                    ship.getDrone().setLine(ship.getDrone().getCurrentLine()-1);
                break;
                
            case S_UPPERCASE:
            case S_LOWERCASE:
                if(ship.getDrone().getCurrentLine() == 5)
                    ship.getDrone().setLine(ship.getDrone().getCurrentLine());
                else
                    ship.getDrone().setLine(ship.getDrone().getCurrentLine()+1);
                break;
                
            case A_UPPERCASE:
            case A_LOWERCASE:
                if(ship.getDrone().getCurrentColumn() == 0)
                    ship.getDrone().setColumn(ship.getDrone().getCurrentColumn());
                else
                    ship.getDrone().setColumn(ship.getDrone().getCurrentColumn()-1);
                break;
                
            case D_UPPERCASE:
            case D_LOWERCASE:
                if(ship.getDrone().getCurrentColumn() == 5)
                    ship.getDrone().setColumn(ship.getDrone().getCurrentColumn());
                else
                    ship.getDrone().setColumn(ship.getDrone().getCurrentColumn()+1);
                break;
        }
        
        for(char[] row : terrain.getTerrain())
            Arrays.fill(row, '-');
        
        terrain.getTerrain()[ship.getDrone().getInitialLine()][ship.getDrone().getInitialColumn()] = 'S';
        terrain.getTerrain()[ship.getDrone().getCurrentLine()][ship.getDrone().getCurrentColumn()] = 'D';
        
        for(int i = 0; i < planet.getPlanetResources().size(); i++) 
            if(planet.getPlanetResources().get(i).getNextToBeMined()) 
                terrain.getTerrain()[terrain.getResourceCurrentLine()][terrain.getResourceCurrentColumn()] = 'R';
        
        if(terrain.getAlien().getAlienAlive())
            terrain.getTerrain()[terrain.getAlienCurrentLine()][terrain.getAlienCurrentColumn()] = 'A';
        
        /* SHOWS THE TERRAIN WITH DRONE AND RESOURCE SPAWNED *//*
        for (char[] grid : terrain.getTerrain()) {
            for (int j = 0; j < terrain.getTerrain()[0].length; j++) {
                System.out.print(grid[j]);
            }
            System.out.println();
        }*/
        
        /* IF ALIEN IS ADJACENT TO THE DRONE */
        if(verifyAdjacentCells() == DRONE_DESTROYED)
            return DRONE_DESTROYED;
        
        /* ALIEN MOVEMENT TOWARDS DRONE */
        if(terrain.getAlien().getAlienAlive())
            alienMovement();
        else {
            if(terrain.getItToNextAlien() == 0) {
                terrain.createNewAlien();
                terrain.setAlienLine(this.d6Roll());
                terrain.setAlienColumn(this.d6Roll());
            }
            else
                terrain.setItToNextAlien(terrain.getItToNextAlien()-1);
        }
        
        /* IF DRONE REACHED TO THE RESOURCE */
        if( terrain.getTerrain()[ship.getDrone().getCurrentLine()][ship.getDrone().getCurrentColumn()] == 
            terrain.getTerrain()[terrain.getResourceCurrentLine()][terrain.getResourceCurrentColumn()] ) 
        {
            for(int i = 0; i < planet.getPlanetResources().size(); i++) {
                if(planet.getPlanetResources().get(i).getNextToBeMined()) {
                    log.append("The drone found the ").append(planet.getPlanetResources().get(i).getResourceColor()).append(" resource\n");
                    ship.getDrone().setCurrentResource(planet.getPlanetResources().get(i).getResourceColor());
                    planet.getPlanetResources().remove(i);
                }
            }
        }
            
        /* IF DRONE RETURNED TO THE SHIP */ 
        if( terrain.getTerrain()[ship.getDrone().getCurrentLine()][ship.getDrone().getCurrentColumn()] == 
            terrain.getTerrain()[ship.getDrone().getInitialLine()][ship.getDrone().getInitialColumn()])
        {
            if(returnedToTheShip() == GAME_WIN)
                return GAME_WIN;
            else {
                if(ship.getSpaceLocation() == 2) {
                    return RETURNED_TO_SHIP_WHITE_ORBIT;
                }
                else if(ship.getSpaceLocation() == 3) {
                    return RETURNED_TO_SHIP_RED_ORBIT;
                }
            }
        }
        
        return SUCCESS;
    }
    
    public int returnedToTheShip()
    {
        log.append("The drone returned to the ship with the ").append(ship.getDrone().getCurrentResource()).append(" resource\n");
        log.append("Unloading the resource to the cargo\n");
        
        int unitsToUnload = this.d6Roll() + 1;
        int unitsOnTheCargo;
        int maxUnitsCargoCanStore = 0;
        
        switch(ship.getCargo().getLevel()) {
            case 0:
                maxUnitsCargoCanStore = 6;
                break;
            case 1:
                maxUnitsCargoCanStore = 12;
                break;
            case 2:
                maxUnitsCargoCanStore = 18;
                break;
            case 3:
                maxUnitsCargoCanStore = 24;
                break;
        }
        
        for(Resource resource : ship.getCargo().getResources()) {
            if(resource.getResourceColor().equals(ship.getDrone().getCurrentResource())) {
                unitsOnTheCargo = resource.getResourceAmount();
                
                if(resource.getResourceAmount() < maxUnitsCargoCanStore) {
                    ship.setResourceUnits(ship.getDrone().getCurrentResource(), unitsOnTheCargo + unitsToUnload);
                    log.append("The cargo has now more ").append(unitsToUnload).append(" of the ").append(ship.getDrone().getCurrentResource()).append(" resource\n");
                }
                else {
                    log.append("The cargo is already full of ").append(ship.getDrone().getCurrentResource()).append(" resource\n");
                    log.append("Upgrade cargo to the next level to store more units\n");
                }
            }
        }
        
        /* SHOWS THE CARGO CONTENT */
        log.append("Ship cargo content: \n");
        for(Resource resource : ship.getCargo().getResources())
            log.append(" > ").append(resource.getResourceColor()).append(": ").append(resource.getResourceAmount()).append("\n");
        
        /* VERIFY THE NUMBER OF ARTEFACTS */
        for(Resource resource : ship.getCargo().getResources()) {
            if(resource.getResourceColor().equals(ARTEFACT)) {
                if(resource.getResourceAmount() == 5) {
                    log.append("GAME WIN, you found the 5 artifacts");
                    return GAME_WIN;
                }
            }
        }
        return SUCCESS;
    }
    
    public void convertResourcesIntoSupplies(int conversion)
    {        
        boolean enoughResources = true;
        
        if(ship.getCrew().contains(CARGO_HOLD_OFFICER)) {
            
            switch(conversion) {
                case 1:     // conversion to energy shield
                    for(Resource resource : ship.getCargo().getResources()) {
                        switch(resource.getResourceColor()) {
                            case BLUE_RESOURCE:
                            case GREEN_RESOURCE:
                            case BLACK_RESOURCE:
                                if(resource.getResourceAmount() < 1)
                                    enoughResources = false;
                                break;
                        }
                    }

                    if(enoughResources) {
                        ship.setShields(ship.getShields()+1);
                        log.append("You have 1 more shield now\n");
                        log.append("Shields available: ").append(ship.getShields()).append("\n");
                    }
                    else
                        log.append("You don't have enough resources to convert to shields\n");

                    break;
                case 2:     // conversion to ammo
                    for(Resource resource : ship.getCargo().getResources()) {
                        switch(resource.getResourceColor()) {
                            case BLUE_RESOURCE:
                            case BLACK_RESOURCE:
                                if(resource.getResourceAmount() < 1)
                                    enoughResources = false;
                                break;
                        }
                    }

                    if(enoughResources) {
                        ship.setWeapons(ship.getWeapons()+1);
                        log.append("You have 1 more weapon now\n");
                        log.append("Weapons available: ").append(ship.getWeapons()).append("\n");
                    }
                    else
                        log.append("You don't have enough resources to convert to weapons\n");
                    break;
                case 3:     // conversion to fuel
                    for(Resource resource : ship.getCargo().getResources()) {
                        switch(resource.getResourceColor()) {
                            case RED_RESOURCE:
                            case GREEN_RESOURCE:
                            case BLACK_RESOURCE:
                                if(resource.getResourceAmount() < 1)
                                    enoughResources = false;
                                break;
                        }
                    }

                    if(enoughResources) {
                        ship.setFuel(ship.getFuel()+1);
                        log.append("You have now 1 more fuel cell\n");
                        log.append("Fuel available: ").append(ship.getFuel()).append("\n");
                    }
                    else
                        log.append("You don't have enough resources to convert to fuel\n");
                    break;  
            }
        }
        else
            log.append("You don't have a Cargo Hold Officer, hire one to convert resources\n");
    }
    
    public int verifyAdjacentCells()
    {
        if(terrain.getAlien().getAlienAlive()) {
            if(ship.getDrone().getCurrentLine() == 0) {
                if(ship.getDrone().getCurrentColumn() == 0) {
                    // checks up and down
                    if( terrain.getTerrain()[ship.getDrone().getCurrentLine()+1][ship.getDrone().getCurrentColumn()] ==  
                        terrain.getTerrain()[terrain.getAlienCurrentLine()][terrain.getAlienCurrentColumn()] ||
                        terrain.getTerrain()[ship.getDrone().getCurrentLine()][ship.getDrone().getCurrentColumn()+1] == 
                        terrain.getTerrain()[terrain.getAlienCurrentLine()][terrain.getAlienCurrentColumn()]) 
                    {
                        log.append("The drone and the alien met, a fight will start...\n");
                        return alienFight();
                    }                
                }
                else if(ship.getDrone().getCurrentColumn() == 5) {
                    // checks down and left
                    if( terrain.getTerrain()[ship.getDrone().getCurrentLine()+1][ship.getDrone().getCurrentColumn()] ==  
                        terrain.getTerrain()[terrain.getAlienCurrentLine()][terrain.getAlienCurrentColumn()] ||
                        terrain.getTerrain()[ship.getDrone().getCurrentLine()][ship.getDrone().getCurrentColumn()-1] == 
                        terrain.getTerrain()[terrain.getAlienCurrentLine()][terrain.getAlienCurrentColumn()]) 
                    {
                        log.append("The drone and the alien met, a fight will start...\n");
                        return alienFight();
                    }
                }
                else {
                    // checks down and both sides
                    if( terrain.getTerrain()[ship.getDrone().getCurrentLine()+1][ship.getDrone().getCurrentColumn()] ==  
                        terrain.getTerrain()[terrain.getAlienCurrentLine()][terrain.getAlienCurrentColumn()] ||
                        terrain.getTerrain()[ship.getDrone().getCurrentLine()][ship.getDrone().getCurrentColumn()-1] == 
                        terrain.getTerrain()[terrain.getAlienCurrentLine()][terrain.getAlienCurrentColumn()] ||
                        terrain.getTerrain()[ship.getDrone().getCurrentLine()][ship.getDrone().getCurrentColumn()+1] == 
                        terrain.getTerrain()[terrain.getAlienCurrentLine()][terrain.getAlienCurrentColumn()])
                    {
                        log.append("The drone and the alien met, a fight will start...\n");
                        return alienFight();
                    }
                }
            }

            if(ship.getDrone().getCurrentLine() == 5) {
                if(ship.getDrone().getCurrentColumn() == 0) {
                    // checks up and right
                    if( terrain.getTerrain()[ship.getDrone().getCurrentLine()-1][ship.getDrone().getCurrentColumn()] ==  
                        terrain.getTerrain()[terrain.getAlienCurrentLine()][terrain.getAlienCurrentColumn()] ||
                        terrain.getTerrain()[ship.getDrone().getCurrentLine()][ship.getDrone().getCurrentColumn()+1] == 
                        terrain.getTerrain()[terrain.getAlienCurrentLine()][terrain.getAlienCurrentColumn()]) 
                    {
                        log.append("The drone and the alien met, a fight will start...\n");
                        return alienFight();
                    } 
                }
                else if(ship.getDrone().getCurrentColumn() == 5) {
                    // checks up and left
                    if( terrain.getTerrain()[ship.getDrone().getCurrentLine()-1][ship.getDrone().getCurrentColumn()] ==  
                        terrain.getTerrain()[terrain.getAlienCurrentLine()][terrain.getAlienCurrentColumn()] ||
                        terrain.getTerrain()[ship.getDrone().getCurrentLine()][ship.getDrone().getCurrentColumn()-1] == 
                        terrain.getTerrain()[terrain.getAlienCurrentLine()][terrain.getAlienCurrentColumn()]) 
                    {
                        log.append("The drone and the alien met, a fight will start...\n");
                        return alienFight();
                    } 
                }
                else {
                    // checks up and both sides
                    if( terrain.getTerrain()[ship.getDrone().getCurrentLine()-1][ship.getDrone().getCurrentColumn()] ==  
                        terrain.getTerrain()[terrain.getAlienCurrentLine()][terrain.getAlienCurrentColumn()] ||
                        terrain.getTerrain()[ship.getDrone().getCurrentLine()][ship.getDrone().getCurrentColumn()-1] == 
                        terrain.getTerrain()[terrain.getAlienCurrentLine()][terrain.getAlienCurrentColumn()] ||
                        terrain.getTerrain()[ship.getDrone().getCurrentLine()][ship.getDrone().getCurrentColumn()+1] == 
                        terrain.getTerrain()[terrain.getAlienCurrentLine()][terrain.getAlienCurrentColumn()])
                    {
                        log.append("The drone and the alien met, a fight will start...\n");
                        return alienFight();
                    }
                }
            }

            if(ship.getDrone().getCurrentColumn() == 0 && ship.getDrone().getCurrentLine() != 0 && ship.getDrone().getCurrentLine() != 5) {
                // checks up, down and right
                if( terrain.getTerrain()[ship.getDrone().getCurrentLine()-1][ship.getDrone().getCurrentColumn()] ==  
                    terrain.getTerrain()[terrain.getAlienCurrentLine()][terrain.getAlienCurrentColumn()] ||
                    terrain.getTerrain()[ship.getDrone().getCurrentLine()+1][ship.getDrone().getCurrentColumn()] == 
                    terrain.getTerrain()[terrain.getAlienCurrentLine()][terrain.getAlienCurrentColumn()] ||
                    terrain.getTerrain()[ship.getDrone().getCurrentLine()][ship.getDrone().getCurrentColumn()+1] == 
                    terrain.getTerrain()[terrain.getAlienCurrentLine()][terrain.getAlienCurrentColumn()])
                {
                    log.append("The drone and the alien met, a fight will start...\n");
                    return alienFight();
                }
            }
            else if(ship.getDrone().getCurrentColumn() == 5 && ship.getDrone().getCurrentLine() != 0 && ship.getDrone().getCurrentLine() != 5) {
                // checks up, down and left
                if( terrain.getTerrain()[ship.getDrone().getCurrentLine()-1][ship.getDrone().getCurrentColumn()] ==  
                    terrain.getTerrain()[terrain.getAlienCurrentLine()][terrain.getAlienCurrentColumn()] ||
                    terrain.getTerrain()[ship.getDrone().getCurrentLine()+1][ship.getDrone().getCurrentColumn()] == 
                    terrain.getTerrain()[terrain.getAlienCurrentLine()][terrain.getAlienCurrentColumn()] ||
                    terrain.getTerrain()[ship.getDrone().getCurrentLine()][ship.getDrone().getCurrentColumn()-1] == 
                    terrain.getTerrain()[terrain.getAlienCurrentLine()][terrain.getAlienCurrentColumn()])
                {
                    log.append("The drone and the alien met, a fight will start...\n");
                    return alienFight();
                }
            }
            else if(ship.getDrone().getCurrentLine() != 0 && ship.getDrone().getCurrentLine() != 5 && ship.getDrone().getCurrentColumn() != 0 && ship.getDrone().getCurrentColumn() != 5) {
                // checks all sides
                if( terrain.getTerrain()[ship.getDrone().getCurrentLine()-1][ship.getDrone().getCurrentColumn()] ==  
                    terrain.getTerrain()[terrain.getAlienCurrentLine()][terrain.getAlienCurrentColumn()] ||
                    terrain.getTerrain()[ship.getDrone().getCurrentLine()+1][ship.getDrone().getCurrentColumn()] == 
                    terrain.getTerrain()[terrain.getAlienCurrentLine()][terrain.getAlienCurrentColumn()] ||
                    terrain.getTerrain()[ship.getDrone().getCurrentLine()][ship.getDrone().getCurrentColumn()-1] == 
                    terrain.getTerrain()[terrain.getAlienCurrentLine()][terrain.getAlienCurrentColumn()] || 
                    terrain.getTerrain()[ship.getDrone().getCurrentLine()][ship.getDrone().getCurrentColumn()+1] == 
                    terrain.getTerrain()[terrain.getAlienCurrentLine()][terrain.getAlienCurrentColumn()])
                {
                    log.append("The drone and the alien met, a fight will start...\n");
                    return alienFight();
                }
            }
        }
        
        return SUCCESS;
    }

    public void alienMovement()
    {        
        /* ALIEN POSITION */
        int alienLine = terrain.getAlienCurrentLine();
        int alienColumn = terrain.getAlienCurrentColumn();
        
        /* DRONE POSITION */
        int droneLine = ship.getDrone().getCurrentLine();
        int droneColumn = ship.getDrone().getCurrentColumn();
        
        if(terrain.getAlien().getAlienAlive()) 
        {
            switch(alienLine)
            {
                case 0:     // alien line = 0
                    switch(alienColumn)
                    {
                        case 0:     // [0][0]
                            if(droneLine == alienLine)
                                alienColumn += 1;
                            else if(droneColumn == alienColumn)
                                alienLine += 1;
                            else if(droneColumn > alienColumn)
                                alienColumn += 1;
                            else if(droneLine > alienLine)
                                alienLine += 1;
                            break;
                            
                        case 5:     // [0][5]
                            if(droneLine == alienLine)
                                alienColumn -= 1;
                            else if(droneColumn == alienColumn)
                                alienLine += 1;
                            else if(droneColumn < alienColumn)
                                alienColumn -= 1;
                            else if(droneLine > alienLine)
                                alienLine += 1;
                            break;
                                                        
                        case 1:    
                        case 2:
                        case 3:
                        case 4:
                            if(droneLine == alienLine && droneColumn > alienColumn)
                                alienColumn += 1;
                            else if(droneLine == alienLine && droneColumn < alienColumn)
                                alienColumn -= 1;
                            else if(droneLine > alienLine && droneColumn > alienColumn)
                            {
                                if(Math.abs(droneColumn - alienColumn) > Math.abs(droneLine - alienLine))
                                    alienColumn += 1;
                                else
                                    alienLine += 1;
                            }
                            else if(droneLine > alienLine && droneColumn < alienColumn)
                            {
                                if(Math.abs(droneColumn - alienColumn) > Math.abs(droneLine - alienLine))
                                    alienColumn += 1;
                                else
                                    alienLine += 1;
                            }
                            else if(droneColumn == alienColumn)
                                alienLine += 1;
                            break;                                
                    }
                    break;
                
                case 5:     // alien line = 5
                    switch(alienColumn)
                    {
                        case 0:
                            if(droneLine == alienLine)
                                alienColumn += 1;
                            else if(droneColumn == alienColumn)
                                alienLine -= 1;
                            else if(droneColumn > alienColumn)
                                alienColumn += 1;
                            else if(droneLine < alienLine)
                                alienLine -= 1;
                            break;
                            
                        case 5:
                            if(droneLine == alienLine)
                                alienColumn -= 1;
                            else if(droneColumn == alienColumn)
                                alienLine -= 1;
                            else if(droneColumn < alienColumn)
                                alienColumn -= 1;
                            else if(droneLine < alienLine)
                                alienLine -= 1;
                            break;
                            
                        case 1:
                        case 2:
                        case 3:
                        case 4:
                            if(droneLine == alienLine && droneColumn > alienColumn)
                                alienColumn += 1;
                            else if(droneLine == alienLine && droneColumn < alienColumn)
                                alienColumn -= 1;
                            else if(droneLine < alienLine && droneColumn > alienColumn)
                            {
                                if(Math.abs(droneColumn - alienColumn) > Math.abs(droneLine - alienLine))
                                    alienColumn += 1;
                                else
                                    alienLine -= 1;
                            }
                            else if(droneLine < alienLine && droneColumn < alienColumn)
                            {
                                if(Math.abs(droneColumn - alienColumn) > Math.abs(droneLine - alienLine))
                                    alienColumn -= 1;
                                else
                                    alienLine -= 1;
                            }
                            else if(droneColumn == alienColumn)
                                alienLine += 1;
                            break;
                    }
                    break;
                case 1:
                case 2:
                case 3:
                case 4:
                    switch(alienColumn)
                    {
                        case 0:     // [1, 2, 3, 4][0]
                            if(droneLine == alienLine)
                                alienColumn += 1;
                            else if(droneColumn == alienColumn && droneLine < alienLine)
                                alienLine -= 1;
                            else if(droneColumn == alienColumn && droneLine > alienLine)
                                alienLine += 1;
                            else if(droneColumn > alienColumn && droneLine < alienLine)
                            {
                                if(Math.abs(droneColumn - alienColumn) > Math.abs(droneLine - alienLine))
                                    alienColumn += 1;
                                else
                                    alienLine -= 1;
                            }
                            else if(droneColumn > alienColumn && droneLine > alienLine)
                            {
                                if(Math.abs(droneColumn - alienColumn) > Math.abs(droneLine - alienLine))
                                    alienColumn += 1;
                                else
                                    alienLine += 1;
                            }
                            break;
                            
                        case 5:     // [1, 2, 3, 4][5]
                            if(droneLine == alienLine)
                                alienColumn -= 1;
                            else if(droneColumn == alienColumn && droneLine < alienLine)
                                alienLine -= 1;
                            else if(droneColumn == alienColumn && droneLine > alienLine)
                                alienLine += 1;
                            else if(droneColumn < alienColumn && droneLine < alienLine)
                            {
                                if(Math.abs(droneColumn - alienColumn) > Math.abs(droneLine - alienLine))
                                    alienColumn -= 1;
                                else
                                    alienLine -= 1;
                            }
                            else if(droneColumn < alienColumn && droneLine > alienLine)
                            {
                                if(Math.abs(droneColumn - alienColumn) > Math.abs(droneLine - alienLine))
                                    alienColumn -= 1;
                                else
                                    alienLine += 1;
                            }
                            break;
                            
                        case 1:     // all array positions without the margins
                        case 2:
                        case 3:
                        case 4:
                            if(droneLine == alienLine)
                            {
                                if(droneColumn < alienColumn)
                                    alienColumn -= 1;
                                else if(droneColumn > alienColumn)
                                    alienColumn += 1;
                            }
                            else if(droneLine > alienLine) {
                                if(Math.abs(droneColumn - alienColumn) > Math.abs(droneLine - alienLine))
                                    alienColumn += 1;
                                else
                                    alienLine += 1; 
                            }
                            else if(droneLine < alienLine) {
                                if(Math.abs(droneColumn - alienColumn) > Math.abs(droneLine - alienLine))
                                    alienColumn += 1;
                                else
                                    alienLine -= 1;
                            }
                    }
            }
            
            if(alienLine < 0)
                terrain.setAlienLine(0);
            else if(alienLine > 5)
                terrain.setAlienLine(5);
            else
                terrain.setAlienLine(alienLine);
            
            if(alienColumn < 0)
                terrain.setAlienColumn(0);
            else if(alienLine > 5)
                terrain.setAlienColumn(5);
            else
                terrain.setAlienColumn(alienColumn);
            
            if(droneLine < 0)
                ship.getDrone().setLine(0);
            else if(droneLine > 5)
                ship.getDrone().setLine(5);
            else
                ship.getDrone().setLine(droneLine);
            
            if(droneColumn < 0)
                ship.getDrone().setColumn(0);
            else if(droneLine > 5)
                ship.getDrone().setColumn(5);
            else
                ship.getDrone().setColumn(droneColumn);
            
        }
    }
    
    public int alienFight()
    {
        int dice, i = 1, itToNextAlien;
        boolean gameState = true;
        
        switch(terrain.getAlien().getAlienColor()) {
            case RED_ALIEN:
                while(gameState) {
                    log.append("\nRound ").append(i).append(": ");
                    dice = this.d6Roll() + 1;

                    switch (dice) {
                    // alien attack
                        case 1:
                            ship.getDrone().setArmor(ship.getDrone().getCurrentArmor()-1);
                            log.append("The drone lost 1 armor\n");
                            log.append("Armor available: ").append(ship.getDrone().getCurrentArmor()).append("\n");
                            
                            if(ship.getDrone().getCurrentArmor() < 1) {
                                gameState = false;
                                log.append("The drone was destroyed by the alien, the exploration is over\n");
                                ship.setNDrones(0);
                                
                                return DRONE_DESTROYED;
                            }
                            break;
                    // drone attack
                        case 5:
                        case 6:
                            terrain.getAlien().setAlienAlive(false);
                            log.append("The drone destroyed the alien\n");
                            gameState = false;
                            
                            itToNextAlien = this.d6Roll() + 1;
                            terrain.setItToNextAlien(itToNextAlien);
                            log.append("Iterations to next alien: ").append(itToNextAlien).append("\n");
                            
                            break;
                        default:
                            log.append("Missed\n");
                            break;
                    }
                    i++;
                }
            case BLUE_ALIEN:
                while(gameState) {
                    log.append("\nRound ").append(i).append(": ");
                    dice = this.d6Roll() + 1;
                    
                    switch (dice) {
                    // alien attack
                    // drone attack
                        case 3:
                        case 5:
                            ship.getDrone().setArmor(ship.getDrone().getCurrentArmor()-1);
                            log.append("The drone lost 1 armor\n");
                            log.append("Armor available: ").append(ship.getDrone().getCurrentArmor()).append("\n");
                            
                            if(ship.getDrone().getCurrentArmor() < 1) {
                                gameState = false;
                                log.append("The drone was destroyed by the alien, the exploration is over\n");
                                ship.setNDrones(0);
                                
                                return DRONE_DESTROYED;
                            }
                            
                            terrain.getAlien().setAlienAlive(false);
                            log.append("The drone destroyed the alien\n");
                            gameState = false;
                            
                            itToNextAlien = this.d6Roll() + 1;
                            terrain.setItToNextAlien(itToNextAlien);
                            log.append("Iterations to next alien: ").append(itToNextAlien).append("\n");
                            
                            break;
                        default:
                            log.append("Missed\n");
                            break;
                    }  
                    i++;
                }
            case GREEN_ALIEN:
                while(gameState) {
                    log.append("\nRound ").append(i).append(": ");
                    dice = this.d6Roll() + 1;
                    
                    switch (dice) {
                    // alien attack
                        case 1:
                        case 2:
                            ship.getDrone().setArmor(ship.getDrone().getCurrentArmor()-1);
                            log.append("The drone lost 1 armor\n");
                            log.append("Armor available: ").append(ship.getDrone().getCurrentArmor()).append("\n");
                            
                            if(ship.getDrone().getCurrentArmor() < 1) {
                                gameState = false;
                                log.append("The drone was destroyed by the alien, the exploration is over\n");
                                ship.setNDrones(0);
                                
                                return DRONE_DESTROYED;
                            }
                            break;
                            
                    // drone attack
                        case 4:
                        case 6:
                            terrain.getAlien().setAlienAlive(false);
                            log.append("The drone destroyed the alien\n");
                            gameState = false;
                            
                            itToNextAlien = this.d6Roll() + 1;
                            terrain.setItToNextAlien(itToNextAlien);
                            log.append("Iterations to next alien: ").append(itToNextAlien).append("\n");
                            
                            break;
                        default:
                            log.append("Missed\n");
                            break;
                    }  
                    i++;
                }
            case BLACK_ALIEN:
                while(gameState) {
                    log.append("\nRound ").append(i).append(": ");
                    dice = this.d6Roll() + 1;
                    
                    switch (dice) {
                    // alien attack
                        case 1:
                            ship.getDrone().setArmor(ship.getDrone().getCurrentArmor()-1);
                            log.append("The drone lost 1 armor\n");
                            log.append("Armor available: ").append(ship.getDrone().getCurrentArmor()).append("\n");
                            
                            if(ship.getDrone().getCurrentArmor() < 1) {
                                gameState = false;
                                log.append("The drone was destroyed by the alien, the exploration is over\n");
                                ship.setNDrones(0);
                                
                                return DRONE_DESTROYED;
                            }
                            break;
                    // drone attack
                        case 5:
                        case 6:
                            terrain.getAlien().setAlienAlive(false);
                            log.append("The drone destroyed the alien\n");
                            gameState = false;
                            
                            itToNextAlien = this.d6Roll() + 1;
                            terrain.setItToNextAlien(itToNextAlien);
                            log.append("Iterations to next alien: ").append(itToNextAlien).append("\n");
                            
                            break;
                        default:
                            log.append("Missed\n");
                            break;
                    }  
                    i++;
                }
        }
        return SUCCESS;
    }
    
    public void upgradeCargo()
    {
        boolean enoughResources = true;
        
        for(int i = 0; i < ship.getCargo().getResources().size()-1; i++) {
            if(ship.getCargo().getResources().get(i).getResourceAmount() < 3) {
                enoughResources = false;
            }
        }
        
        if(enoughResources) {
            if(ship.getCargo().getLevel() < 3) {
                ship.getCargo().setLevel(ship.getCargo().getLevel() + 1);

                /* DECREASES AMOUNT BY 3 ON EACH RESOURCE */
                for (Resource resource : ship.getCargo().getResources()) 
                    ship.getCargo().setResourceUnits(resource.getResourceColor(), resource.getResourceAmount() - 3);

                log.append("Cargo updated to level ").append(ship.getCargo().getLevel()).append("\n");
            }
            else
                log.append("Your cargo is already at max level\n");
        }
        else 
            log.append("You don't have enough resources to upgrade the cargo\n");
    }
    
    public void convertSingleResource(int resourceOrigin, int resourceFinal)
    {
        String resource1 = "", resource2 = "";
        
        switch(resourceOrigin) {
            case 1:
                resource1 = RED_RESOURCE; break;
            case 2:
                resource1 = BLUE_RESOURCE; break;
            case 3:
                resource1 = GREEN_RESOURCE; break;
            case 4:
                resource1 = BLACK_RESOURCE; break;
        }
        
        switch(resourceFinal) {
            case 1:
                resource2 = RED_RESOURCE; break;
            case 2:
                resource2 = BLUE_RESOURCE; break;
            case 3:
                resource2 = GREEN_RESOURCE; break;
            case 4:
                resource2 = BLACK_RESOURCE; break;
        }
        
        int newAmountResource1, newAmountResource2;
        
        for(int i = 0; i < ship.getCargo().getResources().size()-1; i++) {
            if(ship.getCargo().getResources().get(i).getResourceColor().equals(resource1)) {
                if(ship.getCargo().getResources().get(i).getResourceAmount() >= 1) {
                    for(int j = 0; j < ship.getCargo().getResources().size()-1; j++) {
                        if(ship.getCargo().getResources().get(j).getResourceColor().equals(resource2)) {
                            newAmountResource1 = ship.getCargo().getResources().get(i).getResourceAmount() - 1;
                            newAmountResource2 = ship.getCargo().getResources().get(j).getResourceAmount() + 1;
                            
                            ship.getCargo().setResourceUnits(resource1, newAmountResource1);
                            ship.getCargo().setResourceUnits(resource2, newAmountResource2);
                            
                            log.append("You exchanged 1 unit of the ").append(resource1).append(" resource for 1 unit of the ").append(resource2).append(" resource\n");
                        }
                    }
                }
            }
        }
        
        /* SHOWS THE CARGO CONTENT */
        log.append("Resources available: \n");
        for(Resource resource : ship.getCargo().getResources())
            log.append(" > ").append(resource.getResourceColor()).append(": ").append(resource.getResourceAmount()).append("\n");
    }
    
    public void hireALostCrewMember()
    {
        int crewSize;
        boolean hired = false, enoughResources = true;
        
        for(int i = 0; i < ship.getCargo().getResources().size()-1; i++) 
            if(ship.getCargo().getResources().get(i).getResourceAmount() < 1) 
                enoughResources = false;
        
        if(enoughResources) {
            if(ship.getCrew().size() < 6) {
                crewSize = ship.getCrew().size();

                switch(crewSize) {
                    case 1:
                        ship.getCrew().add(NAVIGATION_OFFICER); 
                        log.append("You hired a ").append(NAVIGATION_OFFICER).append("\n");
                        hired = true;
                        break;
                    case 2:
                        ship.getCrew().add(LANDING_PARTY_OFFICER); 
                        log.append("You hired a ").append(LANDING_PARTY_OFFICER).append("\n");
                        hired = true;
                        break;
                    case 3:
                        ship.getCrew().add(SHIELDS_OFFICER); 
                        log.append("You hired a ").append(SHIELDS_OFFICER).append("\n");
                        hired = true;
                        break;
                    case 4:
                        ship.getCrew().add(WEAPONS_OFFICER); 
                        log.append("You hired a ").append(WEAPONS_OFFICER).append("\n");
                        hired = true;
                        break;
                    case 5:
                        ship.getCrew().add(CARGO_HOLD_OFFICER); 
                        log.append("You hired a ").append(CARGO_HOLD_OFFICER).append("\n");
                        hired = true;
                        break;
                }

                
                if(hired) {
                    /* DECREASES AMOUNT BY 1 ON EACH RESOURCE */
                    for(int i = 0; i < ship.getCargo().getResources().size()-1; i++) 
                        ship.getCargo().setResourceUnits(ship.getCargo().getResources().get(i).getResourceColor(), ship.getCargo().getResources().get(i).getResourceAmount() - 1);
                }
            }
            else
                log.append("You already have a full crew, there is no space for more crew members\n");
        }
        else
            log.append("You don't have enough resources to hire a crew member\n");
        
        /* SHOWS THE CARGO CONTENT */
        log.append("Resource available: ");
        for(int i = 0; i < ship.getCargo().getResources().size()-1; i++) 
            log.append(" > ").append(ship.getCargo().getResources().get(i).getResourceColor()).append(": ").append(ship.getCargo().getResources().get(i).getResourceAmount());
    }
    
    public void repairDrone()
    {
        if(ship.getDrone().getCurrentArmor() < 6) {
            for(int i = 0; i < ship.getCargo().getResources().size()-1; i++) {
                if(ship.getCargo().getResources().get(i).getResourceAmount() > 0)
                    ship.getDrone().setArmor(6);
                else
                    log.append("You don't have enough resources to repair the drone\n");

                ship.getCargo().getResources().get(i).setAmount(ship.getCargo().getResources().get(i).getResourceAmount() - 1);
            }


            /* SHOWS THE CARGO CONTENT */
            log.append("Resource available: ");
            for(int i = 0; i < ship.getCargo().getResources().size()-1; i++)
                log.append(" > ").append(ship.getCargo().getResources().get(i).getResourceColor()).append(": ").append(ship.getCargo().getResources().get(i).getResourceAmount());
        }
        else
            log.append("Your drone has already full armor strength\n");
    }
    
    public void buyDrone()
    {
        if(ship.getNDrones() == 0) {
            for(int i = 0; i < ship.getCargo().getResources().size()-1; i++) {
                if(ship.getCargo().getResources().get(i).getResourceAmount() > 2)
                    ship.createNewDrone();
                else
                    log.append("You don't have enough resources to buy a new drone\n");

                ship.getCargo().getResources().get(i).setAmount(ship.getCargo().getResources().get(i).getResourceAmount() - 3);
            }

            /* SHOWS THE CARGO CONTENT */
            log.append("Resource available: ");
            for(int i = 0; i < ship.getCargo().getResources().size()-1; i++)
                log.append(" > ").append(ship.getCargo().getResources().get(i).getResourceColor()).append(": ").append(ship.getCargo().getResources().get(i).getResourceAmount());
        }
        else
            log.append("You already have a drone\n");
    }
    
    public int doEventFromReferenceCard()
    {
        int result = (int)(Math.random() * (DICE_MAX-DICE_MIN) + 1) + DICE_MIN;
        
        switch(result) {
            case CREW_DEATH:
                if(!ship.getCrew().contains(SHIELDS_OFFICER)) {
                    log.append("You don't have a shields officer, the effect will double\n");
                    eventCrewDeath();
                }
                return eventCrewDeath();
                
            case SALVAGE_SHIP:
                eventSalvageShip();
                if(!ship.getCrew().contains(SHIELDS_OFFICER)) {
                    log.append("You don't have a shields officer, the effect will double\n");
                    eventSalvageShip();
                }
                break;
                
            case CARGO_LOSS:
                eventCargoLoss();
                if(!ship.getCrew().contains(SHIELDS_OFFICER)) {
                    log.append("You don't have a shields officer, the effect will double\n");
                    eventCargoLoss();
                }
                break;
                
            case FUEL_LOSS:
                if(!ship.getCrew().contains(SHIELDS_OFFICER)) {
                    log.append("You don't have a shields officer, the effect will double\n");
                    eventFuelLoss();
                }
                return eventFuelLoss();
                
            case NO_EVENT:
                log.append("Nothing happens");
                break;
                
            case CREW_RESCUE:
                if(!ship.getCrew().contains(SHIELDS_OFFICER)) {
                    log.append("You don't have a shields officer, the effect will double\n");
                    eventCrewRescue();
                }
                eventCrewRescue();
                break;
        }
        return SUCCESS;
    }
    
    public int doSpecificEventFromReferenceCard(int event)
    {        
        switch(event) {
            case CREW_DEATH:
                if(!ship.getCrew().contains(SHIELDS_OFFICER)) {
                    log.append("You don't have a shields officer, the effect will double\n");
                    eventCrewDeath();
                }
                return eventCrewDeath();
                
            case SALVAGE_SHIP:
                eventSalvageShip();
                if(!ship.getCrew().contains(SHIELDS_OFFICER)) {
                    log.append("You don't have a shields officer, the effect will double\n");
                    eventSalvageShip();
                }
                break;
                
            case CARGO_LOSS:
                eventCargoLoss();
                if(!ship.getCrew().contains(SHIELDS_OFFICER)) {
                    log.append("You don't have a shields officer, the effect will double\n");
                    eventCargoLoss();
                }
                break;
                
            case FUEL_LOSS:
                if(!ship.getCrew().contains(SHIELDS_OFFICER)) {
                    log.append("You don't have a shields officer, the effect will double\n");
                    eventFuelLoss();
                }
                return eventFuelLoss();
                
            case NO_EVENT:
                log.append("Nothing happens");
                break;
                
            case CREW_RESCUE:
                if(!ship.getCrew().contains(SHIELDS_OFFICER)) {
                    log.append("You don't have a shields officer, the effect will double\n");
                    eventCrewRescue();
                }
                eventCrewRescue();
                break;
        }
        return SUCCESS;
    }
    
    public int eventCrewDeath()
    {
        int index = ship.getCrew().size() - 1;
        
        if(ship.getCrew().size() > 1) {     
            log.append("A crew member died: ").append(ship.getCrew().get(index)).append("\n");
            ship.getCrew().remove(index);   // removes the last crew member from ArrayList
            
            return SUCCESS;
        }
        else if(ship.getCrew().size() == 1){
            log.append("Your last crew member died: ").append(ship.getCrew().get(0)).append("\n");
            ship.getCrew().remove(0);   // removes the last crew member from ArrayList
            log.append("You don't have crew to continue to play, the game will end...\n");
            
            return ERROR;
        }
        else {
            log.append("You don't have crew\n");
            return ERROR;
        }
    }
    
    public void eventSalvageShip()
    {
        String resourceFound = "";
        int numberOfResourcesFound;
        
        log.append("A salvage ship was found\n");
        
        int result = (int)(Math.random() * (4-1) + 1);
        
        switch(result) {
            case 1:
                resourceFound = RED_RESOURCE; break;
            case 2:
                resourceFound = BLUE_RESOURCE; break;
            case 3:
                resourceFound = GREEN_RESOURCE; break;
            case 4:
                resourceFound = BLACK_RESOURCE; break;
        }
        
        numberOfResourcesFound = (int)(Math.random() * (DICE_MAX-DICE_MIN) + DICE_MIN);
        
        log.append("You found ").append(numberOfResourcesFound).append(" units of ").append(resourceFound).append(" resource\n");
        
        ship.setResourceUnits(resourceFound, numberOfResourcesFound);
    }
    
    public void eventCargoLoss()
    {
        int result = (int)(Math.random() * (4-1) + 1);
        int unitsToRemove = (int)(Math.random() * (3-1) + 1);
        
        switch(result) {
            case 1:
                ship.setResourceUnits(RED_RESOURCE, ship.getCargo().getResources().get(0).getResourceAmount() - unitsToRemove); 
                log.append("You lost ").append(unitsToRemove).append(" units of ").append(RED_RESOURCE).append(" resource\n");
                break;
            case 2:
                ship.setResourceUnits(RED_RESOURCE, ship.getCargo().getResources().get(1).getResourceAmount() - unitsToRemove); 
                log.append("You lost ").append(unitsToRemove).append(" units of ").append(BLUE_RESOURCE).append(" resource\n");
                break;
            case 3:
                ship.setResourceUnits(RED_RESOURCE, ship.getCargo().getResources().get(2).getResourceAmount() - unitsToRemove); 
                log.append("You lost ").append(unitsToRemove).append(" units of ").append(GREEN_RESOURCE).append(" resource\n");
                break;
            case 4:
                ship.setResourceUnits(RED_RESOURCE, ship.getCargo().getResources().get(3).getResourceAmount() - unitsToRemove); 
                log.append("You lost ").append(unitsToRemove).append(" units of ").append(BLACK_RESOURCE).append(" resource\n");
                break;
        }
    }
    
    public int eventFuelLoss()
    {        
        if(ship.getFuel() < 1) {
            log.append("Your ship runned out of fuel\n");
            log.append("The game will end\n");
            return ERROR;
        }
        else if(ship.getFuel() == 1) {
            ship.setFuel(ship.getFuel() - 1);
            log.append("You accidentally used too much fuel and lost your last fuel cell\n");  
            log.append("Your ship runned out of fuel\n");
            log.append("The game will end\n");
            return ERROR;
        }
        else {
            ship.setFuel(ship.getFuel() - 1);
            log.append("You accidentally used too much fuel and lost 1 cell\n");
            log.append("Fuel available: ").append(ship.getFuel());

            return SUCCESS;
        }
    }
    
    public void eventCrewRescue()
    {                
        log.append("You found a ship with a lone crew member\n");
        
        if(ship.getCrew().size() < 6) {
            switch(ship.getCrew().size()) {
                case 1:
                    ship.getCrew().add(NAVIGATION_OFFICER);
                    log.append("You rescued a Navigation Officer\n");
                    break;
                case 2:
                    ship.getCrew().add(LANDING_PARTY_OFFICER);
                    log.append("You rescued aLanding Party Officer\n");
                    break;
                case 3:
                    ship.getCrew().add(SHIELDS_OFFICER);
                    log.append("You rescued a Shields Officer\n");
                    break;
                case 4:
                    ship.getCrew().add(WEAPONS_OFFICER);
                    log.append("You rescued a Weapons Officer\n");
                    break;
                case 5:
                    ship.getCrew().add(CARGO_HOLD_OFFICER);
                    log.append("You rescued a Cargo Hold Officer\n");
                    break;
            }
        }
        else
            log.append("You already have 6 members, nobody will be rescued\n");
    }
    
    public int d6Roll() {
        int random = new Random().nextInt(6);
        return random;
    }
    
    public Ship getShip() {
        return ship;
    }
    
    public Planet getPlanet() {
        return planet;
    }
    
    public Terrain getTerrain() {
        return terrain;
    }
    
    public Alien getAlien() {
        return terrain.getAlien();
    }
    
    public Resource getResource() {
        return terrain.getResource();
    }
    
    public String getLog() {
        return log.toString();
    }
}
