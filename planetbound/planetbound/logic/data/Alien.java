package planetbound.logic.data;

import java.io.Serializable;
import java.util.Random;

public class Alien implements Constants, Serializable {
    
    private int currentLine;
    private int currentColumn;
    private String alienColor;
    private boolean alive;
    
    public Alien()
    {
        this.currentLine = 0;
        this.currentColumn = 0;
        this.alive = true;
        
        /* DRAW RANDOMLY AN ALIEN COLOR */
        int random = new Random().nextInt(4);
        switch(random) {
            case 0:
                this.alienColor = RED_ALIEN; break;
            case 1:
                this.alienColor = BLUE_ALIEN; break;
            case 2:
                this.alienColor = GREEN_ALIEN; break;
            case 3:
                this.alienColor = BLACK_ALIEN; break;
        }
    }
    
    public int getCurrentLine() {
        return currentLine;
    }
    
    public int getCurrentColumn() {
        return currentColumn;
    }
    
    public String getAlienColor() {
        return alienColor;
    }
    
    public boolean getAlienAlive() {
        return alive;
    }
    
    public void setLine(int line) {
        this.currentLine = line;
    }
    
    public void setColumn(int column) {
        this.currentColumn = column;
    }
    
    public void setAlienColor(String alienColor) {
        this.alienColor = alienColor;
    }
    
    public void setAlienAlive(boolean alive) {
        this.alive = alive;
    }
}
