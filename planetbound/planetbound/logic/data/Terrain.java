package planetbound.logic.data;

import java.io.Serializable;

public class Terrain implements Serializable {
    
    private final char[][] terrain;
    private Alien alien;
    private final Resource resource;
    private int itToNextAlien;
    
    public Terrain()
    {
        this.terrain = new char[6][6];
        this.alien = new Alien();
        this.resource = new Resource();
        this.itToNextAlien = 0;
    }
    
    public char[][] getTerrain() {
        return terrain;
    }
    
    public int getAlienCurrentLine() {
        return alien.getCurrentLine();
    }
    
    public int getAlienCurrentColumn() {
        return alien.getCurrentColumn();
    }
    
    public Alien getAlien() {
        return alien;
    }
    
    public int getItToNextAlien() {
        return itToNextAlien;
    }
    
    public Resource getResource() {
        return resource;
    }
    
    public int getResourceCurrentLine() {
        return resource.getCurrentLineOnTerrain();
    }
    
    public int getResourceCurrentColumn() {
        return resource.getCurrentColumnOnTerrain();
    }
    
    public void createNewAlien() {
        alien = new Alien();
    }
    
    public void setAlienLine(int line) {
        alien.setLine(line);
    }
    
    public void setAlienColumn(int column) {
        alien.setColumn(column);
    }
    
    public void setItToNextAlien(int itToNextAlien) {
        this.itToNextAlien = itToNextAlien;
    }
    
    public void setResourceLine(int line) {
        resource.setLine(line);
    }
    
    public void setResourceColumn(int column) {
        resource.setColumn(column);
    }
}
