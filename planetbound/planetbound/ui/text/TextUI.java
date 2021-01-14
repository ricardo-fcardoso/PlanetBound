package planetbound.ui.text;

import planetbound.logic.PlanetBound;
import java.util.Scanner;
import planetbound.logic.states.AwaitBeginning;
import planetbound.logic.states.AwaitChoiceOnRedOrbit;
import planetbound.logic.states.AwaitChoiceOnWhiteOrbit;
import planetbound.logic.states.AwaitDroneMovement;
import planetbound.logic.states.AwaitEvent;
import planetbound.logic.states.AwaitGameOver;
import planetbound.logic.states.AwaitShipSelection;
import planetbound.logic.states.AwaitToDockToSpaceStation;
import planetbound.logic.states.AwaitToMove;
import planetbound.logic.states.IStates;

public class TextUI {
    
    PlanetBound game;
    Scanner sc;
    private boolean quit = false;
    
    public TextUI(PlanetBound game) 
    {
        this.game = game;
        this.sc = new Scanner(System.in);
    }
    
    public void uiAwaitBeginning()
    {
        int option;
        
        System.out.println("\n=== AWAITING FOR THE BEGINNING OF THE GAME ===\n");
        
        while(true) {
            do {
                System.out.println("0. Quit");
                System.out.println("1. Start");
                System.out.println();
                System.out.print("> ");
                
                option = sc.nextInt();
                
            }while(option < 0 || option > 1);
            
            switch(option) {
                case 0:
                    quit = true;
                    return;
                    
                case 1: 
                    System.out.println("\nGame started");
                    game.startGame();
                    return;
                    
                default:
                    return;
            } // switch
        } // while
    } // uiAwaitBeginning
    
    public void uiAwaitShipSelection() 
    {
        int option;
        
        do {
            System.out.println("\n=== AWAITING FOR THE SHIP SELECTION ===");
            System.out.println();
            System.out.println("Please select your ship: ");
            System.out.println(" 1. Military Ship");
            System.out.println(" 2. Mining Ship");
            System.out.println();
            System.out.print("> ");

            option = sc.nextInt();

        }while(option < 1 || option > 2);

        game.selectShipType(option);
    }
    
    public void uiAwaitToMove() 
    {
        int option;
        
        while(true) {
            do {
                System.out.println("\n=== AWAITING TO MOVE ===");
                System.out.println();
                System.out.println("Please select an option: ");
                System.out.println(" 0. Quit");
                System.out.println(" 1. Move to next Space Location");
                System.out.println();
                System.out.print("> ");
                
                option = sc.nextInt();
                
            }while(option < 0 || option > 1);
            
            switch(option) {
                case 0:
                    quit = true;
                    return;
                    
                case 1:
                    game.moveToNextSpaceLocation();
                    
                default:
                    return;
            } // switch
        } // while
    }
    
    public void uiAwaitEvent()
    {
        int option, event;
        
        do {
            System.out.println("\nPlease select an option: ");
            System.out.println(" 1. Specific event");
            System.out.println(" 2. Random event");
            System.out.println();
            System.out.print("> ");

            option = sc.nextInt();

        }while(option < 1 || option > 2);

        switch(option) {
            case 1: {
                do {
                    System.out.println("\nPlease select an event: ");
                    System.out.println(" 1. Crew death");
                    System.out.println(" 2. Salvage ship");
                    System.out.println(" 3. Cargo loss");
                    System.out.println(" 4. Fuel loss");
                    System.out.println(" 5. No event");
                    System.out.println(" 6. Crew Rescue");
                    System.out.println();
                    System.out.print("> ");

                    event = sc.nextInt();
                    System.out.println();

                    game.doSpecificEventFromReferenceCard(event);

                }while(event < 1 || event > 6);
            }

            case 2:
                game.doEventFromReferenceCard();
        }
    }
    
    public void uiAwaitDroneMovement()
    {      
        int keyCode;
        String input;
        System.out.print("\n> ");
        
        input = sc.next();
        keyCode = input.charAt(0);
        System.out.println();
        
        game.moveDrone(keyCode);
    }
    
    public void uiAwaitChoiceOnWhiteOrbit()
    {
        int option;
        
        do {
            System.out.println("Select an option: ");
            System.out.println(" 1. Leave the orbit");
            System.out.println(" 2. Land to mine resources");
            System.out.println(" 3. Convert resources into supplies");
            System.out.println();
            System.out.print(" > ");

            option = sc.nextInt();

            switch(option) {
                case 1:
                    game.moveToNextSpaceLocation(); break;
                case 2:
                    game.landToMineResources(); break;
                case 3:
                    game.convertResourcesIntoSupplies(option); break;
            }
        }while(option < 1 || option > 2);
    }
    
