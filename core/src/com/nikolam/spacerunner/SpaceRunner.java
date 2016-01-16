package com.nikolam.spacerunner;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.nikolam.spacerunner.Screens.MenuScreen;
import com.nikolam.spacerunner.Screens.PlayScreen;

public class SpaceRunner extends Game {
	public static final int WIDTH = 800;
	public static final int HEIGHT = 480;
	public static final float PPM = 100;

	public static final short GROUND_BIT = 1;
	public static final short RUNNER_BIT = 2;
	public static final short ENEMY_BIT = 4;
	public static final short OBJECT_BIT = 8;
	public static final short WIN_BIT = 16;
	public static final short DEATH_BIT = 32;




	public SpriteBatch batch;
	public BitmapFont font;
	public MenuScreen menuScreen;
	public PlayScreen playScreen;

	@Override
	public void create () {
		batch = new SpriteBatch();
		menuScreen = new MenuScreen(this);
		playScreen = new PlayScreen(this);
		setScreen(menuScreen);
		font = new BitmapFont(Gdx.files.internal("digi62.fnt"), false);

	}

	@Override
	public void render () {
		super.render();
	}

	public void dispose(){
		batch.dispose();
		menuScreen.dispose();
		playScreen.dispose();
	}
}
