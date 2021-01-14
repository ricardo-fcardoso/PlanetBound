package planetbound;

import planetbound.logic.PlanetBound;
import planetbound.ui.gui.MainPane;
import planetbound.ui.text.TextUI;

public class Main {

    public static void main(String[] args) {
//        TextUI textUI = new TextUI(new PlanetBound());
//        textUI.run();  
        new MainPane().Launch();    // JavaFX UI
    }
    
}
