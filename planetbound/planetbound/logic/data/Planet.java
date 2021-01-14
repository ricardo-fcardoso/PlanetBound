package planetbound.logic.data;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class Planet implements Constants, Serializable {
    
    private String color;
    private boolean hasStation;
    private final List<Resource> resources;
    
    public Planet()
    {
        int result = (int)(Math.random() * (4-1) + 1);
        
        this.resources = new ArrayList<>();
        
        switch(result) {
            case 1:
                this.color = RED_PLANET;
                this.resources.add(new Resource(RED_RESOURCE, 6));
                this.resources.add(new Resource(BLUE_RESOURCE, 6));
                break;
                
            case 2:
                this.color = BLACK_PLANET;
                this.resources.add(new Resource(BLACK_RESOURCE, 6));
                this.resources.add(new Resource(BLUE_RESOURCE, 6));
                break;
                
            case 3:
                this.color = GREEN_PLANET;
                this.resources.add(new Resource(RED_RESOURCE, 6));
                this.resources.add(new Resource(GREEN_RESOURCE, 6));
                break;
            
            case 4:
                this.color = BLUE_PLANET;
                this.resources.add(new Resource(BLACK_RESOURCE, 6));
                this.resources.add(new Resource(GREEN_RESOURCE, 6));
                this.resources.add(new Resource(BLUE_RESOURCE, 6));
                this.resources.add(new Resource(ARTEFACT, 1));
                break;
        }
    }
    
    public String getPlanetColor()
    {
        return color;
    }
    
    public List<Resource> getPlanetResources()
    {
        return resources;
    }
    
    public boolean getHasStation()
    {
        return hasStation;
    }
    
    public final void setPlanetColor(String planetColor)
    {
        this.color = planetColor;
    }
    
    public final void setHasStation(boolean hasStation)
    {
        this.hasStation = hasStation;
    }
    
    @Override
    public final String toString() {
        String os = "";
        
        os += "\nPlanet created successfully: " +
                "\n > Type: " + color;
        
        if(hasStation)
            os += "\n > Station: Has station";
        else
            os += "\n > Station: Has no station";
        
        os += "\n > Number of explorations available: " + resources.size();
        
        return os;
    }
    
}
