package com.nikolam.spacerunner.Util;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.physics.box2d.Contact;
import com.badlogic.gdx.physics.box2d.ContactImpulse;
import com.badlogic.gdx.physics.box2d.ContactListener;
import com.badlogic.gdx.physics.box2d.Fixture;
import com.badlogic.gdx.physics.box2d.Manifold;
import com.nikolam.spacerunner.SpaceRunner;
import com.nikolam.spacerunner.Sprites.Enemy;
import com.nikolam.spacerunner.Sprites.Runner;

/**
 * Created by Nikola on 1/4/2016.
 */
public class WorldContactListener implements ContactListener {
    @Override
    public void beginContact(Contact contact) {
        Fixture fixA = contact.getFixtureA();
        Fixture fixB = contact.getFixtureB();

        int cDef = fixA.getFilterData().categoryBits | fixB.getFilterData().categoryBits;

        switch (cDef){
            case SpaceRunner.RUNNER_BIT | SpaceRunner.ENEMY_BIT:
                if(fixA.getFilterData().categoryBits == SpaceRunner.RUNNER_BIT){
                    try{
                        ((Runner)fixA.getUserData()).runnerDie();
                    }catch (Exception e){}

                }
                else if(fixB.getFilterData().categoryBits == SpaceRunner.RUNNER_BIT){

                    try{
                        ((Runner)fixB.getUserData()).runnerDie();
                    }catch (Exception e){}

                }
                break;
            case SpaceRunner.ENEMY_BIT | SpaceRunner.OBJECT_BIT:
                if(fixA.getFilterData().categoryBits == SpaceRunner.ENEMY_BIT){
                    ((Enemy)fixA.getUserData()).reverseVelocity(true, false);
                }
                else {

                    try{
                        ((Enemy)fixB.getUserData()).reverseVelocity(true,false);
                    }catch (Exception e){}
                   // ((Enemy)fixB.getUserData()).reverseVelocity(true,false);
                }
                break;
            case SpaceRunner.RUNNER_BIT | SpaceRunner.DEATH_BIT:
                if(fixA.getFilterData().categoryBits == SpaceRunner.RUNNER_BIT){
                    ((Runner)fixA.getUserData()).runnerDie();
                    Gdx.app.log("Die", "DIe");

                }
                else {
                    Gdx.app.log("Die", "DIe");
                    ((Runner)fixB.getUserData()).runnerDie();
                }
                break;

            case SpaceRunner.RUNNER_BIT | SpaceRunner.WIN_BIT:
                if(fixA.getFilterData().categoryBits == SpaceRunner.RUNNER_BIT){
                    ((Runner)fixA.getUserData()).runnerWin();
                    Gdx.app.log("Die", "DIe");

                }
                else {
                    try{
                        ((Runner)fixB.getUserData()).runnerWin();
                        Gdx.app.log("WINs", ((Runner) fixB.getUserData()).currentState.toString());
                    }catch (Exception e){}


                }
                break;
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
