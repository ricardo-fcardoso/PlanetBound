package planetbound.logic.data;

import java.io.Serializable;

public class Drone implements Serializable {
    
    private int armor;
    private int initialLine;
    private int initialColumn;
    private int currentLine;
    private int currentColumn;
    private String currentResource;
    
    public Drone()
    {
        this.armor = 6;
        this.currentLine = 0;
        this.currentColumn = 0;
        this.initialLine = 0;
        this.initialColumn = 0;
        this.currentResource = null;
    }
    
    public int getCurrentArmor() {
        return armor;
    }
    
    public int getCurrentLine() {
        return currentLine;
    }
    
    public int getInitialLine() {
        return initialLine;
    }
    
    public int getInitialColumn() {
        return initialColumn;
    }
    
    public int getCurrentColumn() {
        return currentColumn;
    }
    
    public String getCurrentResource() {
        return currentResource;
    }
    
    public void setInitialLine(int initialLine) {
        this.initialLine = initialLine;
    }
    
    public void setInitialColumn(int initialColumn) {
        this.initialColumn = initialColumn;
    }
    
    public void setLine(int line) {
        this.currentLine = line;
    }
    
    public void setColumn(int column) {
        this.currentColumn = column;
    }
    
    public void setArmor(int armor) {
        this.armor = armor;
    }
    
    public void setCurrentResource(String currentResource) {
        this.currentResource = currentResource;
    }
}
