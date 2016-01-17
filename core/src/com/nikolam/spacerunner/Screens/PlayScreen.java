package com.nikolam.spacerunner.Screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.graphics.Camera;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.maps.MapObject;
import com.badlogic.gdx.maps.objects.PolylineMapObject;
import com.badlogic.gdx.maps.objects.RectangleMapObject;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TiledMapRenderer;
import com.badlogic.gdx.maps.tiled.TmxMapLoader;
import com.badlogic.gdx.maps.tiled.renderers.OrthogonalTiledMapRenderer;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.Box2DDebugRenderer;
import com.badlogic.gdx.physics.box2d.ChainShape;
import com.badlogic.gdx.physics.box2d.FixtureDef;
import com.badlogic.gdx.physics.box2d.PolygonShape;
import com.badlogic.gdx.physics.box2d.Shape;
import com.badlogic.gdx.physics.box2d.World;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.utils.viewport.FillViewport;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.badlogic.gdx.utils.viewport.StretchViewport;
import com.badlogic.gdx.utils.viewport.Viewport;
import com.nikolam.spacerunner.SpaceRunner;
import com.nikolam.spacerunner.Sprites.Enemy;
import com.nikolam.spacerunner.Sprites.Runner;
import com.nikolam.spacerunner.Util.B2WorldCreator;
import com.nikolam.spacerunner.Util.WorldContactListener;

/**
 * Created by Nikola on 12/27/2015.
 */
public class PlayScreen implements Screen {



    private Texture bg;

    private SpaceRunner game;
    private OrthographicCamera camera;
    private Viewport viewport;


    private TmxMapLoader mapLoader;
    private TiledMap map;
    private OrthogonalTiledMapRenderer renderer;

    private World world;
    private Box2DDebugRenderer b2dr;

    private TextureAtlas atlas;

    public Runner runner;
    private Enemy enemy;


    TextButton.TextButtonStyle buttonStyle;
    TextButton button;
    TextButton buttonExit;
    Skin skin;
    BitmapFont font40;

    public Music music;

    public PlayScreen(SpaceRunner game){
        atlas = new TextureAtlas("runnersprite.pack");

        this.game = game;

        bg = new Texture("bg.png");

        camera = new OrthographicCamera();
        viewport = new FitViewport(SpaceRunner.WIDTH/SpaceRunner.PPM, SpaceRunner.HEIGHT/SpaceRunner.PPM, camera);

        mapLoader = new TmxMapLoader();
        map = mapLoader.load("level1.tmx");
        renderer = new OrthogonalTiledMapRenderer(map, 1/SpaceRunner.PPM);

        camera.position.set(viewport.getWorldWidth() / 2, viewport.getWorldHeight() / 2, 0);

        world = new World(new Vector2(0,-8), true);

        b2dr = new Box2DDebugRenderer();


        new B2WorldCreator(this);

        runner = new Runner(this);

        world.setContactListener(new WorldContactListener());

        enemy = new Enemy(this,20.512f, .64f);

        skin = new Skin();

        skin.addRegions(atlas);

        music = game.manager.get("audio/song2.ogg", Music.class);
        music.setLooping(true);
        music.play();

    }

    public TextureAtlas getAtlas(){
        return atlas;
    }




    @Override
    public void show() {

    }

    public void handleInput(float delat){
        if (Gdx.input.isKeyJustPressed(Input.Keys.UP)) {
            if (runner.body.getLinearVelocity().y == 0) {

                runner.body.applyLinearImpulse(new Vector2(0, 4f), runner.body.getWorldCenter(), true);
            }

        }
        if (Gdx.input.isKeyPressed(Input.Keys.RIGHT) && runner.body.getLinearVelocity().x <= 4){
            runner.body.applyLinearImpulse(new Vector2(0.2f,0), runner.body.getWorldCenter(), true);
        } if (Gdx.input.isKeyPressed(Input.Keys.LEFT) && runner.body.getLinearVelocity().x >= -4){
            runner.body.applyLinearImpulse(new Vector2(-0.2f,0), runner.body.getWorldCenter(), true);
        }
    }



    public void update(float delta){

        if(runner.currentState == Runner.State.DEATH || runner.currentState == Runner.State.WIN) {
            game.setScreen(game.endScreen);
        }


        handleInput(delta);

        world.step(1 / 60f, 6, 2);

        camera.position.x = runner.body.getPosition().x;

        float startX = camera.viewportWidth/2;
        float startY = camera.viewportHeight/2;
        boundary(camera, startX, startY, 32 - (4 * 0.32f) - startX * 2, 480);

        runner.update(delta);
        enemy.update(delta);
        camera.update();
        renderer.setView(camera);
    }

    @Override
    public void render(float delta) {
        update(delta);

        Gdx.gl.glClearColor(0, 0, 0, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        renderer.render();

        game.batch.setProjectionMatrix(camera.combined);
        game.batch.begin();
        runner.draw(game.batch);
        enemy.draw(game.batch);
        game.batch.end();


        //b2dr.render(world, camera.combined);

    }

    public TiledMap getMap(){
        return map;
    }

    public World getWorld(){
        return world;
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

    public static void boundary(Camera camera, float startX, float startY, float with, float height) {
        Vector3 position = camera.position;

        if(position.x < startX){
            position.x = startX;
        }
        if(position.y < startY){
            position.y = startY;
        }
        if(position.x >startX + with){
            position.x = startX + with;
        }
        if(position.y > startY + height){
            position.y = startY + height;
        }

        camera.position.set(position);
        camera.update();
    }

    @Override
    public void dispose() {

        map.dispose();
        renderer.dispose();
        b2dr.dispose();
    }
}
