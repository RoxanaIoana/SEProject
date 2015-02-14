package nume.joc.controller;

/**
 * Created by uioana on 2/14/2015.
 */

import java.util.HashMap;
import java.util.Map;

import nume.joc.model.Actor;
import nume.joc.model.Actor.State;
import nume.joc.model.World;

public class WorldController {

    enum Keys {
        LEFT, RIGHT, JUMP, FIRE
    }

    private World world;
    private Actor actor;

    static Map<Keys, Boolean> keys = new HashMap<WorldController.Keys, Boolean>();

    static {
        keys.put(Keys.LEFT, false);
        keys.put(Keys.RIGHT, false);
        keys.put(Keys.JUMP, false);
        keys.put(Keys.FIRE, false);
    }

    ;

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
    }

    public void fireReleased() {
        keys.get(keys.put(Keys.FIRE, false));
    }

    /**
     * The main update method *
     */
    public void update(float delta) {
        processInput();
        actor.update(delta);
    }

    /**
     * Change Bob's state and parameters based on input controls *
     */
    private void processInput() {
        if (keys.get(Keys.LEFT)) {
            // left is pressed
            actor.setFacingLeft(true);
            actor.setState(State.WALKING);
            actor.getVelocity().x = -Actor.SPEED;
        }
        if (keys.get(Keys.RIGHT)) {
            // left is pressed
            actor.setFacingLeft(false);
            actor.setState(State.WALKING);
            actor.getVelocity().x = Actor.SPEED;
        }
        // need to check if both or none direction are pressed, then Bob is idle
        if ((keys.get(Keys.LEFT) && keys.get(Keys.RIGHT)) ||
                (!keys.get(Keys.LEFT) && !(keys.get(Keys.RIGHT)))) {
            actor.setState(State.IDLE);
            // acceleration is 0 on the x
            actor.getAcceleration().x = 0;
            // horizontal speed is 0
            actor.getVelocity().x = 0;
        }
    }
}