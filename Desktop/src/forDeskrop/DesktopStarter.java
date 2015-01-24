package forDeskrop;

import com.badlogic.gdx.backends.lwjgl.LwjglApplication;
import com.badlogic.gdx.backends.lwjgl.LwjglApplicationConfiguration;
import nume.joc.MyGame;

/**
 * Created by roxy on 1/23/2015.
 */
public class DesktopStarter {
    public static void main(String[] args) {
<<<<<<< HEAD
       
=======
        LwjglApplicationConfiguration cfg = new LwjglApplicationConfiguration();
        cfg.title = "MyGame";
        cfg.useGL30 = true;
        cfg.width = 480;
        cfg.height = 320;
>>>>>>> origin/master

        new LwjglApplication(new MyGame(), cfg);
    }
}