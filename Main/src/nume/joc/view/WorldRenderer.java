package nume.joc.view;


import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
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


    private static final float CAMERA_WIDTH = 10f;

    private static final float CAMERA_HEIGHT = 7f;


    private World world;

    // pentru a putea privi camera dintr-o perspectiva ortografica
    private OrthographicCamera cam;

    // folosit pentru a declara primitive (de exemplu: dreptunghi )
    ShapeRenderer debugRenderer = new ShapeRenderer();

    /** Textures **/

    private Texture bobTexture;

    private Texture blockTexture;



    private SpriteBatch spriteBatch;

    private boolean debug = false;

    private int width;

    private int height;

    private float ppuX; // pixels per unit on the X axis

    private float ppuY; // pixels per unit on the Y axis

    public void setSize (int w, int h) {
             this.width = w;

        this.height = h;

        ppuX = (float)width / CAMERA_WIDTH;

        ppuY = (float)height / CAMERA_HEIGHT;

    }




    // constructorul  clasei
    public WorldRenderer(World world, boolean debug) {

        this.world = world;
        // camera are 10 unitati in lungime si 7 in inaltime (10 blocuri, respectiv 7)
        this.cam = new OrthographicCamera(CAMERA_WIDTH, CAMERA_HEIGHT);
        this.cam.position.set(CAMERA_WIDTH / 2f, CAMERA_HEIGHT / 2f, 0);
        this.cam.update();

        this.debug = debug;

        spriteBatch = new SpriteBatch();

        loadTextures();

    }

    private void loadTextures() {

        bobTexture = new  Texture(Gdx.files.internal("images/bob_01.png"));

        blockTexture = new Texture(Gdx.files.internal("images/block.png"));

    }

    public void render() {

        spriteBatch.begin();

        drawBlocks();

        drawActor();

        spriteBatch.end();

        if (debug)

        drawDebug();

    }



    private void drawBlocks() {

        for (Object blockObj : world.getBlocks()) {

            Block block = (Block)blockObj ;
            spriteBatch.draw(blockTexture, block.getPosition().x * ppuX, block.getPosition().y * ppuY, Block.SIZE * ppuX, Block.SIZE * ppuY);

        }

    }

    private void drawActor() {

        Actor actor = world.getActor();

        spriteBatch.draw(bobTexture, actor.getPosition().x * ppuX, actor.getPosition().y * ppuY, Actor.SIZE * ppuX, Actor.SIZE * ppuY);

    }

    private void drawDebug() {

        // render blocks

        debugRenderer.setProjectionMatrix(cam.combined);

        debugRenderer.begin(ShapeType.Line);

        for (Object blockObj : world.getBlocks()) {

            Block block = (Block)blockObj ;
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
