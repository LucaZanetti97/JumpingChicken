package com.first.game.com.first.game.scene2D;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.scenes.scene2d.Actor;

/**
 * Created by Luca on 20/12/2018.
 */

public class FirstActor extends Actor {

    private Texture actorImage;

    public FirstActor (Texture _actorImage){
        actorImage=_actorImage;
        setSize(_actorImage.getWidth(), _actorImage.getHeight());
    }

    @Override
    public void act(float delta) {
        // ...
    }

    @Override
    public void draw(Batch batch, float parentAlpha) {
        // ...
    }
}
