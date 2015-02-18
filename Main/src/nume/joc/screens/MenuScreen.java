package nume.joc.screens;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Pixmap;
import com.badlogic.gdx.graphics.Pixmap.Format;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.*;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton.TextButtonStyle;
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener;
import nume.joc.MyGame;


/**
 * Created by roxy on 2/15/2015.
 */
public class MenuScreen implements Screen {


    MyGame game;

    Skin skin;
    Stage stage;
    Table table;
    SpriteBatch batch;
    Music mp3Sound;

    public MenuScreen(MyGame g){
        create();
        this.game=g;

        mp3Sound = Gdx.audio.newMusic(Gdx.files.internal("sleepy.mp3"));
        mp3Sound.setLooping(true);
        mp3Sound.play();


    }

    public MenuScreen(){
        create();
    }
    public void create(){
        batch = new SpriteBatch();
        stage = new Stage();
        table= new Table();
        table.debug();

        Gdx.input.setInputProcessor(stage);

        // declar un skin pentru a alege cum sa arate butonul
        skin = new Skin();
        // generarea unei texturi albe 1x1
        Pixmap pixmap = new Pixmap(100, 100, Format.RGBA8888);
        pixmap.setColor(Color.BLUE);
        pixmap.fill();

        skin.add("white", new Texture(pixmap));

        // stocheaza fontul default libgdx
        BitmapFont bfont=new BitmapFont();
        bfont.scale(1);
        skin.add("default",bfont);

        // definirea stilului butonului
        TextButtonStyle textButtonStyle = new TextButtonStyle();
        textButtonStyle.up = skin.newDrawable("white", Color.DARK_GRAY);
        textButtonStyle.down = skin.newDrawable("white", Color.DARK_GRAY);
        textButtonStyle.checked = skin.newDrawable("white", Color.MAGENTA);
        textButtonStyle.over = skin.newDrawable("white", Color.LIGHT_GRAY);

        textButtonStyle.font = skin.getFont("default");

        skin.add("default", textButtonStyle);


        Label.LabelStyle labelStyle=new Label.LabelStyle();
        labelStyle.font=skin.getFont("default");


        Label welcomeLabel = new Label( "Welcome !", labelStyle );
        welcomeLabel.setPosition(260,400);

        stage.addActor( welcomeLabel );


        // Create a button with the "default" TextButtonStyle. A 3rd parameter can be used to specify a name other than "default".
        final TextButton textButton=new TextButton("Start Game",textButtonStyle);
        textButton.setPosition(250, 250);
        stage.addActor(textButton);


       final TextButton textButton2=new TextButton("Exit Game",textButtonStyle);
        textButton2.setPosition(260, 100);
        stage.addActor(textButton2);



        // adaug un eveniment la schimbarea starii butonului
        textButton.addListener(new ChangeListener() {
            public void changed (ChangeEvent event, Actor actor) {
                mp3Sound.stop();
                 game.setScreen( new GameScreen());

            }
        });


        textButton2.addListener(new ChangeListener(){
            public void changed (ChangeEvent event, Actor actor) {
                Gdx.app.exit();

            }
        });

    }

    public void render (float delta) {
        Gdx.gl.glClearColor(0.2f, 0.2f, 0.2f, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        stage.act(Math.min(Gdx.graphics.getDeltaTime(), 1 / 30f));
        stage.draw();
    }

    @Override
    public void resize (int width, int height) {
        stage.getViewport().update(width, height, true);
    }

    @Override
    public void dispose () {
        stage.dispose();
        skin.dispose();
    }

    @Override
    public void show() {
        // TODO Auto-generated method stub

    }

    @Override
    public void hide() {
        // TODO Auto-generated method stub

    }

    @Override
    public void pause() {
        // TODO Auto-generated method stub

    }

    @Override
    public void resume() {
        // TODO Auto-generated method stub

    }
}
