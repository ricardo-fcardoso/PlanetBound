package planetbound.logic.data;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class Cargo implements Constants, Serializable {
    
    private int level;
    private final List<Resource> resources;
    
    public Cargo()
    {
        this.level = 0;
        this.resources = new ArrayList<>();
        
        this.resources.add(new Resource(RED_RESOURCE, 0));
        this.resources.add(new Resource(BLUE_RESOURCE, 0));
        this.resources.add(new Resource(GREEN_RESOURCE, 0));
        this.resources.add(new Resource(BLACK_RESOURCE, 0));
        this.resources.add(new Resource(ARTEFACT, 0));
    }
    
    public int getLevel() {
        return level;
    }
    
    public List<Resource> getResources() {
        return resources;
    }
    
    public void setLevel(int num) {
        this.level = num;
    }
    
    public void setResourceUnits(String resourceName, int units) 
    {        
        switch(resourceName) {
            case RED_RESOURCE:
                for(Resource resource : resources)
                    if(resource.getResourceColor().equals(RED_RESOURCE))
                        resource.setAmount(units);
                break;
                    
            case BLUE_RESOURCE:
                for(Resource resource : resources)
                    if(resource.getResourceColor().equals(BLUE_RESOURCE))
                        resource.setAmount(units);
                break;
                
            case GREEN_RESOURCE:
                for(Resource resource : resources)
                    if(resource.getResourceColor().equals(GREEN_RESOURCE))
                        resource.setAmount(units);
                break;
                
            case BLACK_RESOURCE:
                for(Resource resource : resources)
                    if(resource.getResourceColor().equals(BLACK_RESOURCE))
                        resource.setAmount(units);
                break;
                
            case ARTEFACT:
                for(Resource resource: resources)
                    if(resource.getResourceColor().equals(ARTEFACT))
                        resource.setAmount(units);
        }
    }
    
    public int getResourceUnits(String resource)
    {
        for(Resource res : resources) {
            if(res.getResourceColor().equals(resource))
                return res.getResourceAmount();
        }
        
        return 0;
    }
}
