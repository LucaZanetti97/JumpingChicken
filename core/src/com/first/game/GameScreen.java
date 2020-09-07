package com.first.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.audio.Sound;
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
import com.first.game.entities.FlagEntity;
import com.first.game.entities.FlameEntity;
import com.first.game.entities.FloorEntity;
import com.first.game.entities.PlayerEntity;
import com.first.game.entities.SpikeEntity;

import java.util.ArrayList;
import java.util.List;

public class GameScreen extends BasicScreen {

    private static final float METRO_IN_PIXEL = 45;
    private static final float KILLED_FLAME_POINTS = 50;
    //private static final float FLAME_STARTER_SPEED = 5f;
    private static final float ARRIVE_POSITION = 270;


    private FirstGame game;
    private World world;
    private Stage stage;

    private PlayerEntity player;
    private FlameEntity flame;
    private List<FloorEntity> floorList = new ArrayList<FloorEntity>();
    private List<SpikeEntity> spikeList = new ArrayList<SpikeEntity>();
    private FlagEntity arrivalFlag;

    private Sound hitSound, flameSound;
    private Music bgMusic;

    private int flameCounter;
    private float playerPoints;

    public GameScreen (FirstGame _game){
        game=_game;

        hitSound= game.getManager().get("metal-hit-sound.ogg");
        flameSound= game.getManager().get("flame-sound.ogg");
        bgMusic= game.getManager().get("game-music.ogg");

        world= new World(new Vector2(0,-10), true);
        stage= new Stage(new FitViewport(640, 360));

        world.setContactListener(new GameContactListener());
    }

    @Override
    public void show() {

        Texture playerTexture = game.getManager().get("chicken-256.png");
        Texture flameTexture= game.getManager().get("flame-256.png");
        Texture floorTexture= game.getManager().get("square-256.png");
        Texture spikeTexture= game.getManager().get("triangle-256.png");
        Texture spikeRotateTexture= game.getManager().get("triangle-rotate-256.png");
        Texture flagTexture= game.getManager().get("flag-256.png");

        player = new PlayerEntity(world, playerTexture, new Vector2(2,1.5f));

        flame = new FlameEntity(world, flameTexture, new Vector2(0.5f,1.5f)/*, FLAME_STARTER_SPEED*/);

        floorList.add(new FloorEntity(world, floorTexture,0, 1, 1000));
        floorList.add(new FloorEntity(world, floorTexture, 25, 2, 15));
        floorList.add(new FloorEntity(world, floorTexture, 45, 2, 55));
        floorList.add(new FloorEntity(world, floorTexture, 54, 3, 6));
        floorList.add(new FloorEntity(world, floorTexture, 110, 5, 80));
        floorList.add(new FloorEntity(world, floorTexture, 200, 2, 50));

        spikeList.add(new SpikeEntity(world, spikeTexture,10,1));
        spikeList.add(new SpikeEntity(world, spikeTexture,18,1));
        spikeList.add(new SpikeEntity(world, spikeTexture,30,2));
        spikeList.add(new SpikeEntity(world, spikeRotateTexture,37,3.2f));
        spikeList.add(new SpikeEntity(world, spikeTexture,42.5f,1));
        spikeList.add(new SpikeEntity(world, spikeRotateTexture,57,4.2f));
        spikeList.add(new SpikeEntity(world, spikeTexture,62,2));
        spikeList.add(new SpikeEntity(world, spikeTexture,64,2));
        spikeList.add(new SpikeEntity(world, spikeTexture,72,2));
        spikeList.add(new SpikeEntity(world, spikeTexture,80,2));
        spikeList.add(new SpikeEntity(world, spikeTexture,88,2));
        spikeList.add(new SpikeEntity(world, spikeTexture,96,2));
        spikeList.add(new SpikeEntity(world, spikeTexture,115,1));
        spikeList.add(new SpikeEntity(world, spikeRotateTexture,120,3.2f));
        spikeList.add(new SpikeEntity(world, spikeTexture,125,1));
        spikeList.add(new SpikeEntity(world, spikeRotateTexture,130,3.2f));
        spikeList.add(new SpikeEntity(world, spikeTexture,135,1));
        spikeList.add(new SpikeEntity(world, spikeRotateTexture,140,3.2f));
        spikeList.add(new SpikeEntity(world, spikeTexture,145,1));
        spikeList.add(new SpikeEntity(world, spikeTexture,146,1));
        spikeList.add(new SpikeEntity(world, spikeTexture,147,1));
        spikeList.add(new SpikeEntity(world, spikeTexture,148,1));
        spikeList.add(new SpikeEntity(world, spikeTexture,149,1));
        spikeList.add(new SpikeEntity(world, spikeTexture,150,1));
        spikeList.add(new SpikeEntity(world, spikeTexture,160,1));
        spikeList.add(new SpikeEntity(world, spikeTexture,161,1));
        spikeList.add(new SpikeEntity(world, spikeTexture,162,1));
        spikeList.add(new SpikeEntity(world, spikeRotateTexture,167,3.2f));
        spikeList.add(new SpikeEntity(world, spikeRotateTexture,168,3.2f));
        spikeList.add(new SpikeEntity(world, spikeRotateTexture,169,3.2f));
        spikeList.add(new SpikeEntity(world, spikeTexture,174,1));
        spikeList.add(new SpikeEntity(world, spikeTexture,175,1));
        spikeList.add(new SpikeEntity(world, spikeTexture,176,1));
        spikeList.add(new SpikeEntity(world, spikeRotateTexture,180,3.2f));
        spikeList.add(new SpikeEntity(world, spikeTexture,215,2));
        spikeList.add(new SpikeEntity(world, spikeTexture,225,2));
        spikeList.add(new SpikeEntity(world, spikeTexture,235,2));
        spikeList.add(new SpikeEntity(world, spikeTexture,245,2));
        spikeList.add(new SpikeEntity(world, spikeTexture,265,1));

        arrivalFlag= new FlagEntity(world, flagTexture, new Vector2(ARRIVE_POSITION, 1.5f));

        stage.addActor(player);

        stage.addActor(flame);
        flameCounter++;

        for(FloorEntity floor : floorList){
            stage.addActor(floor);
        }

        for(SpikeEntity spike : spikeList){
            stage.addActor(spike);
        }

        stage.addActor(arrivalFlag);
        //stage.getCamera().translate(-stage.getCamera().position.x + 320,0,0);

        bgMusic.setVolume(0.5f);
        bgMusic.play();
    }

