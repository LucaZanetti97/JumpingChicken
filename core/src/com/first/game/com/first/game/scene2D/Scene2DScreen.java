package com.first.game.com.first.game.scene2D;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.first.game.BasicScreen;
import com.first.game.FirstGame;
import com.first.game.com.first.game.scene2D.FirstActor;

/**
 * Created by Luca on 20/12/2018.
 */

public class Scene2DScreen extends BasicScreen {

    private FirstGame game;
    //associo lo schermo al gioco
    public Scene2DScreen(FirstGame _game){
        game=_game;
    }

    private Stage stage;
    private FirstActor protagonista;
    private Texture protagonistaImage;

    @Override
    public void show() {
        stage = new Stage();
        stage.setDebugAll(true);
        protagonista = new FirstActor(protagonistaImage);
        stage.addActor(protagonista);
    }

    @Override
    public void hide() {
        stage.dispose();    //rilascio la risorsa
    }

    @Override
    public void render(float delta) {
        stage.act();
        stage.draw();
        // .....
    }

}
