package com.nikolam.spacerunner.Screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.nikolam.spacerunner.SpaceRunner;

/**
 * Created by Nikola on 1/14/2016.
 */
public class MenuScreen implements Screen {

    private Texture bg;

    private FitViewport viewport;
    private OrthographicCamera camera;
    private SpaceRunner game;
    private Stage stage;

    TextButton.TextButtonStyle buttonStyle;
    TextButton button;
    TextButton buttonExit;
    Skin skin;
    BitmapFont font40;

    private TextureAtlas atlas;
    public MenuScreen(final SpaceRunner game){
        this.game = game;
        this.stage = new Stage();

        camera = new OrthographicCamera();
        viewport = new FitViewport(SpaceRunner.WIDTH/SpaceRunner.PPM, SpaceRunner.HEIGHT/SpaceRunner.PPM, camera);

        camera.position.set(viewport.getWorldWidth() / 2, viewport.getWorldHeight() / 2, 0);

        bg = new Texture("bg.png");
        atlas = new TextureAtlas("runnersprite.pack");

        skin = new Skin();

        skin.addRegions(atlas);

        font40 = new BitmapFont(Gdx.files.internal("digi40.fnt"), false);

        buttonStyle = new TextButton.TextButtonStyle();
        buttonStyle.up = skin.getDrawable("zbtn");
        buttonStyle.over = skin.getDrawable("zbtn");
        buttonStyle.down = skin.getDrawable("zbtn");
        buttonStyle.font = font40;

        button = new TextButton("PLAY", buttonStyle);

        button.setWidth(Gdx.graphics.getWidth() / 3);
        button.setHeight(Gdx.graphics.getHeight() / 6);
        button.setPosition((Gdx.graphics.getWidth() / 2) - (button.getWidth() / 2), (Gdx.graphics.getHeight() / 2) - (button.getHeight() / 2) - 40);
        button.addListener(new ClickListener(){
            @Override
            public void clicked(InputEvent event, float x, float y) {
                game.setScreen(game.playScreen);
            }
        });

        buttonExit = new TextButton("EXIT", buttonStyle);
        buttonExit.setWidth(Gdx.graphics.getWidth() / 3);
        buttonExit.setHeight(Gdx.graphics.getHeight() / 6);
        buttonExit.setPosition((Gdx.graphics.getWidth() / 2) - (button.getWidth() / 2), (Gdx.graphics.getHeight() / 2) - (button.getHeight() / 2) - 160);
        buttonExit.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                Gdx.app.exit();
            }
        });

        stage.addActor(button);
        stage.addActor(buttonExit);

        Gdx.input.setInputProcessor(stage);


    }

    public void update(float delta){
        stage.act(delta);
    }

    @Override
    public void show() {

    }

    @Override
    public void render(float delta) {
        Gdx.gl.glClearColor(0, 0, 0, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        update(delta);


        game.batch.begin();
        game.batch.draw(bg, 0, 0);
        game.font.draw(game.batch, "SPACE RUNNER", 184,362);
        game.batch.end();
        stage.draw();
    }

    @Override
    public void resize(int width, int height) {
        viewport.update(width,height);
    }

    @Override
    public void pause() {

    }

    @Override
    public void resume() {

    }

    @Override
    public void hide() {

    }

    @Override
    public void dispose() {

        font40.dispose();
        atlas.dispose();
        bg.dispose();
    }
}
