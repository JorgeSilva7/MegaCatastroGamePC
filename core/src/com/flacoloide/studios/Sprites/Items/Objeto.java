package com.flacoloide.studios.Sprites.Items;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.CircleShape;
import com.badlogic.gdx.physics.box2d.FixtureDef;
import com.badlogic.gdx.utils.Array;
import com.flacoloide.studios.MegaCatastrofeNuclear;
import com.flacoloide.studios.Screens.PlayScreen;
import com.flacoloide.studios.Sprites.Enemies.Enemy;

/**
 * Created by Coke on 18-12-2015.
 */
public class Objeto extends Enemy {
    private float  stateTime;
    public  boolean setToDestroy;
    public  boolean destroyed;
    private Animation Stand;
    Texture stand;
    TextureRegion [] standFrames;

    public Objeto(PlayScreen screen, float x, float y) {
        super(screen, x, y);
        stand = new Texture(Gdx.files.internal("barritaa.png"));
        TextureRegion[][] tmpp = TextureRegion.split(stand, stand.getWidth()/1, stand.getHeight()/1);              // #10
        standFrames = new TextureRegion[1 * 1];
        int indexx = 0;
        for (int i = 0; i < 1; i++) {
            for (int j = 0; j < 1; j++) {
                standFrames[indexx++] = tmpp[i][j];
            }
        }
        Stand = new Animation(0,standFrames);
        stateTime = 0;

        setBounds(0, 0, 42 / MegaCatastrofeNuclear.PPM, 42 / MegaCatastrofeNuclear.PPM);
        setToDestroy= false;
        destroyed= false;
        b2body.setGravityScale(0);


    }


    public void update(float dt){
        stateTime += dt;
        if(setToDestroy && !destroyed){
            world.destroyBody(b2body);
            destroyed=true;
            setRegion(Stand.getKeyFrame(stateTime, true));
            stateTime=0;
        }
        else if(!destroyed){
            setPosition(b2body.getPosition().x - getWidth() / 2, b2body.getPosition().y - getHeight() / 2);
            setRegion(Stand.getKeyFrame(stateTime, true));

        }
    }

    @Override
    protected void defineEnemy() {
        BodyDef bdef = new BodyDef();
        bdef.position.set(getX(),getY());
        bdef.type = BodyDef.BodyType.DynamicBody;
        b2body = world.createBody(bdef);

        FixtureDef fdef = new FixtureDef();
        CircleShape shape = new CircleShape();
        shape.setRadius(3 / MegaCatastrofeNuclear.PPM);

        fdef.filter.categoryBits = MegaCatastrofeNuclear.BARRA_BIT;
        fdef.filter.maskBits = MegaCatastrofeNuclear.GROUND_BIT |
                MegaCatastrofeNuclear.COIN_BIT |
                MegaCatastrofeNuclear.BRICK_BIT |
                MegaCatastrofeNuclear.ENEMY_BIT |
                MegaCatastrofeNuclear.OBJECT_BIT |
                MegaCatastrofeNuclear.BARRA_BIT |
                MegaCatastrofeNuclear.PLAYER_BIT;

        fdef.shape = shape;
        b2body.createFixture(fdef).setUserData(this);

    }

    public void draw(Batch batch){
        if (!destroyed)
            super.draw(batch);
    }

    @Override
    public void hit() {
        this.setToDestroy = true;
    }
}
