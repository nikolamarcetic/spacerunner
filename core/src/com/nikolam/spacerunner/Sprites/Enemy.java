package com.nikolam.spacerunner.Sprites;

import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.CircleShape;
import com.badlogic.gdx.physics.box2d.FixtureDef;
import com.badlogic.gdx.physics.box2d.World;
import com.badlogic.gdx.utils.Array;
import com.nikolam.spacerunner.Screens.PlayScreen;
import com.nikolam.spacerunner.SpaceRunner;

/**
 * Created by Nikola on 1/5/2016.
 */
public class Enemy extends Sprite {
    protected World world;
    protected Screen screen;

    private float stateTime;
    private Animation levAnimation;
    private Array<TextureRegion> frames;

    public Body body;
    public Vector2 velocity;

    public Enemy(PlayScreen screen, float x, float y){
        this.world = screen.getWorld();
        this.screen = screen;
        setPosition(x,y);
        frames = new Array<TextureRegion>();
        for(int i = 0; i < 6; i++){
            frames.add(new TextureRegion(screen.getAtlas().findRegion("alien"), i*43, 0, 43, 64));
        }
        levAnimation = new Animation(0.3f, frames);
        stateTime = 0;
        setBounds(getX(),getY(), 43/SpaceRunner.PPM, 64/SpaceRunner.PPM);
        defineEnemy();
        velocity = new Vector2(1,0);


    }

    public void reverseVelocity(boolean x, boolean y){
        if (x)
            velocity.x = -velocity.x;
        if(y)
            velocity.y = -velocity.y;
    }

    public void update(float delta){
        stateTime += delta;
        setPosition(body.getPosition().x - getWidth()/2, body.getPosition().y - getHeight()/2);
        setRegion(levAnimation.getKeyFrame(stateTime, true));
        body.setLinearVelocity(velocity);
    }

    public void defineEnemy(){
        BodyDef bdef = new BodyDef();
        bdef.position.set(getX(),getY());
        bdef.type = BodyDef.BodyType.DynamicBody;
        body = world.createBody(bdef);


        FixtureDef fdef = new FixtureDef();
        CircleShape shape = new CircleShape();
        shape.setRadius(16/SpaceRunner.PPM);
        fdef.filter.categoryBits = SpaceRunner.ENEMY_BIT;
        fdef.filter.maskBits = SpaceRunner.GROUND_BIT | SpaceRunner.OBJECT_BIT | SpaceRunner.RUNNER_BIT;

        fdef.shape = shape;

        body.createFixture(fdef);
        shape.setPosition(new Vector2(0, -20 / SpaceRunner.PPM));
        shape.setRadius(14 / SpaceRunner.PPM);
        fdef.shape = shape;



        body.createFixture(fdef).setUserData(this);
    }
}
