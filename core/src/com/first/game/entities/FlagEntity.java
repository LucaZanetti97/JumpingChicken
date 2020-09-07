package com.first.game.entities;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.World;
import com.badlogic.gdx.scenes.scene2d.Actor;

public class FlagEntity extends Actor {

    private static final float METRO_IN_PIXEL = 45;

    private Texture texture;
    private World world;
    private Body body;
    private Vector2 position;

    public FlagEntity (World _world, Texture _texture, Vector2 flagPosition){
        world= _world;
        texture= _texture;
        position= flagPosition;

        BodyDef def= new BodyDef();
        def.position.set(flagPosition);
        body= world.createBody(def);
    }

    @Override
    public void draw(Batch batch, float parentAlpha) {
        batch.draw(texture, (position.x-0.5f)*METRO_IN_PIXEL , (position.y-0.5f)*METRO_IN_PIXEL, 1*METRO_IN_PIXEL, 1*METRO_IN_PIXEL);
    }

    public void release(){
        world.destroyBody(body);
    }

}
