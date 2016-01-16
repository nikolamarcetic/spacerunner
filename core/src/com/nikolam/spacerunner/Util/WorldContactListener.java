package com.nikolam.spacerunner.Util;

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
                    ((Runner)fixA.getUserData()).gotHit();
                }

                else if(fixB.getFilterData().categoryBits == SpaceRunner.RUNNER_BIT){
                    ((Runner)fixB.getUserData()).gotHit();
                }
                break;
            case SpaceRunner.ENEMY_BIT | SpaceRunner.OBJECT_BIT:
                if(fixA.getFilterData().categoryBits == SpaceRunner.ENEMY_BIT){
                    ((Enemy)fixA.getUserData()).reverseVelocity(true, false);
                }
                else if(fixB.getFilterData().categoryBits == SpaceRunner.ENEMY_BIT){
                    ((Enemy)fixB.getUserData()).reverseVelocity(true,false);
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
