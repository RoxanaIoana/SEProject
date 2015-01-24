package nume.joc.model;

import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.math.Rectangle;


/**
 * Created by roxy on 1/23/2015.
 */
public class Actor {

    public Vector2 getPosition() {
        return position;
    }

    public Rectangle getBounds() {
        return bounds;
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
    static final float SPEED = 2f;  // unit per second
    static final float JUMP_VELOCITY = 1f;
    static public final float SIZE = 0.5f; // half a unit

    // proprietati care ajuta la descrierea starii actorului
    Vector2     position = new Vector2();
    Vector2     acceleration = new Vector2();
    Vector2     velocity = new Vector2();
    Rectangle   bounds = new Rectangle();
    State       state = State.IDLE;
    boolean     facingLeft = true;


    public Actor(Vector2 position) {

        this.position = position;
        this.bounds.height = SIZE;
        this.bounds.width = SIZE;

    }


}
