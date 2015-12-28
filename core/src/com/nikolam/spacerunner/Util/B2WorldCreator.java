package com.nikolam.spacerunner.Util;

import com.badlogic.gdx.maps.MapObject;
import com.badlogic.gdx.maps.objects.PolylineMapObject;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.ChainShape;
import com.badlogic.gdx.physics.box2d.FixtureDef;
import com.badlogic.gdx.physics.box2d.PolygonShape;
import com.badlogic.gdx.physics.box2d.Shape;
import com.badlogic.gdx.physics.box2d.World;
import com.nikolam.spacerunner.SpaceRunner;

/**
 * Created by Nikola on 12/28/2015.
 */
public class B2WorldCreator {
    private World world;
    private TiledMap map;
    public B2WorldCreator(World world, TiledMap map){
        // BodyDef bdef = new BodyDef();
        PolygonShape shape = new PolygonShape();
        FixtureDef fdef = new FixtureDef();


        for(MapObject object : map.getLayers().get(4).getObjects().getByType(PolylineMapObject.class)){
            Shape pshape;
            if(object instanceof PolylineMapObject){
                pshape = createPolyline((PolylineMapObject) object);
            }else{
                continue;
            }
            Body body;
            BodyDef bdef = new BodyDef();
            bdef.type = BodyDef.BodyType.StaticBody;
            body = world.createBody(bdef);
            body.createFixture(pshape, 1.0f);
            pshape.dispose();


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
