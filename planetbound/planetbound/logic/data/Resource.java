package planetbound.logic.data;

import java.io.Serializable;

public class Resource implements Serializable {
    
    private String color;
    private int amount;
    private int currentLine;
    private int currentColumn;
    private boolean nextToBeMined;
    
    public Resource()
    {
        this.amount = 6;
        this.currentLine = 0;
        this.currentColumn = 0;
        this.nextToBeMined = false;
    }
    
    public Resource(String resourceColor, int amount)
    {
        this.color = resourceColor;
        this.amount = amount;
        this.currentLine = 0;
        this.currentColumn = 0;
    }
    
    public String getResourceColor() {
        return this.color;
    }
    
    public int getResourceAmount() {
        return this.amount;
    }
    
    public int getCurrentLineOnTerrain() {
        return this.currentLine;
    }
    
    public int getCurrentColumnOnTerrain() {
        return this.currentColumn;
    }
    
    public boolean getNextToBeMined() {
        return this.nextToBeMined;
    }
    
    public void setLine(int line) {
        this.currentLine = line;
    }
    
    public void setColumn(int column) {
        this.currentColumn = column;
    }
    
    public void setNextToBeMined(boolean state) {
        this.nextToBeMined = state;
    }
    
    public void setAmount(int amount) {
        this.amount = amount;
        if(this.amount < 0)
            this.amount = 0;
    }
}
