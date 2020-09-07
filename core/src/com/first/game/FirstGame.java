package com.first.game;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;

public class FirstGame extends Game {

	private AssetManager manager;

	//public GameScreen gameScreen;
	//public GameOverScreen gameOverScreen;
	public MenuScreen menuScreen;
	public LoadingScreen loadingScreen;

	public AssetManager getManager() {

		return manager;
	}

	@Override
	public void create () {
		manager= new AssetManager();
		manager.load("flame-256.png", Texture.class);
		manager.load("square-rounded-256.png", Texture.class);
		manager.load("chicken-256.png", Texture.class);
		manager.load("square-256.png", Texture.class);
		manager.load("triangle-256.png", Texture.class);
		manager.load("triangle-rotate-256.png", Texture.class);
		manager.load("game-over.png", Texture.class);
		manager.load("logo.jpg", Texture.class);
		manager.load("flag-256.png", Texture.class);
		manager.load("victory.png", Texture.class);
		manager.load("thip-sound.ogg", Sound.class);
		manager.load("flame-sound.ogg", Sound.class);
		manager.load("metal-hit-sound.ogg", Sound.class);
		manager.load("game-music.ogg", Music.class);
		manager.load("victory-music.ogg", Music.class);
		manager.load("defeat-music.ogg", Music.class);

		loadingScreen= new LoadingScreen(this);
		setScreen(loadingScreen);
	}

	public void finishLoading(){
		//gameScreen= new GameScreen(this);
		//gameOverScreen=new GameOverScreen(this);
		menuScreen=new MenuScreen(this);
		setScreen(menuScreen);
	}

}
