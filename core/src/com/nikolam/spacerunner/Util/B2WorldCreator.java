package com.nikolam.spacerunner.Util;

import com.badlogic.gdx.maps.MapObject;
import com.badlogic.gdx.maps.objects.PolylineMapObject;
import com.badlogic.gdx.maps.objects.RectangleMapObject;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.ChainShape;
import com.badlogic.gdx.physics.box2d.FixtureDef;
import com.badlogic.gdx.physics.box2d.PolygonShape;
import com.badlogic.gdx.physics.box2d.Shape;
import com.badlogic.gdx.physics.box2d.World;
import com.nikolam.spacerunner.Screens.PlayScreen;
import com.nikolam.spacerunner.SpaceRunner;

/**
 * Created by Nikola on 12/28/2015.
 */
public class B2WorldCreator {

    public B2WorldCreator(PlayScreen screen){
        World world = screen.getWorld();
        TiledMap map = screen.getMap();
        BodyDef bdef = new BodyDef();
        Body body;
        PolygonShape shape = new PolygonShape();
        FixtureDef fdef = new FixtureDef();


        for(MapObject object : map.getLayers().get(4).getObjects().getByType(PolylineMapObject.class)){
            Shape pshape;
            if(object instanceof PolylineMapObject){
                pshape = createPolyline((PolylineMapObject) object);
                fdef.shape = pshape;
            }else{
                continue;
            }


            bdef.type = BodyDef.BodyType.StaticBody;
            body = world.createBody(bdef);

            fdef.filter.categoryBits = SpaceRunner.GROUND_BIT;
            fdef.filter.maskBits = SpaceRunner.RUNNER_BIT | SpaceRunner.ENEMY_BIT;

            body.createFixture(fdef);
            pshape.dispose();

        }

        for(MapObject object : map.getLayers().get(5).getObjects().getByType(RectangleMapObject.class)){
            Rectangle rect = ((RectangleMapObject) object).getRectangle();

            bdef.type = BodyDef.BodyType.StaticBody;
            bdef.position.set((rect.getX() + rect.getWidth() / 2) / SpaceRunner.PPM, (rect.getY() + rect.getHeight() / 2) / SpaceRunner.PPM);

            body = world.createBody(bdef);

            shape.setAsBox(rect.getWidth() / 2 / SpaceRunner.PPM, rect.getHeight() / 2 / SpaceRunner.PPM);
            fdef.shape = shape;
            fdef.filter.categoryBits = SpaceRunner.OBJECT_BIT;
            fdef.filter.maskBits = SpaceRunner.RUNNER_BIT | SpaceRunner.ENEMY_BIT;

            body.createFixture(fdef).setUserData(this);
        }

        for(MapObject object : map.getLayers().get(6).getObjects().getByType(RectangleMapObject.class)){
            Rectangle rect = ((RectangleMapObject) object).getRectangle();

            bdef.type = BodyDef.BodyType.StaticBody;
            bdef.position.set((rect.getX() + rect.getWidth() / 2) / SpaceRunner.PPM, (rect.getY() + rect.getHeight() / 2) / SpaceRunner.PPM);

            body = world.createBody(bdef);

            shape.setAsBox(rect.getWidth() / 2 / SpaceRunner.PPM, rect.getHeight() / 2 / SpaceRunner.PPM);
            fdef.shape = shape;
            fdef.filter.categoryBits = SpaceRunner.WIN_BIT;
            body.createFixture(fdef).setUserData(this);
        }

        for(MapObject object : map.getLayers().get(7).getObjects().getByType(RectangleMapObject.class)){
            Rectangle rect = ((RectangleMapObject) object).getRectangle();

            bdef.type = BodyDef.BodyType.StaticBody;
            bdef.position.set((rect.getX() + rect.getWidth() / 2) / SpaceRunner.PPM, (rect.getY() + rect.getHeight() / 2) / SpaceRunner.PPM);

            body = world.createBody(bdef);

            shape.setAsBox(rect.getWidth() / 2 / SpaceRunner.PPM, rect.getHeight() / 2 / SpaceRunner.PPM);
            fdef.shape = shape;
            fdef.filter.categoryBits = SpaceRunner.OBJECT_BIT;
            body.createFixture(fdef).setUserData(this);
        }

        for(MapObject object : map.getLayers().get(8).getObjects().getByType(RectangleMapObject.class)){
            Rectangle rect = ((RectangleMapObject) object).getRectangle();

            bdef.type = BodyDef.BodyType.StaticBody;
            bdef.position.set((rect.getX() + rect.getWidth() / 2) / SpaceRunner.PPM, (rect.getY() + rect.getHeight() / 2) / SpaceRunner.PPM);

            body = world.createBody(bdef);

            shape.setAsBox(rect.getWidth() / 2 / SpaceRunner.PPM, rect.getHeight() / 2 / SpaceRunner.PPM);
            fdef.shape = shape;
            fdef.filter.categoryBits = SpaceRunner.DEATH_BIT;
            body.createFixture(fdef).setUserData(this);
        }

    }

    private static ChainShape createPolyline(PolylineMapObject object) {
        float[] vertices = object.getPolyline().getTransformedVertices();
        Vector2[] worldVertices = new Vector2[vertices.length/2];

        for(int i = 0; i< worldVertices.length; i++){
            worldVertices[i] = new Vector2(vertices[i*2]/ SpaceRunner.PPM,vertices[i*2+1]/SpaceRunner.PPM);
        }
        ChainShape cs = new ChainShape();
        cs.createChain(worldVertices);
        return cs;

    }
}
