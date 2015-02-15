package nume.joc.model;


import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.Array;

/**
 * Created by roxy on 1/23/2015.
 */
public class World {


    // blocurile
    Array<Block> blocks = new Array<Block>();

    Actor actor;

    public Array<Block> getBlocks() {

        return blocks;
    }

    public Actor getActor() {

        return actor;

    }

   public World() {

        createDemoWorld();

    }

    // adaugarea blocurilor
   private void createDemoWorld() {
        //pozitionarea actorului
        actor = new Actor(new Vector2(7, 2));
       //creaza liniile cu blocuri
       for (int i = 0; i < 10; i++) {
           blocks.add(new Block(new Vector2(i, 0)));                       blocks.add(new Block(new Vector2(i, 7)));
           blocks.add(new Block(new Vector2(i, 6)));
           //a doua linie de blocuri
           if (i > 2)
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

    }


}
