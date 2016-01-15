package com.flacoloide.studios;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.flacoloide.studios.Screens.PlayScreen;

public class MegaCatastrofeNuclear extends Game {
	public static final int V_WIDTH = 500;
	public static final int V_HEIGHT = 320;
	public static final float PPM = 100;

	public static final short GROUND_BIT = 1 ;
	public static final short PLAYER_BIT = 2;
	public static final short BRICK_BIT = 4;
	public static final short COIN_BIT = 8;
	public static final short BARRA_BIT = 64;
	public static final short OBJECT_BIT = 16;
	public static final short ENEMYY_BIT = 128;
	public static final short ENEMY_BIT = 68;
	public static final short OBJETO_BIT = 32;
	public static final short ITEM_BIT = 256;



	public BitmapFont font24;

	public PlayScreen level1;

	public SpriteBatch batch;
        
	public static AssetManager manager;




	@Override
	public void create () {




		//camera = new OrthographicCamera(Gdx.graphics.getWidth(),Gdx.graphics.getHeight());

		//camera.setToOrtho(false,Gdx.graphics.getWidth(),851);

		//camera.position.set(V_WIDTH/2,V_HEIGHT/2,0);
		//camera.setToOrtho(false, V_WIDTH, V_HEIGHT);


		batch = new SpriteBatch();

		manager = new AssetManager();
		manager.load("audio/music/mario_music.ogg", Music.class);
		manager.load("audio/sounds/coin.wav", Sound.class);
		manager.load("audio/sounds/bump.wav", Sound.class);
		manager.load("audio/sounds/mariodie.wav", Sound.class);
		manager.load("audio/sounds/gato.mp3", Sound.class);
		manager.load("audio/sounds/gatoDie.mp3", Sound.class);



		manager.finishLoading();



		//loadingScreen = new LoadingScreen(this);
		//splashScreen = new SplashScreen(this);
		//mainMenuScreen = new MainMenuScreen(this);
		//gameOverScreen = new GameOverScreen(this);


		this.setScreen(new PlayScreen(this, "Level1/Level1.tmx"));






	}


	@Override
	public void dispose() {
		super.dispose();
		batch.dispose();
//		font24.dispose();
		manager.dispose();
		//loadingScreen.dispose();
		//splashScreen.dispose();
		//mainMenuScreen.dispose();
		//level1.dispose();


	}

	@Override
	public void render () {
		super.render();
		if (Gdx.input.isKeyJustPressed(Input.Keys.ESCAPE)) {
			Gdx.app.exit();
		}
	}

}
