package com.first.game.entities;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.Fixture;
import com.badlogic.gdx.physics.box2d.PolygonShape;
import com.badlogic.gdx.physics.box2d.World;
import com.badlogic.gdx.scenes.scene2d.Actor;

public class FlameEntity extends Actor {

    private static final float METRO_IN_PIXEL = 45;
    private static final float FLAME_SPEED = 5;
    private static final float IMPULSE_STRENGHT = 7.2f;

    private Texture texture;
    private World world;
    private Body body;
    private Fixture fixture;
    private boolean alive=true,jumping=false, mustJump=false;
    private float speed;
    private float starterPositionX;
    private int collisions=0;

    public FlameEntity(World _world, Texture _texture, Vector2 initialPosition /*, float _speed*/){
        world= _world;
        texture= _texture;
        //speed=_speed;
        starterPositionX= initialPosition.x;

        BodyDef def= new BodyDef();
        def.position.set(initialPosition);
        def.type = BodyDef.BodyType.DynamicBody;
        body= world.createBody(def);

        PolygonShape playerShape = new PolygonShape();
        playerShape.setAsBox(0.3f,0.3f);
        fixture= body.createFixture(playerShape, 3);
        fixture.setUserData("flame");
        playerShape.dispose();

        setSize( 1*METRO_IN_PIXEL , 1*METRO_IN_PIXEL);
    }

    @Override
    public void act(float delta) {
        if(Gdx.input.justTouched() || mustJump){
            jump();
        }

        if(alive){
            modifyLinearVelocityX(newLinearVelocityX());
        }
    }

    @Override
    public void draw(Batch batch, float parentAlpha) {
        setPosition((body.getPosition().x - 0.3f)*METRO_IN_PIXEL, (body.getPosition().y-0.3f)*METRO_IN_PIXEL);
        batch.draw(texture, getX(), getY(), getWidth(), getHeight());
    }

    public void release(){
        body.destroyFixture(fixture);
        world.destroyBody(body);
    }

    public void jump (){
        if(!jumping && alive){
            jumping=true;
            mustJump=false;
            Vector2 posizione = body.getPosition();
            body.applyLinearImpulse(0, IMPULSE_STRENGHT, posizione.x, posizione.y, true);
        }
    }

    public void modifyLinearVelocityX ( float vX){
        float vY= body.getLinearVelocity().y;
        body.setLinearVelocity(vX, vY);
    }

    public float newLinearVelocityX(){
        float speed= FLAME_SPEED;

        float metersCounter= body.getPosition().x - starterPositionX;

        if(metersCounter<20) {
            for (int m = (int) metersCounter; m > 0; m--) {
                speed = speed + 0.01f;
            }
        }else if(metersCounter<50) {
            for (int m = (int) metersCounter; m > 0; m--) {
                speed = speed + 0.02f;
            }
        }  else {
            for (int m = (int) metersCounter; m > 0; m--) {
                speed = speed + 0.05f;
            }
        }

        return speed;
    }

    public boolean isJumping() {
        return jumping;
    }

    public void setJumping(boolean _jumping) {
        jumping = _jumping;
    }

    public boolean isAlive() {
        return alive;
    }

    public void setAlive(boolean _alive) {
        alive = _alive;
    }

    public void incrementCollisionsNumber(){
        collisions++;
    }

    public int getCollisionsNumber(){
        return collisions;
    }

    public void setMustJump(boolean _mustJump){
        mustJump=_mustJump;
    }
}
