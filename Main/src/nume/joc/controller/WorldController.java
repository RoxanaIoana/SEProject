package nume.joc.controller;

/**
 * Created by uioana on 2/14/2015.
 */

import java.util.HashMap;
import java.util.Map;

import com.badlogic.gdx.math.Vector2;
import nume.joc.model.Actor;
import nume.joc.model.Block;
import nume.joc.model.Actor.State;
import nume.joc.model.World;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.utils.Pool;
import com.badlogic.gdx.utils.Array;

public class WorldController {

    //enumeratia(strunc) Kyes care contine cheile folosite in joc
    enum Keys {
        LEFT, RIGHT, JUMP, FIRE
    }

    private static final long LONG_JUMP_PRESS 	= 150l;
    private static final float ACCELERATION 	= 20f;
    private static final float GRAVITY 			= -20f;
    private static final float MAX_JUMP_SPEED	= 4f;
    private static final float DAMP 			= 0.90f;
    private static final float MAX_VEL 			= 4f;
    private static final float WIDTH = 20f;

    private World 	world;
    private Actor 	actor;
    private long	jumpPressedTime;
    private boolean jumpingPressed;
    private boolean grounded = false;

    // This is the rectangle pool used in collision detection
    // Good to avoid instantiation each frame
    private Pool<Rectangle> rectPool = new Pool<Rectangle>() {
        @Override
        protected Rectangle newObject() {
            return new Rectangle();
        }
    };

    static Map<Keys, Boolean> keys = new HashMap<WorldController.Keys, Boolean>();
    static {
        keys.put(Keys.LEFT, false);
        keys.put(Keys.RIGHT, false);
        keys.put(Keys.JUMP, false);
        keys.put(Keys.FIRE, false);
    };
    // Blocks that Bob can collide with any given frame
    private Array<Block> collidable = new Array<Block>();


    public WorldController(World world) {
        this.world = world;
        this.actor = world.getActor();

    }

    // ** Key presses and touches **************** //

    public void leftPressed() {
        keys.get(keys.put(Keys.LEFT, true));
    }

    public void rightPressed() {
        keys.get(keys.put(Keys.RIGHT, true));
    }

    public void jumpPressed() {
        keys.get(keys.put(Keys.JUMP, true));
    }

    public void firePressed() {
        keys.get(keys.put(Keys.FIRE, false));
    }

    public void leftReleased() {
        keys.get(keys.put(Keys.LEFT, false));
    }

    public void rightReleased() {
        keys.get(keys.put(Keys.RIGHT, false));
    }

    public void jumpReleased() {
        keys.get(keys.put(Keys.JUMP, false));
        jumpingPressed = false;
    }

    public void fireReleased() {
        keys.get(keys.put(Keys.FIRE, false));
    }

    /** The main update method **/
    public void update(float delta) {
        processInput();
       if (grounded && actor.getState().equals(State.JUMPING)) {
           actor.setState(State.IDLE);
       }


        actor.getAcceleration().y = GRAVITY;
        actor.getAcceleration().scl(delta);
        actor.getVelocity().add(actor.getAcceleration().x, actor.getAcceleration().y);


        // checking collisions with the surrounding blocks depending on Bob's velocity
        checkCollisionWithBlocks(delta);

        // apply damping to halt Bob nicely
        actor.getVelocity().x *= DAMP;

        if (actor.getVelocity().x > MAX_VEL) {
            actor.getVelocity().x = MAX_VEL;
        }
        if (actor.getVelocity().x < -MAX_VEL) {
            actor.getVelocity().x = -MAX_VEL;
        }

        actor.update(delta);
        if (actor.getPosition().y < 0) {
            actor.getPosition().y = 0f;
            actor.setPosition(actor.getPosition());
            if (actor.getState().equals(State.JUMPING)) {
                actor.setState(State.IDLE);
            }
        }
        if (actor.getPosition().x < 0) {
            actor.getPosition().x = 0;
            actor.setPosition(actor.getPosition());
            if (!actor.getState().equals(State.JUMPING)) {
                actor.setState(State.IDLE);
            }
        }
        if (actor.getPosition().x > WIDTH - actor.getBounds().width ) {
            actor.getPosition().x = WIDTH - actor.getBounds().width;
            actor.setPosition(actor.getPosition());
            if (!actor.getState().equals(State.JUMPING)) {
                actor.setState(State.IDLE);
            }
        }

    }

