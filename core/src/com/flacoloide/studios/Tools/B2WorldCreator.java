package com.flacoloide.studios.Tools;

import com.badlogic.gdx.maps.MapObject;
import com.badlogic.gdx.maps.objects.RectangleMapObject;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.FixtureDef;
import com.badlogic.gdx.physics.box2d.PolygonShape;
import com.badlogic.gdx.physics.box2d.World;
import com.badlogic.gdx.utils.Array;
import com.flacoloide.studios.MegaCatastrofeNuclear;
import com.flacoloide.studios.Screens.PlayScreen;
import com.flacoloide.studios.Sprites.Items.Brick;
import com.flacoloide.studios.Sprites.Items.Coin;
import com.flacoloide.studios.Sprites.Enemies.Gato;
import com.flacoloide.studios.Sprites.Items.Meta;
import com.flacoloide.studios.Sprites.Items.Objeto;

/**
 * Created by Coke on 24-11-2015.
 */
public class B2WorldCreator {
    private Array<Gato> gatos;
    private Array<Objeto> objetos;

    public B2WorldCreator(PlayScreen screen) {

        World world = screen.getWorld();
        TiledMap map = screen.getMap();
        BodyDef bdef = new BodyDef();
        PolygonShape shape = new PolygonShape();
        FixtureDef fdef = new FixtureDef();
        Body body;

        //Crea tierra cuerpos/accesorios
        for (MapObject object : map.getLayers().get(2).getObjects().getByType(RectangleMapObject.class)) {
            Rectangle rect = ((RectangleMapObject) object).getRectangle();

            bdef.type = BodyDef.BodyType.StaticBody;
            bdef.position.set((rect.getX() + rect.getWidth() / 2) / MegaCatastrofeNuclear.PPM, (rect.getY() + rect.getHeight() / 2) / MegaCatastrofeNuclear.PPM);

            body = world.createBody(bdef);

            shape.setAsBox(rect.getWidth() / 2 / MegaCatastrofeNuclear.PPM, rect.getHeight() / 2 / MegaCatastrofeNuclear.PPM);
            fdef.shape = shape;
            body.createFixture(fdef);
        }

        //crea tubos cuerpos/accesorios
        for (MapObject object : map.getLayers().get(3).getObjects().getByType(RectangleMapObject.class)) {
            Rectangle rect = ((RectangleMapObject) object).getRectangle();


            new Meta(screen, object);
        }

        //crea bloques
        for (MapObject object : map.getLayers().get(5).getObjects().getByType(RectangleMapObject.class)) {
            Rectangle rect = ((RectangleMapObject) object).getRectangle();


            new Brick(screen, object);
        }


        //crea monedas
        for (MapObject object : map.getLayers().get(4).getObjects().getByType(RectangleMapObject.class)) {
            Rectangle rect = ((RectangleMapObject) object).getRectangle();

            new Coin(screen, object);
        }

        objetos = new Array<Objeto>();
        for (MapObject object : map.getLayers().get(7).getObjects().getByType(RectangleMapObject.class)) {
            Rectangle rect = ((RectangleMapObject) object).getRectangle();
            objetos.add(new Objeto(screen, rect.getX() / MegaCatastrofeNuclear.PPM, rect.getY() / MegaCatastrofeNuclear.PPM));

        }

        //create all goombas
        gatos = new Array<Gato>();
        for (MapObject object : map.getLayers().get(6).getObjects().getByType(RectangleMapObject.class)) {
            Rectangle rect = ((RectangleMapObject) object).getRectangle();
            gatos.add(new Gato(screen, rect.getX() / MegaCatastrofeNuclear.PPM, rect.getY() / MegaCatastrofeNuclear.PPM));

        }

    }

    public Array<Gato> getGatos() {
            return gatos;
        }
    public Array<Objeto> getObjetos() {
        return objetos;
    }
}

