package com.nikolam.spacerunner.Sprites;

import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.CircleShape;
import com.badlogic.gdx.physics.box2d.FixtureDef;
import com.badlogic.gdx.physics.box2d.World;
import com.badlogic.gdx.utils.Array;
import com.nikolam.spacerunner.Screens.PlayScreen;
import com.nikolam.spacerunner.SpaceRunner;

/**
 * Created by Nikola on 12/28/2015.
 */
public class Runner extends Sprite {
    public enum State {STANDING, RUNNING, JUMPING, FALLING};
    public State currentState;
    public State previousState;
    public World world;
    public Body body;
    private TextureRegion runnerStand;
    private TextureRegion runnerJump;
    private TextureRegion runnerFall;
    private Animation  runnerRun;

    private float stateTimer;
    private boolean runningRight;

    public Runner(World world, PlayScreen screen){
        super(screen.getAtlas().findRegion("runner"));
        this.world = world;
        currentState = State.STANDING;
        previousState = State.STANDING;
        stateTimer = 0;
        runningRight = true;

        Array<TextureRegion> frames = new Array<TextureRegion>();
        for (int i = 1; i < 7;i++){
            frames.add(new TextureRegion(getTexture(),i*82, 0,82,96));
        }
        runnerRun = new Animation(0.1f, frames);
        frames.clear();

        runnerJump = new TextureRegion(getTexture(),574,0,82,96);
        runnerFall = new TextureRegion(getTexture(),656,0,82,96);

        defineRunner();
        runnerStand = new TextureRegion(getTexture(),0,0,82,96);
        setBounds(0,0,52/SpaceRunner.PPM,64/SpaceRunner.PPM);
        setRegion(runnerStand);
    }

    public void update(float delta){
        setPosition(body.getPosition().x - getWidth()/2, body.getPosition().y - getHeight()/2);
        setRegion(getFrame(delta));
    }

    private TextureRegion getFrame(float delta){
        currentState = getState();

        TextureRegion region;
        switch (currentState){
            case  JUMPING:
                region = runnerJump;
                break;
            case RUNNING:
                region = runnerRun.getKeyFrame(stateTimer, true);
                break;
            case FALLING:
                region = runnerFall;
                break;
            case STANDING:
                default:
                    region = runnerStand;
                    break;
        }
        if((body.getLinearVelocity().x<0 || !runningRight) && !region.isFlipX()){
            region.flip(true,false);
            runningRight = false;
        }else if ((body.getLinearVelocity().x>0 || runningRight) && region.isFlipX()){
            region.flip(true,false);
            runningRight = true;
        }
        stateTimer = currentState == previousState ? stateTimer + delta : 0;
        previousState = currentState;
        return region;
    }

    public State getState(){
        if(body.getLinearVelocity().y>0)
            return State.JUMPING;
        else if(body.getLinearVelocity().y<0)
            return State.FALLING;
        else if(body.getLinearVelocity().x != 0)
            return State.RUNNING;
        else
            return State.STANDING;
    }

    public void defineRunner(){
        BodyDef bdef = new BodyDef();
        bdef.position.set(32/SpaceRunner.PPM,64/SpaceRunner.PPM);
        bdef.type = BodyDef.BodyType.DynamicBody;
        body = world.createBody(bdef);

        FixtureDef fdef = new FixtureDef();
        CircleShape shape = new CircleShape();
        shape.setRadius(32/SpaceRunner.PPM);

        fdef.shape = shape;
        body.createFixture(fdef);
    }
}
