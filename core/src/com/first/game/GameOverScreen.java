package com.first.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Button;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener;
import com.badlogic.gdx.utils.viewport.FitViewport;

public class GameOverScreen extends BasicScreen {

    private static final float BUTTON_WIDTH= 200f;
    private static final float BUTTON_HEIGHT= 50f;

    private FirstGame game;

    private Stage stage;
    private Skin skin;
    private Image gameOver, victory;
    private TextButton retry,menu,gamePoints;
    private Sound selectSound;
    private Music bgMusic;

    public GameOverScreen (FirstGame _game, float points, boolean win){
        game=_game;

        selectSound= game.getManager().get("thip-sound.ogg");

        if(win){
            bgMusic = game.getManager().get("victory-music.ogg");
        }
        else {
            bgMusic = game.getManager().get("defeat-music.ogg");
        }

        stage= new Stage(new FitViewport(640, 360));
        skin= new Skin(Gdx.files.internal("skin/uiskin.json"));
        gameOver= new Image(game.getManager().get("game-over.png", Texture.class));
        victory= new Image(game.getManager().get("victory.png", Texture.class));
        retry= new TextButton("Retry", skin);
        menu= new TextButton("Menu", skin);
        gamePoints= new TextButton("POINTS: " + points, skin);

        retry.addCaptureListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                bgMusic.stop();
                selectSound.play();
                game.setScreen(new GameScreen(game));
            }
        });

        menu.addCaptureListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                bgMusic.stop();
                selectSound.play();
                game.setScreen(game.menuScreen);
            }
        });

        menu.setColor(Color.GRAY);
        menu.setSize(BUTTON_WIDTH,BUTTON_HEIGHT);
        menu.setPosition(320-BUTTON_WIDTH-20, 10);

        retry.setColor(Color.SLATE);
        retry.setSize(BUTTON_WIDTH,BUTTON_HEIGHT);
        retry.setPosition(menu.getX()+BUTTON_WIDTH+40,10);

        gamePoints.setColor(Color.RED);
        gamePoints.setSize(BUTTON_WIDTH,BUTTON_HEIGHT);
        gamePoints.setPosition(320-BUTTON_WIDTH/2, 90);

        if(win){
            victory.setSize(450, 173);
            victory.setPosition(320-victory.getWidth()/2, 170);
            stage.addActor(victory);
        } else {
            gameOver.scaleBy(-0.5f);
            gameOver.setPosition(320-gameOver.getWidth()/4, 130);
            stage.addActor(gameOver);
        }
        stage.addActor(retry);
        stage.addActor(menu);
        stage.addActor(gamePoints);

        bgMusic.setVolume(0.5f);
        bgMusic.play();
    }

    @Override
    public void show() {
        Gdx.input.setInputProcessor(stage);
    }

    @Override
    public void hide() {
        bgMusic.stop();
        Gdx.input.setInputProcessor(null);
    }

    @Override
    public void render(float delta) {
        Gdx.gl.glClearColor(0.4f, 0.5f, 0.8f, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        stage.act();
        stage.draw();
    }

    @Override
    public void dispose() {
        stage.dispose();
    }
}
