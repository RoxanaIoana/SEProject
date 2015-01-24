package nume.joc.view;


import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer.ShapeType;
import com.badlogic.gdx.math.Rectangle;
import nume.joc.model.Actor;
import nume.joc.model.Block;
import nume.joc.model.World;


/**
 * Created by roxy on 1/24/2015.
 */
public class WorldRenderer {


    private World world;

    // pentru a putea privi camera dintr-o perspectiva ortografica
    private OrthographicCamera cam;

    // folosit pentru a declara primitive (de exemplu: dreptunghi )
    ShapeRenderer debugRenderer = new ShapeRenderer();

    // constructorul  clasei
    public WorldRenderer(World world) {

        this.world = world;
        // camera are 10 unitati in lungime si 7 in inaltime (10 blocuri, respectiv 7)
        this.cam = new OrthographicCamera(10, 7);
        this.cam.position.set(5, 3.5f, 0);
        this.cam.update();

    }
    public void render() {
// render blocks
        debugRenderer.setProjectionMatrix(cam.combined);

        debugRenderer.begin(ShapeType.Line);

        for (Object blockObj : world.getBlocks()) {
            Block block = (Block) blockObj;
            Rectangle rect = block.getBounds();

            float x1 = block.getPosition().x + rect.x;

            float y1 = block.getPosition().y + rect.y;

            debugRenderer.setColor(new Color(1, 0, 0, 1));

            debugRenderer.rect(x1, y1, rect.width, rect.height);

        }

        // render Bob

        Actor actor = world.getActor();

        Rectangle rect = actor.getBounds();

        float x1 = actor.getPosition().x + rect.x;

        float y1 = actor.getPosition().y + rect.y;

        debugRenderer.setColor(new Color(0, 1, 0, 1));

        debugRenderer.rect(x1, y1, rect.width, rect.height);

        debugRenderer.end();

    }


}
