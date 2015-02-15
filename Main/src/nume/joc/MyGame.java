package nume.joc;

import com.badlogic.gdx.ApplicationListener;
import com.badlogic.gdx.Game;
import nume.joc.screens.GameScreen;
import nume.joc.screens.MenuScreen;

/**
 * Created by roxy on 1/23/2015.
 */
public class MyGame extends Game{


    MenuScreen menuScreen;
    GameScreen gameScreen;


    @Override
    // prima metoda care va fi apelata
    public void create() {
        menuScreen = new MenuScreen(this);
        gameScreen = new GameScreen(this);
        setScreen(menuScreen);
    }

}
