package com.nikolam.spacerunner;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.nikolam.spacerunner.Screens.PlayScreen;

public class SpaceRunner extends Game {
	public static final int WIDTH = 800;
	public static final int HEIGHT = 480;
	public static final float PPM = 100;


	public SpriteBatch batch;


	@Override
	public void create () {
		batch = new SpriteBatch();
		setScreen(new PlayScreen(this));


	}

	@Override
	public void render () {
		super.render();
	}

	public void dispose(){
		batch.dispose();
	}
}
