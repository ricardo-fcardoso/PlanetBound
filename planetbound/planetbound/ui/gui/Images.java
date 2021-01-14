package planetbound.ui.gui;

import java.util.HashMap;
import java.util.Map;
import javafx.scene.image.Image;

public class Images implements Constants {
    
    private static final Map<String, Image> imagens = new HashMap<>();
    
    static {
        imagens.put(DRONE, new Image(Resources.getResourceFile(PATH_DRONE)));
        imagens.put(SPACESHIP, new Image(Resources.getResourceFile(PATH_SPACESHIP)));
        imagens.put(STATION, new Image(Resources.getResourceFile(PATH_STATION)));

        imagens.put(MILITARY_SHIP, new Image(Resources.getResourceFile(PATH_MILITARY_SHIP)));
        imagens.put(MINING_SHIP, new Image(Resources.getResourceFile(PATH_MINING_SHIP)));
        
        imagens.put(RED_ALIEN, new Image(Resources.getResourceFile(PATH_RED_ALIEN)));
        imagens.put(BLUE_ALIEN, new Image(Resources.getResourceFile(PATH_BLUE_ALIEN)));
        imagens.put(GREEN_ALIEN, new Image(Resources.getResourceFile(PATH_GREEN_ALIEN)));
        imagens.put(BLACK_ALIEN, new Image(Resources.getResourceFile(PATH_BLACK_ALIEN)));
        
        imagens.put(RED_RESOURCE, new Image(Resources.getResourceFile(PATH_RED_RESOURCE)));
        imagens.put(BLUE_RESOURCE, new Image(Resources.getResourceFile(PATH_BLUE_RESOURCE)));
        imagens.put(GREEN_RESOURCE, new Image(Resources.getResourceFile(PATH_GREEN_RESOURCE)));
        imagens.put(BLACK_RESOURCE, new Image(Resources.getResourceFile(PATH_BLACK_RESOURCE)));

        imagens.put(RED_PLANET, new Image(Resources.getResourceFile(PATH_RED_PLANET)));
        imagens.put(GREEN_PLANET, new Image(Resources.getResourceFile(PATH_GREEN_PLANET)));
        imagens.put(BLUE_PLANET, new Image(Resources.getResourceFile(PATH_BLUE_PLANET)));
        imagens.put(BLACK_PLANET, new Image(Resources.getResourceFile(PATH_BLACK_PLANET)));
        
        imagens.put(MILITARY_FUEL, new Image(Resources.getResourceFile(PATH_MILITARY_FUEL)));
        imagens.put(MILITARY_CREW, new Image(Resources.getResourceFile(PATH_MILITARY_CREW)));
        imagens.put(MILITARY_CARGO, new Image(Resources.getResourceFile(PATH_MILITARY_CARGO)));
        imagens.put(MILITARY_SHIELDS_WEAPONS, new Image(Resources.getResourceFile(PATH_MILITARY_WEAPONS_SHIELD)));
        
        imagens.put(MINING_FUEL, new Image(Resources.getResourceFile(PATH_MINING_FUEL)));
        imagens.put(MINING_CREW, new Image(Resources.getResourceFile(PATH_MINING_CREW)));
        imagens.put(MINING_CARGO, new Image(Resources.getResourceFile(PATH_MINING_CARGO)));
        imagens.put(MINING_SHIELDS_WEAPONS, new Image(Resources.getResourceFile(PATH_MINING_WEAPONS_SHIELD)));
    }
    
    public static Image getImagem(String name) {
        return imagens.get(name);
    }
}