    public void uiAwaitToDockToSpaceStation() 
    {
        int option, resourceOrigin, resourceFinal;
        
        do {
            System.out.println("\nSelect an option: ");
            System.out.println(" 0. Leave the space station");
            System.out.println(" 1. Upgrade cargo (requires 3 of each resource)");
            System.out.println(" 2. Convert a single resource into another resource");
            System.out.println(" 3. Hire a lost crew member (requires 1 of each resource)");
            System.out.println(" 4. Service your landing craft to its full armor strength (requires 1 of each resource)");
            System.out.println(" 5. Buy a new mining drone (requires 3 of each resource");
            System.out.println();
            System.out.print(" > ");

            option = sc.nextInt();

            switch(option) {
                case 0:
                    game.moveToNextSpaceLocation(); break;
                case 1:
                    game.upgradeCargo(); break;
                case 2:
                    do {
                        System.out.println("\nSelect the resource you want to convert: ");
                        System.out.println(" 1. Red resource");
                        System.out.println(" 2. Blue resource");
                        System.out.println(" 3. Green resource");
                        System.out.println(" 4. Black resource");
                        System.out.println();
                        System.out.print(" > ");
                        
                        resourceOrigin = sc.nextInt();
                        
                        do {
                            System.out.println("\nSelect the resource you want to convert to: ");
                            System.out.println(" 1. Red resource");
                            System.out.println(" 2. Blue resource");
                            System.out.println(" 3. Green resource");
                            System.out.println(" 4. Black resource");
                            System.out.println();
                            System.out.print(" > ");

                            resourceFinal = sc.nextInt();
                            
                            game.convertSingleResource(resourceOrigin, resourceFinal); break;
                                
                        }while(option < 1 || option > 4);
                    }while(option < 1 || option > 4);
                   
                    break;
                    
                case 3:
                    game.hireALostCrewMember(); break;
                    
                case 4:
                    game.repairDrone(); break;
                case 5:
                    game.buyDrone(); break;
            }
        }while(option < 0 || option > 6);
    }
    
    public void uiAwaitChoiceOnRedOrbit()
    {
        int option;
        
        do {
            System.out.println("Select an option: ");
            System.out.println(" 1. Leave the orbit");
            System.out.println(" 2. Land to mine resources");
            System.out.println(" 3. Dock to the space station");
            System.out.println();
            System.out.print(" > ");

            option = sc.nextInt();

            switch(option) {
                case 1:
                    game.moveToNextSpaceLocation(); break;
                case 2:
                    game.landToMineResources(); break;
                case 3:
                    game.dockToSpaceStation(); break;
            }
        }while(option < 1 || option > 3);
    }
    
    public void uiAwaitGameOver()
    {
        int option;
        
        System.out.println("\n=== GAME OVER ===\n");
        
        do {
            System.out.println("\nPlease select an option: ");
            System.out.println(" 0. Quit");
            System.out.println(" 1. Play again");
            System.out.println();
            System.out.print("> ");

            option = sc.nextInt();
        }while(option < 0 || option > 1);
        
        switch(option) {
            case 0:
                quit = true;
                break;
            case 1:
                game.replay();
                break;
        }
    }
    
    public void run()
    {
        while(!quit) {
            IStates state = game.getState();
            
            if(state instanceof AwaitBeginning) {
                uiAwaitBeginning();
            } else if(state instanceof AwaitShipSelection) {
                uiAwaitShipSelection();
            } else if(state instanceof AwaitToMove) {
                uiAwaitToMove();
            } else if(state instanceof AwaitEvent) {
                uiAwaitEvent();
            } else if(state instanceof AwaitDroneMovement) {
                uiAwaitDroneMovement();
            } else if(state instanceof AwaitChoiceOnRedOrbit) {
                uiAwaitChoiceOnRedOrbit();
            } else if(state instanceof AwaitChoiceOnWhiteOrbit) {
                uiAwaitChoiceOnWhiteOrbit();
            } else if(state instanceof AwaitToDockToSpaceStation) {
                uiAwaitToDockToSpaceStation();
            } else if(state instanceof AwaitGameOver) {
                uiAwaitGameOver();
            }
        }
    }
}
