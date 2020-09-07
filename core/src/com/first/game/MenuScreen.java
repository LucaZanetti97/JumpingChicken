package com.first.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener;
import com.badlogic.gdx.utils.viewport.FitViewport;

public class MenuScreen extends BasicScreen {

    private static final float BUTTON_WIDTH= 200f;
    private static final float BUTTON_HEIGHT= 50f;

    private FirstGame game;

    private Stage stage;
    private Skin skin;
    private Image logo;
    private TextButton play;
    private Sound selectSound;

    public MenuScreen (final FirstGame _game){
        game=_game;

        selectSound= game.getManager().get("thip-sound.ogg");

        stage= new Stage(new FitViewport(640, 360));
        skin= new Skin(Gdx.files.internal("skin/uiskin.json"));
        logo= new Image(game.getManager().get("logo.jpg", Texture.class));
        play= new TextButton("Play", skin);

        play.addCaptureListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                selectSound.play();
                game.setScreen(new GameScreen(game));
            }
        });

        logo.setSize(192,240);
        logo.setPosition(320-logo.getWidth()/2, 120);
        play.setColor(Color.SLATE);
        play.setSize(BUTTON_WIDTH,BUTTON_HEIGHT);
        play.setPosition(320-BUTTON_WIDTH/2,20);
        stage.addActor(logo);
        stage.addActor(play);
    }

    @Override
    public void show() {
        Gdx.input.setInputProcessor(stage);
    }

    @Override
    public void hide() {
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
