package nume.joc.controller;

/**
 * Created by uioana on 2/14/2015.
 */

import java.util.HashMap;
import java.util.Map;

import com.badlogic.gdx.math.Vector2;
import nume.joc.model.Actor;
import nume.joc.model.Actor.State;
import nume.joc.model.World;

public class WorldController {

    //enumeratia(strunc) Kyes care contine cheile folosite in joc
    enum Keys {
        LEFT, RIGHT, JUMP, FIRE
    }

    private static final long LONG_JUMP_PRESS 	= 150l;
    private static final float ACCELERATION 	= 20f;
    private static final float GRAVITY 			= -20f;
    private static final float MAX_JUMP_SPEED	= 7f;
    private static final float DAMP 			= 0.90f;
    private static final float MAX_VEL 			= 4f;
    private static final float WIDTH = 10f;

    private World 	world;
    private Actor 	actor;
    private long	jumpPressedTime;
    private boolean jumpingPressed;

    static Map<Keys, Boolean> keys = new HashMap<WorldController.Keys, Boolean>();
    static {
        keys.put(Keys.LEFT, false);
        keys.put(Keys.RIGHT, false);
        keys.put(Keys.JUMP, false);
        keys.put(Keys.FIRE, false);
    };

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

        actor.getAcceleration().y = GRAVITY;
        actor.getAcceleration().scl(delta);
        actor.getVelocity().add(actor.getAcceleration().x, actor.getAcceleration().y);
        if (actor.getAcceleration().x == 0) actor.getVelocity().x *= DAMP;
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

    /** Change Bob's state and parameters based on input controls **/
    private boolean processInput() {
        if (keys.get(Keys.JUMP)) {
            if (!actor.getState().equals(State.JUMPING)) {
                jumpingPressed = true;
                jumpPressedTime = System.currentTimeMillis();
                actor.setState(State.JUMPING);
                actor.getVelocity().y = MAX_JUMP_SPEED;
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
