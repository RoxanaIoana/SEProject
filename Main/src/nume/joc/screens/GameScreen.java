package nume.joc.screens;

import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import nume.joc.model.World;
import nume.joc.view.WorldRenderer;

//import javax.microedition.khronos.opengles.GL10;


/**
 * Created by roxy on 1/23/2015.
 */
public class GameScreen implements Screen {

    private World world;

    private WorldRenderer renderer;


    @Override
    // metoda apelata cand acest ecran este activ
    public void show() {

        world = new World();
        renderer = new WorldRenderer(world,true);

    }

    @Override
    public void render(float delta) {

        // sterge ecranul
        Gdx.gl.glClearColor(0.1f, 0.1f, 0.1f, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        renderer.render();


    }

    @Override
    public void resize(int width, int height) {

        renderer.setSize(width, height);

    }

    @Override
    public void pause() {

    }

    @Override
    public void resume() {

    }

    @Override
    // metoda apelata cand alt ecran devine activ
    public void hide() {

    }

    @Override
    public void dispose() {

    }
}
