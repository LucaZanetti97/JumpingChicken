package com.first.game.Box2D;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.Box2DDebugRenderer;
import com.badlogic.gdx.physics.box2d.Contact;
import com.badlogic.gdx.physics.box2d.ContactImpulse;
import com.badlogic.gdx.physics.box2d.ContactListener;
import com.badlogic.gdx.physics.box2d.Fixture;
import com.badlogic.gdx.physics.box2d.Manifold;
import com.badlogic.gdx.physics.box2d.PolygonShape;
import com.badlogic.gdx.physics.box2d.World;
import com.first.game.BasicScreen;
import com.first.game.FirstGame;

/**
 * Created by Luca on 20/12/2018.
 */

public class Box2DScreen extends BasicScreen {

    private FirstGame game;

    public Box2DScreen (FirstGame _game){
        game=_game;
    }

    private World world;
    private Box2DDebugRenderer renderer;
    private OrthographicCamera camera;

    private Body personaggioBody, suoloBody;
    private Fixture personaggioFixture, suoloFixture;
    private boolean collisioneAvvenuta = false;

    @Override
    public void show() {
        world= new World(new Vector2( 0, -10) , true);
        renderer= new Box2DDebugRenderer();
        camera= new OrthographicCamera(Gdx.graphics.getWidth()/50,Gdx.graphics.getHeight()/50);
        camera.translate(0,2);  //abbassa di y metri

        world.setContactListener(new ContactListener() {
            @Override
            public void beginContact(Contact contact) {
                Fixture fixtureA = contact.getFixtureA();
                Fixture fixtureB = contact.getFixtureB();

                if( fixtureA==personaggioFixture && fixtureB==suoloFixture){
                    collisioneAvvenuta= true;
                }

                if( fixtureB==personaggioFixture && fixtureA==suoloFixture){
                    collisioneAvvenuta= true;
                }
            }

            @Override
            public void endContact(Contact contact) {

            }

            @Override
            public void preSolve(Contact contact, Manifold oldManifold) {

            }

            @Override
            public void postSolve(Contact contact, ContactImpulse impulse) {

            }
        });

        personaggioBody= world.createBody(createPersonaggioBodyDef()); //creo la def del corpo e lo aggiungo al world
        suoloBody= world.createBody(createSuoloBodyDef());

        PolygonShape personaggioShape = new PolygonShape();
        personaggioShape.setAsBox(0.5f,0.5f);  //lo considera fisicamente una scatola 1m x 1m
        personaggioFixture= personaggioBody.createFixture(personaggioShape, 1);
        personaggioShape.dispose();  //va fatto qui perchè esiste solo in questo metodo

        PolygonShape suoloShape = new PolygonShape();
        suoloShape.setAsBox(500,1);
        suoloFixture= suoloBody.createFixture(suoloShape, 1);
        suoloShape.dispose();
    }

    private BodyDef createSuoloBodyDef() {
        BodyDef def= new BodyDef();
        def.position.set(0,-1);
        def.type = BodyDef.BodyType.StaticBody;
        return def;
    }

    //metodo per specificare le caratteristiche del corpo
    private BodyDef createPersonaggioBodyDef(){
        BodyDef def= new BodyDef();
        def.position.set(-5,0.5f);
        def.type = BodyDef.BodyType.DynamicBody;
        return def;
    }

    @Override
    public void dispose() {
        personaggioBody.destroyFixture(personaggioFixture);
        world.destroyBody(personaggioBody);
        suoloBody.destroyFixture(suoloFixture);
        world.destroyBody(suoloBody);
        world.dispose();
        renderer.dispose();
    }

    @Override
    public void render(float delta) {
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        if(Gdx.input.justTouched() && collisioneAvvenuta){
            jump();
            collisioneAvvenuta=false;
        }

        float vY = personaggioBody.getLinearVelocity().y;
        personaggioBody.setLinearVelocity(2, vY);

        world.step(delta, 6,2);

        camera.update();

        renderer.render(world,camera.combined);
    }

    public void jump (){
        Vector2 posizione = personaggioBody.getPosition(); //rappresenta il centro di massa
        personaggioBody.applyLinearImpulse(0, 10, posizione.x, posizione.y, true);
    }

}
