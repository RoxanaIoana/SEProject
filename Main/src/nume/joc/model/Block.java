package nume.joc.model;

import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;

/**
 * Created by roxy on 1/23/2015.
 * Aceasta clasa este necesara pentru a defini un bloc, blocurile sunt folosite drept obstacole,
 * Actorul nu poate trece prin ele
 */
public class Block {

    static public final float SIZE = 1f;
    Vector2     position = new Vector2();
    Rectangle   bounds = new Rectangle();

    public Block(Vector2 pos) {

        this.position = pos;
        this.bounds.width = SIZE;
        this.bounds.height = SIZE;
        this.bounds.setX(pos.x);
        this.bounds.setY(pos.y);

    }


    public Rectangle getBounds() {
        return bounds;
    }

    public Vector2 getPosition() {
        return position;
    }
}
