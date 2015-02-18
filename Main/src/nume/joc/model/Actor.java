package nume.joc.model;

import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.math.Rectangle;


/**
 * Created by roxy on 1/23/2015.
 */
public class Actor {


    private float width;

    public float getStateTime() {
        return stateTime;
    }

    public float getWidth() {
        return width;
    }

    /*
                Starile in care se poate afla actorul:
                IDLE - actorul nu se misca si nici nu sare dar este in viata
                WALKING - se misca la stanga sau la dreapta cu o viteza constanta
                JUMPING - sare in sus la dreapta sau la stanga
                DYING - a murit, nu mai este vizibil
                 */
    public enum State {
        IDLE, WALKING, JUMPING, DYING
    }

    // constante pentru calcularea vitezei si pozitiei actorului
    public static final float SPEED = 4f;  // unit per second
    public static final float JUMP_VELOCITY = 4f;
    public static final float SIZE = 0.5f; // half a unit

    // proprietati care ajuta la descrierea starii actorului
    Vector2     position = new Vector2();
    Vector2     acceleration = new Vector2();
    Vector2     velocity = new Vector2();
    Rectangle   bounds = new Rectangle();
    State       state;
    boolean     facingLeft;
    float		stateTime;
    boolean		longJump;

    public Actor(Vector2 position) {

        this.position = position;
        this.bounds.height = SIZE;
        this.bounds.width = SIZE;

        state = State.IDLE;

        facingLeft = true;
        stateTime = 0;
        longJump = false;

    }

    public Vector2 getPosition() {
        return position;
    }

    public Rectangle getBounds() {
        return bounds;
    }

    public Vector2 getAcceleration() {
        return acceleration;
    }

    public void setState(State state) {
        this.state = state;
    }

    public Vector2 getVelocity() {
        return velocity;
    }

    public void setFacingLeft(boolean facingLeft) {
        this.facingLeft = facingLeft;
    }

    public boolean isFacingLeft() {
        return facingLeft;
    }

    public State getState() {
        return state;
    }

    public boolean isLongJump() {
        return longJump;
    }

    public void setLongJump(boolean longJump) {
        this.longJump = longJump;
    }

   public void setPosition(Vector2 position) {
        this.position = position;
        this.bounds.setX(position.x);
        this.bounds.setY(position.y);
    }

    public void setAcceleration(Vector2 acceleration) {
        this.acceleration = acceleration;
    }

    public void setVelocity(Vector2 velocity) {
        this.velocity = velocity;
    }

    public void setBounds(Rectangle bounds) {
        this.bounds = bounds;
    }

    public void setStateTime(float stateTime) {
        this.stateTime = stateTime;
    }

    public void update(float delta) {
        position.add(velocity.cpy().scl(delta));
        bounds.x = position.x;
        bounds.y = position.y;
        stateTime += delta;
    }

}
