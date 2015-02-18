package forDeskrop;

import com.badlogic.gdx.backends.lwjgl.LwjglApplication;
import com.badlogic.gdx.backends.lwjgl.LwjglApplicationConfiguration;
import nume.joc.MyGame;
//import com.badlogic.gdx.tools.imagepacker.TexturePacker2;


/**
 * Created by roxy on 1/23/2015.
 */
public class DesktopStarter {
    public static void main(String[] args) {


        LwjglApplicationConfiguration cfg = new LwjglApplicationConfiguration();
        cfg.title = "MyGame";
        cfg.width=800;
        //TexturePacker2.process("/Android/assets/images/", "/Android/assets/images/texture/", "textures.pack");
        new LwjglApplication(new MyGame(), cfg);
    }
}