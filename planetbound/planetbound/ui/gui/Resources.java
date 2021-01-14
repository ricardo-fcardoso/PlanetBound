package planetbound.ui.gui;

import java.io.InputStream;
import java.net.URL;

public class Resources {
    
    public static InputStream getResourceFile(String name){
        InputStream in = Resources.class.getResourceAsStream(name);
        return in; 
    }
    
//    public static URL getFileURL(String name){
//        /* Obtem o recurso nomeado a partir da localizacao de Resources.class */
//        return Resources.class.getResource(name);
//    }
}
