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

public class FloorEntity extends Actor {

    private static final float METRO_IN_PIXEL = 45f;

    private Texture texture;
    private World world;
    private Body body, leftBody;
    private Fixture fixture, leftFixture;
    private boolean alive=true;

    /**
     *
     * @param _world
     * @param _texture
     * @param x  dove sta il bordo sinistro del suolo
     * @param y  dove sta il vordo superiore del suolo
     * @param width lunghezza del suolo
     */
    public FloorEntity (World _world, Texture _texture,float x, float y, float width){
        world= _world;
        texture= _texture;

        BodyDef def= new BodyDef();
        def.position.set((x + width/2), y - 0.5f);
        def.type = BodyDef.BodyType.StaticBody;
        body= world.createBody(def);

        PolygonShape floorShape = new PolygonShape();
        floorShape.setAsBox( width/2 ,0.5f);
        fixture= body.createFixture(floorShape, 1);
        fixture.setUserData("floor");
        floorShape.dispose();

        BodyDef leftDef= new BodyDef();
        leftDef.position.set(x, y - 0.5f);
        leftBody= world.createBody(leftDef);

        PolygonShape leftBodyShape = new PolygonShape();
        leftBodyShape.setAsBox( 0.02f ,0.4f);
        leftFixture= leftBody.createFixture(leftBodyShape, 1);
        leftFixture.setUserData("wall");
        leftBodyShape.dispose();

        setSize( width*METRO_IN_PIXEL , 1*METRO_IN_PIXEL);
        setPosition((x)*METRO_IN_PIXEL, (y-1)*METRO_IN_PIXEL);
    }

    @Override
    public void draw(Batch batch, float parentAlpha) {
        batch.draw(texture, getX(), getY(), getWidth(), getHeight());
    }

    public void release(){
        body.destroyFixture(fixture);
        world.destroyBody(body);
        leftBody.destroyFixture(leftFixture);
        world.destroyBody(leftBody);
    }

}
