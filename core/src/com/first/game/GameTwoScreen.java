package com.first.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Contact;
import com.badlogic.gdx.physics.box2d.ContactImpulse;
import com.badlogic.gdx.physics.box2d.ContactListener;
import com.badlogic.gdx.physics.box2d.Manifold;
import com.badlogic.gdx.physics.box2d.World;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.actions.Actions;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.first.game.entities.FlameEntity;
import com.first.game.entities.PlayerEntity;

import java.util.ArrayList;
import java.util.List;

public class GameTwoScreen extends BasicScreen {

    private static final float METRO_IN_PIXEL = 45;

    private FirstGame game;
    private World world;
    private Stage stage;

    private PlayerEntity player;
    private List<FlameEntity> flameList = new ArrayList<FlameEntity>();

    public GameTwoScreen (FirstGame _game){
        game=_game;
        world= new World(new Vector2(0,-10), true);
        stage= new Stage(new FitViewport(640, 360));

        world.setContactListener(new GameTwoScreen.GameContactListener());
    }

    @Override
    public void show() {
        Texture playerTexture = game.getManager().get("chicken-256.png");
        Texture flameTexture= game.getManager().get("flame-256.png");

        player = new PlayerEntity(world, playerTexture, new Vector2(320/METRO_IN_PIXEL,180/METRO_IN_PIXEL));

        flameList.add(new FlameEntity(world, flameTexture, new Vector2(90/METRO_IN_PIXEL, 270/METRO_IN_PIXEL)));
        flameList.add(new FlameEntity(world, flameTexture, new Vector2(90/METRO_IN_PIXEL, 90/METRO_IN_PIXEL)));
        flameList.add(new FlameEntity(world, flameTexture, new Vector2(550/METRO_IN_PIXEL, 90/METRO_IN_PIXEL)));
        flameList.add(new FlameEntity(world, flameTexture, new Vector2(550/METRO_IN_PIXEL, 270/METRO_IN_PIXEL)));

        stage.addActor(player);

        for(FlameEntity flame : flameList){
            stage.addActor(flame);
        }
    }

    @Override
    public void hide() {
        player.release();
        player.remove();

        for(FlameEntity flame : flameList){
            flame.release();
            flame.remove();
        }

    }

    @Override
    public void render(float delta) {
        Gdx.gl.glClearColor(0.4f, 0.5f, 0.8f, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        stage.act();
        world.step(delta,6, 2);
        stage.draw();
    }

    @Override
    public void dispose() {
        world.dispose();
        stage.dispose();
    }

    private class GameContactListener implements ContactListener {

        private boolean areCollided(Contact contact, Object userA, Object userB){
            Object userDataA = contact.getFixtureA().getUserData();
            Object userDataB = contact.getFixtureB().getUserData();

            if(userDataA==null || userDataB==null){
                return false;
            }

            return (userDataA.equals(userA) && userDataB.equals(userB)) ||
                    (userDataA.equals(userB) && userB.equals(userA));
        }

        @Override
        public void beginContact(Contact contact) {
            if(areCollided(contact, "player", "flame")){
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
    }
}