    @Override
    public void hide() {
        bgMusic.stop();
        stage.clear();
        player.release();
        if(flameCounter != 0) {
            flame.release();
        }
        for(FloorEntity floor : floorList){
            floor.release();
        }
        for(SpikeEntity spike : spikeList){
            spike.release();
        }
        arrivalFlag.release();
    }

    @Override
    public void render(float delta) {
        Gdx.gl.glClearColor(0.4f, 0.5f, 0.8f, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        moveTheCamera(delta);
        checkIfArrived();
        deleteFlameIfNecessary();
        createFlameIfNecessary();
        stage.act();
        world.step(delta, 6,2);
        stage.draw();
    }

    @Override
    public void dispose() {
        world.dispose();
        stage.dispose();
    }

    private void checkIfArrived() {
        if(player.getX()/METRO_IN_PIXEL > ARRIVE_POSITION){
            playerPoints= playerPoints + player.getX() + pointsForSpeed();
            player.setAlive(false);
            game.setScreen(new GameOverScreen(game, playerPoints, true));
        }
    }

    private void moveTheCamera(float delta) {
        if(player.getX() > 150 && player.isAlive()) {
            stage.getCamera().translate(player.getLinearVelocityX() * delta * METRO_IN_PIXEL, 0, 0);
        }
    }

    public void deleteFlameIfNecessary(){
        if(flame.getX()>player.getX() && flame.isAlive()){
            flame.setAlive(false);
            flameCounter--;
        }
    }

    public void createFlameIfNecessary(){
        if(((player.getX()%15 < 1f ) && flameCounter==0) || ((player.getX()-flame.getX()>250))){
            flame.release();
            flame.remove();
            Texture flameTexture= game.getManager().get("flame-256.png");
            flame = new FlameEntity(world, flameTexture, new Vector2((player.getX()/METRO_IN_PIXEL)-1, (player.getY()/METRO_IN_PIXEL))/*, player.getSpeedX()*/);
            stage.addActor(flame);
            flameCounter++;
        }
    }

    public int pointsForSpeed(){
        if(player.getLinearVelocityX()< 5) return 1000;
        else if(player.getLinearVelocityX()< 5.5) return 3000;
        else if(player.getLinearVelocityX()< 6) return 5000;
        else return 6000;
    }

    private class GameContactListener implements ContactListener {

            private boolean areCollided( Contact contact, Object userA, Object userB){
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
                if(areCollided(contact, "player", "floor")){
                    player.setJumping(false);
                    flame.setJumping(false);
                }

                if(areCollided(contact, "player", "spike")){

                    if(player.isAlive()){
                        hitSound.play();
                    }

                    player.incrementCollisionsNumber();

                    // se volessi farlo morire dopo tot collisioni...
                    /*if(player.getCollisionsNumber()>2){
                        playerPoints= playerPoints + player.getX();
                        player.setAlive(false);
                        stage.addAction(
                                Actions.sequence(
                                        Actions.delay(2f),
                                        Actions.run(new Runnable(){
                                            @Override
                                            public void run() {
                                                game.setScreen(new GameOverScreen(game, playerPoints, false));
                                            }
                                        })
                                )
                        );
                    }*/
                }

                if(areCollided(contact, "player", "flame")){

                    flameSound.play();
                    bgMusic.stop();

                    playerPoints= playerPoints + player.getX();
                    player.setAlive(false);
                    flame.setAlive(false);

                    stage.addAction(
                            Actions.sequence(
                                    Actions.delay(2f),
                                    Actions.run(new Runnable(){
                                        @Override
                                        public void run() {
                                            game.setScreen(new GameOverScreen(game, playerPoints, false));
                                        }
                                    })
                            )
                    );
                }

                if(areCollided(contact, "flame", "spike")){
                    if(flame.isAlive() ) {
                        flame.setAlive(false);
                        flameCounter--;
                        player.incrementKilledFlamesNumber();
                        playerPoints= playerPoints + player.getLinearVelocityX()*KILLED_FLAME_POINTS;
                    }
                }

                if(areCollided(contact, "player", "wall")){

                    hitSound.play();

                    playerPoints= playerPoints + player.getX();
                    player.setAlive(false);

                    stage.addAction(
                            Actions.sequence(
                                    Actions.delay(2f),
                                    Actions.run(new Runnable(){
                                        @Override
                                        public void run() {
                                            game.setScreen(new GameOverScreen(game, playerPoints, false));
                                        }
                                    })
                            )
                    );
                }

                if(areCollided(contact, "flame", "wall")){
                    if(flame.isAlive() ) {
                        flame.setAlive(false);
                        flameCounter--;
                        player.incrementKilledFlamesNumber();
                        playerPoints= playerPoints + player.getLinearVelocityX()*KILLED_FLAME_POINTS;
                    }
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
