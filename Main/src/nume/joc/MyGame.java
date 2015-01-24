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

    @Override
    // metoda apelata de fiecare data cand suprafata desenabila va fi redimensionata
    public void resize(int width, int height) {

    }

    @Override
    // metoda apelata pana cand jocul este terminat
   public void render() {

   }

    @Override
    // metoda apelata cand jocul va intra in background
    public void pause() {

    }

    @Override
    // metoda apelata cand aplicatia revine in foreground
    public void resume() {

    }

    @Override
    // metoda apelata la terminarea jocului si inchiderea aplicatiei
    public void dispose() {

    }
}