    /** Collision checking **/
    private void checkCollisionWithBlocks(float delta) {
        // scale velocity to frame units
        actor.getVelocity().scl(delta);

        // Obtain the rectangle from the pool instead of instantiating it
        Rectangle bobRect = rectPool.obtain();
        // set the rectangle to bob's bounding box
        bobRect.set(actor.getBounds().x, actor.getBounds().y, actor.getBounds().width, actor.getBounds().height);

        // we first check the movement on the horizontal X axis
        int startX, endX;
        int startY = (int) actor.getBounds().y;
        int endY = (int) (actor.getBounds().y + actor.getBounds().height);
        // if Bob is heading left then we check if he collides with the block on his left
        // we check the block on his right otherwise
        if (actor.getVelocity().x > 0) {
            startX = endX = (int)(actor.getPosition().x + actor.getBounds().width + actor.getVelocity().x);
        } else {
            startX = endX = (int)(actor.getPosition().x + actor.getVelocity().x);
        }

        // get the block(s) bob can collide with
        populateCollidableBlocks(startX, startY, endX, endY);

        // simulate bob's movement on the X
        bobRect.x += actor.getVelocity().x;

        // clear collision boxes in world
        world.getCollisionRects().clear();

        // if bob collides, make his horizontal velocity 0
        for (Block block : collidable) {
            if (block == null) continue;
            if (bobRect.overlaps(block.getBounds())) {
                actor.getVelocity().x = 0;
                world.getCollisionRects().add(block.getBounds());
                break;
            }
        }

        // reset the x position of the collision box
        bobRect.x = actor.getPosition().x;

        // the same thing but on the vertical Y axis
        startX = (int) actor.getBounds().x;
        endX = (int) (actor.getBounds().x + actor.getBounds().width);
        if (actor.getVelocity().y < 0) {
            startY = endY = (int) Math.floor(actor.getBounds().y + actor.getVelocity().y);
        } else {
            startY = endY = (int) Math.floor(actor.getBounds().y + actor.getBounds().height + actor.getVelocity().y);
        }

        populateCollidableBlocks(startX, startY, endX, endY);

        bobRect.y += actor.getVelocity().y;

        for (Block block : collidable) {
            if (block == null) continue;
            if (bobRect.overlaps(block.getBounds())) {
                if (actor.getVelocity().y < 0) {
                    grounded = true;
                }
                actor.getVelocity().y = 0;
                world.getCollisionRects().add(block.getBounds());
                break;
            }
        }
        // reset the collision box's position on Y
        bobRect.y = actor.getPosition().y;

        // update Bob's position
        actor.getPosition().add(actor.getVelocity());
        actor.getBounds().x = actor.getPosition().x;
        actor.getBounds().y = actor.getPosition().y;

        // un-scale velocity (not in frame time)
        actor.getVelocity().scl(1 / delta);

    }

    /** populate the collidable array with the blocks found in the enclosing coordinates **/
    private void populateCollidableBlocks(int startX, int startY, int endX, int endY) {
        collidable.clear();
        for (int x = startX; x <= endX; x++) {
            for (int y = startY; y <= endY; y++) {
                if (x >= 0 && x < world.getLevel().getWidth() && y >=0 && y < world.getLevel().getHeight()) {
                    collidable.add(world.getLevel().get(x, y));
                }
            }
        }
    }


    /** Change Bob's state and parameters based on input controls **/
    private boolean processInput() {
        if (keys.get(Keys.JUMP)) {
            if (!actor.getState().equals(State.JUMPING)) {
                jumpingPressed = true;
                jumpPressedTime = System.currentTimeMillis();
                actor.setState(State.JUMPING);
                actor.getVelocity().y = MAX_JUMP_SPEED;
                grounded = false;
            } else {
                if (jumpingPressed && ((System.currentTimeMillis() - jumpPressedTime) >= LONG_JUMP_PRESS)) {
                    jumpingPressed = false;
                } else {
                    if (jumpingPressed) {
                        actor.getVelocity().y = MAX_JUMP_SPEED;
                    }
                }
            }
        }
        if (keys.get(Keys.LEFT)) {
            // left is pressed
            actor.setFacingLeft(true);
            if (!actor.getState().equals(State.JUMPING)) {
                actor.setState(State.WALKING);
            }
            if (grounded ==true) {
                actor.setState(State.WALKING);
            }
            actor.getAcceleration().x = -ACCELERATION;

        } else if (keys.get(Keys.RIGHT)) {
            // left is pressed
            actor.setFacingLeft(false);
            if (!actor.getState().equals(State.JUMPING)) {
                actor.setState(State.WALKING);
            }
            actor.getAcceleration().x = ACCELERATION;
        } else {
            if (!actor.getState().equals(State.JUMPING)) {
                actor.setState(State.IDLE);
            }
            actor.getAcceleration().x = 0;

        }
        return false;
    }



}
