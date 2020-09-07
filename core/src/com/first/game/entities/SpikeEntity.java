package com.first.game.entities;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.Fixture;
import com.badlogic.gdx.physics.box2d.PolygonShape;
import com.badlogic.gdx.physics.box2d.World;
import com.badlogic.gdx.scenes.scene2d.Actor;

public class SpikeEntity extends Actor {

    private static final float METRO_IN_PIXEL = 45f;

    private Texture texture;
    private World world;
    private Body body;
    private Fixture fixture;
    private boolean alive=true;

    /**
     *
     * @param _world
     * @param _texture
     * @param x  posizione iniziale del centro del triangolo
     * @param y posizione iniziale della base del triangolo
     */
    public SpikeEntity (World _world, Texture _texture, float x, float y){
        world= _world;
        texture= _texture;

        BodyDef def= new BodyDef();
        def.position.set(x , y + 0.5f);
        body= world.createBody(def);

        PolygonShape spikeShape = new PolygonShape();
        Vector2[] vertices= new Vector2[3];
        vertices[0]= new Vector2(-0.5f, -0.5f);
        vertices[1]= new Vector2(0.5f, -0.5f);
        vertices[2]= new Vector2(0, 0.5f);
        spikeShape.set(vertices);
        fixture= body.createFixture(spikeShape, 1);
        fixture.setUserData("spike");
        spikeShape.dispose();

        setSize( 1*METRO_IN_PIXEL , 1*METRO_IN_PIXEL);
        setPosition((x-0.5f)*METRO_IN_PIXEL, (y-0.1f)*METRO_IN_PIXEL);
    }

    @Override
    public void draw(Batch batch, float parentAlpha) {
        batch.draw(texture, getX(), getY(), getWidth(), getHeight());
    }

    public void release(){
        body.destroyFixture(fixture);
        world.destroyBody(body);
    }
}
