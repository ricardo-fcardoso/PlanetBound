package planetbound.logic.data;

public interface Constants {
    
    public static final int MILITARY_SHIP_WEAPONS_MAX = 18;
    public static final int MILITARY_SHIP_SHIELDS_MAX = 9;
    public static final int MILITARY_SHIP_FUEL_MAX = 35;
    
    public static final int MINING_SHIP_WEAPONS_MAX = 9;
    public static final int MINING_SHIP_SHIELDS_MAX = 18;
    public static final int MINING_SHIP_FUEL_MAX = 53;

    public static final int CREW_TAM = 6;
    
    public static final int PLANET_RAND_MIN = 0;
    public static final int PLANET_RAND_MAX = 100;
    
    public static final int DICE_MAX = 6;
    public static final int DICE_MIN = 1;
    
    public static final int MOVE_TO_NEXT_SPACE_LOCATION = 0;
    public static final int LAND_TO_MINE_RESOURCES = 1;
    public static final int DOCK_TO_SPACE_STATION = 2;
    public static final int DO_EVENT_FROM_REFERENCE_CARD = 3;
    public static final int MOVE_THROUGH_WORMHOLE = 4;
    public static final int RED_ORBIT_PLANET_USER_CHOICE = 5;
    public static final int WHITE_ORBIT_PLANET_USER_CHOICE = 6;
    
    public static final int SHIP_LOCATION_IS_NULL = 0;
    public static final int SHIP_LOCATION_IS_RED_DOT = 1;
    public static final int SHIP_LOCATION_IS_WHITE_PLANET = 2;
    public static final int SHIP_LOCATION_IS_RED_PLANET = 3;
    
    public static final int GAME_OVER = -1;
    public static final int GAME_WIN = 1;
    
    public static final int CREW_DEATH = 1;
    public static final int SALVAGE_SHIP = 2;
    public static final int CARGO_LOSS = 3;
    public static final int FUEL_LOSS = 4;
    public static final int NO_EVENT = 5;
    public static final int CREW_RESCUE = 6;
    
    public static final int SUCCESS = 0;
    public static final int ERROR = 1;
    public static final int RETURNED_TO_SHIP_WHITE_ORBIT = 2;
    public static final int RETURNED_TO_SHIP_RED_ORBIT = 3;
    public static final int DRONE_DESTROYED = -1;
    
    public static final String CAPTAIN = "Captain";
    public static final String NAVIGATION_OFFICER = "Navigation Officer";
    public static final String LANDING_PARTY_OFFICER = "Landing Party Officer";
    public static final String SHIELDS_OFFICER = "Shields Officer";
    public static final String WEAPONS_OFFICER = "Weapons Officer";
    public static final String CARGO_HOLD_OFFICER = "Cargo Hold Officer";
    
    public static final String RED_PLANET = "Red";
    public static final String BLUE_PLANET = "Blue";
    public static final String GREEN_PLANET = "Green";
    public static final String BLACK_PLANET = "Black";
    
    public static final String RED_RESOURCE = "Red Resource";
    public static final String BLUE_RESOURCE = "Blue Resource";
    public static final String GREEN_RESOURCE = "Green Resource";
    public static final String BLACK_RESOURCE = "Black Resource";
    public static final String ARTEFACT = "Artefact";
    
    public static final String RED_ALIEN = "Red Alien";
    public static final String BLACK_ALIEN = "Black Alien";
    public static final String BLUE_ALIEN = "Blue Alien";
    public static final String GREEN_ALIEN = "Green Alien";
    
    public static final String MILITARY_SHIP = "Military Ship";
    public static final String MINING_SHIP = "Mining Ship";
    
    public static final int W_LOWERCASE= 119;
    public static final int S_LOWERCASE = 115;
    public static final int A_LOWERCASE = 97;
    public static final int D_LOWERCASE = 100;
    
    public static final int W_UPPERCASE = 87;
    public static final int S_UPPERCASE = 83;
    public static final int A_UPPERCASE = 65;
    public static final int D_UPPERCASE = 68;
}