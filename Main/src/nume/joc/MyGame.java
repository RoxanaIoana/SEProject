package nume.joc;

import com.badlogic.gdx.ApplicationListener;
import com.badlogic.gdx.Game;
import nume.joc.screens.GameScreen;


/**
 * Created by roxy on 1/23/2015.
 */
public class MyGame extends Game{



    @Override
    // prima metoda care va fi apelata
    public void create() {

        setScreen(new GameScreen());

    }


}
