package nume.joc.model;


import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.Array;
import java.util.ArrayList;
import java.util.List;

import com.badlogic.gdx.math.Rectangle;

/**
 * Created by roxy on 1/23/2015.
 */
public class World {


    // blocurile
    Array<Block> blocks = new Array<Block>();

    Actor actor;
    Level level;

    /** The collision boxes **/
    Array<Rectangle> collisionRects = new Array<Rectangle>();

    public Array<Rectangle> getCollisionRects() {
        return collisionRects;
    }

    public Array<Block> getBlocks() {

        return blocks;
    }

    public Actor getActor() {

        return actor;

    }
    public Level getLevel() {
        return level;
    }

    Array<Enemy> enemy=new Array<Enemy>();

    public Array<Enemy> getEnemy() {

        return enemy;
    }


    /** Return only the blocks that need to be drawn **/
    public List<Block> getDrawableBlocks(int width, int height) {
        int x = (int)actor.getPosition().x - width;
        int y = (int)actor.getPosition().y - height;
        if (x < 0) {
            x = 0;
        }
        if (y < 0) {
            y = 0;
        }
        int x2 = x + 2 * width;
        int y2 = y + 2 * height;
        if (x2 > level.getWidth()) {
            x2 = level.getWidth() - 1;
        }
        if (y2 > level.getHeight()) {
            y2 = level.getHeight() - 1;
        }

        List<Block> blocks = new ArrayList<Block>();
        Block block;
        for (int col = x; col <= x2; col++) {
            for (int row = y; row <= y2; row++) {
                block = level.getBlocks()[col][row];
                if (block != null) {
                    blocks.add(block);
                }
            }
        }
        return blocks;
    }

   public World() {

        createDemoWorld();

    }

    // adaugarea blocurilor
   private void createDemoWorld() {
        //pozitionarea actorului
        actor = new Actor(new Vector2(5, 2));
        level = new Level();
       //creaza liniile cu blocuri
     /*  for (int i = 0; i < 20; i++) {
           blocks.add(new Block(new Vector2(i, 0)));
           blocks.add(new Block(new Vector2(i, 7)));
           blocks.add(new Block(new Vector2(i, 6)));
           //a doua linie de blocuri
           if (i > 3)
             blocks.add(new Block(new Vector2(i, 1)));

       }

        //restul liniilor
        blocks.add(new Block(new Vector2(9, 2)));
        blocks.add(new Block(new Vector2(9, 3)));
        blocks.add(new Block(new Vector2(9, 4)));
        blocks.add(new Block(new Vector2(9, 5)));
        blocks.add(new Block(new Vector2(6, 3)));
        blocks.add(new Block(new Vector2(6, 4)));
        blocks.add(new Block(new Vector2(6, 5)));

*/


       Enemy en1 = new Enemy(new Vector2(16, 2));
       Enemy en2 = new Enemy(new Vector2(6, 2));
       Enemy en3 = new Enemy(new Vector2(5, 1));
       Enemy en4 = new Enemy(new Vector2(3, 1));
       Enemy en5 = new Enemy(new Vector2(19, 4));


    enemy.add(en1);
    enemy.add(en2);
    enemy.add(en3);
    enemy.add(en4);
    enemy.add(en5);

   }

}
