package com.first.game.entities;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.Fixture;
import com.badlogic.gdx.physics.box2d.PolygonShape;
import com.badlogic.gdx.physics.box2d.World;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.utils.Array;

public class PlayerEntity extends Actor {

    private static final float METRO_IN_PIXEL = 45;
    private static final float PLAYER_SPEED = 5;
    private static final float MIN_PLAYER_SPEED = 1;
    private static final float IMPULSE_STRENGHT = 20f;

    private Texture texture;
    private World world;
    private Body body;
    private Fixture fixture;
    private boolean alive=true, jumping=false;

    private int collisions=0;
    private int killedFlames=0;

    public PlayerEntity (World _world, Texture _texture, Vector2 initialPosition){
        world= _world;
        texture= _texture;

        BodyDef def= new BodyDef();
        def.position.set(initialPosition);
        def.type = BodyDef.BodyType.DynamicBody;
        body= world.createBody(def);

        PolygonShape playerShape = new PolygonShape();
        playerShape.setAsBox(0.5f,0.5f);
        fixture= body.createFixture(playerShape, 3);
        fixture.setUserData("player");
        playerShape.dispose();

        setSize( 1*METRO_IN_PIXEL , 1*METRO_IN_PIXEL);
    }

    @Override
    public void act(float delta) {

        if(Gdx.input.justTouched()){
            jump();
        }

        if(alive){
            modifyLinearVelocityX(newLinearVelocityX());
        }
    }

    @Override
    public void draw(Batch batch, float parentAlpha) {
        setPosition((body.getPosition().x - 0.5f)*METRO_IN_PIXEL, (body.getPosition().y-0.5f)*METRO_IN_PIXEL);
        batch.draw(texture, getX(), getY(), getWidth(), getHeight());
    }

    public void release(){
        body.destroyFixture(fixture);
        world.destroyBody(body);
    }

    public void jump (){
        if(!jumping && alive){
            jumping=true;
            Vector2 posizione = body.getPosition();
            body.applyLinearImpulse(0, IMPULSE_STRENGHT, posizione.x, posizione.y, true);
        }
    }

    public void modifyLinearVelocityX ( float vX){
        float vY= body.getLinearVelocity().y;
        body.setLinearVelocity(vX, vY);
    }

    public float newLinearVelocityX (){

        float speed= PLAYER_SPEED;

        for(int c=collisions; c>0; c--){
            speed=speed - 0.3f;
        }

        for(int k=killedFlames; k>0; k--){
            speed=speed + 0.1f;
        }

        if(speed>0){
            return speed;
        } else return MIN_PLAYER_SPEED;
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

    public float getLinearVelocityX(){
        return body.getLinearVelocity().x;
    }

    public void incrementCollisionsNumber(){
        collisions++;
    }

    public int getCollisionsNumber(){
        return collisions;
    }

    public int getKilledFlames() {
        return killedFlames;
    }

    public void incrementKilledFlamesNumber() {
        killedFlames++;
    }
}
