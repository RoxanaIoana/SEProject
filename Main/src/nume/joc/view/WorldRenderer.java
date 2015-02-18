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
import nume.joc.model.Actor.State;

import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
/**
 * Created by roxy on 1/24/2015.
 */
public class WorldRenderer {


    private static final float CAMERA_WIDTH = 10f;

    private static final float CAMERA_HEIGHT = 7f;

    private static final float RUNNING_FRAME_DURATION = 0.06f;

    private World world;

    // pentru a putea privi camera dintr-o perspectiva ortografica
    private OrthographicCamera cam;

    // folosit pentru a declara primitive (de exemplu: dreptunghi )
    ShapeRenderer debugRenderer = new ShapeRenderer();

    /* Texturi si regiuni*/
    private TextureRegion actorIdleLeft;
    private TextureRegion actorIdleRight;
    private TextureRegion blockTexture;
    private TextureRegion actorFrame;
    private TextureRegion actorJumpLeft;
    private TextureRegion actorFallLeft;
    private TextureRegion actorJumpRight;
    private TextureRegion actorFallRight;

    /* Animations */
    private Animation walkLeftAnimation;
    private Animation walkRightAnimation;



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
        spriteBatch.getProjectionMatrix().setToOrtho2D(0, 0, this.width, this.height);
        ppuY = (float)height / CAMERA_HEIGHT;

    }
    public boolean isDebug() {
        return debug;
    }
    public void setDebug(boolean debug) {
        this.debug = debug;
    }
    // constructorul  clasei
    public WorldRenderer(World world, boolean debug) {

        this.world = world;
        // camera are 10 unitati in lungime si 7 in inaltime (10 blocuri, respectiv 7)
      //  this.cam = new OrthographicCamera(CAMERA_WIDTH, CAMERA_HEIGHT);
       // this.cam.position.set(CAMERA_WIDTH / 2f, CAMERA_HEIGHT / 2f, 0);
       // cam.setToOrtho(false);
        //this.cam.update();
        cam = new OrthographicCamera();
        cam.setToOrtho(false);
        cam.update();

        this.debug = debug;

        spriteBatch = new SpriteBatch();

        loadTextures();

    }

    private void loadTextures() {
        //atlasul creat in textures
        //exista niste regiuni fiecare regiune reprezinta o imagine bob-0X
        TextureAtlas atlas = new TextureAtlas(Gdx.files.internal("images/textures/textures.pack"));
        //img cu actor in poz stanga
        actorIdleLeft = atlas.findRegion("bob-01");
        actorIdleRight = new TextureRegion(actorIdleLeft);
        actorIdleRight.flip(true, false);
        //regiunea pt bloc
        blockTexture = atlas.findRegion("block");
        //atunci cand actorul merge spre stanga se vor rula imag si se va forma o aminatie
        TextureRegion[] walkLeftFrames = new TextureRegion[5];
        for (int i = 0; i < 5; i++) {
            walkLeftFrames[i] = atlas.findRegion("bob-0" + (i + 2));
        }

        walkLeftAnimation = new Animation(RUNNING_FRAME_DURATION, walkLeftFrames);

        TextureRegion[] walkRightFrames = new TextureRegion[5];

        for (int i = 0; i < 5; i++) {
            walkRightFrames[i] = new TextureRegion(walkLeftFrames[i]);
            walkRightFrames[i].flip(true, false);
        }
        //pt jump se va pune regiunea cu imaginea bob-up
        walkRightAnimation = new Animation(RUNNING_FRAME_DURATION, walkRightFrames);
        actorJumpLeft = atlas.findRegion("bob-up");
        //la dreapta sa genereaza o noua regiune folosindune de regiunea formata pe stanga
        actorJumpRight = new TextureRegion(actorJumpLeft);
        actorJumpRight.flip(true, false);
        actorFallLeft = atlas.findRegion("bob-down");
        actorFallRight = new TextureRegion(actorFallLeft);
        actorFallRight.flip(true, false);
    }

    public void render() {

        Actor actor = world.getActor();
        spriteBatch.setProjectionMatrix(cam.combined);
        cam.position.x = actor.getPosition().x+300;
        cam.update();
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
        //gasirea frameului actorului daca este IDLELeft sau IdleRight
        actorFrame = actor.isFacingLeft() ? actorIdleLeft : actorIdleRight;
        //se cauata pozitia actorului in fucntie de KeyFrame (left sau right walking)
        if(actor.getState().equals(State.WALKING)) {
            actorFrame = actor.isFacingLeft() ? walkLeftAnimation.getKeyFrame(actor.getStateTime(), true) : walkRightAnimation.getKeyFrame(actor.getStateTime(), true);
            //SAU jumping left sau right
        } else if (actor.getState().equals(State.JUMPING)) {
            if (actor.getVelocity().y > 0) {
                actorFrame = actor.isFacingLeft() ? actorJumpLeft : actorJumpRight;
            } else {
                actorFrame = actor.isFacingLeft() ? actorFallLeft : actorFallRight;
            }
        }
        //se genereaza textura -imag dupa ce am gasit pozitia(actorFrame)
        spriteBatch.draw(actorFrame, actor.getPosition().x * ppuX, actor.getPosition().y * ppuY, Actor.SIZE * ppuX, Actor.SIZE * ppuY);
    }
//este activ doar la tasta D(debug)
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

        // render Actor

        Actor actor = world.getActor();

        Rectangle rect = actor.getBounds();

        float x1 = actor.getPosition().x + rect.x;

        float y1 = actor.getPosition().y + rect.y;
        //setam culoare verde pt chemarul actorului
        debugRenderer.setColor(new Color(0, 1, 0, 1));

        debugRenderer.rect(x1, y1, rect.width, rect.height);

        debugRenderer.end();

    }



}
