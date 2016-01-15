package com.flacoloide.studios.Sprites.Enemies;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.CircleShape;
import com.badlogic.gdx.physics.box2d.FixtureDef;
import com.badlogic.gdx.physics.box2d.PolygonShape;
import com.badlogic.gdx.utils.Array;
import com.flacoloide.studios.MegaCatastrofeNuclear;
import com.flacoloide.studios.Screens.PlayScreen;

/**
 * Created by Coke on 25-11-2015.
 */
public class Gato extends Enemy {
    private float  stateTime;
    Texture walkSheet;
    TextureRegion[] walkFrames;

    private Animation walkAnimation;
    private boolean setToDestroy;
    private boolean destroyed;

    public Gato(PlayScreen screen, float x, float y) {
        super(screen, x, y);

     walkSheet = new Texture(Gdx.files.internal("gato.png"));
        TextureRegion[][] tmp = TextureRegion.split(walkSheet, walkSheet.getWidth()/1, walkSheet.getHeight()/3);              // #10
        walkFrames = new TextureRegion[1 * 3];
        int index = 0;
        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 1; j++) {
                walkFrames[index++] = tmp[i][j];
            }
        }

      walkAnimation = new Animation(0.12f, walkFrames);

      

        stateTime = 0;
        setBounds(getX(),getY(),40  / MegaCatastrofeNuclear.PPM, 40/ MegaCatastrofeNuclear.PPM);
        setToDestroy= false;
        destroyed= false;
    }


    public void update(float dt){
        stateTime += dt;
        if(setToDestroy && !destroyed){
            world.destroyBody(b2body);
            destroyed=true;
            stateTime = 0;

        }
        else if(!destroyed){
            b2body.setLinearVelocity(velocity);
            setPosition(b2body.getPosition().x - getWidth() / 2, b2body.getPosition().y - getHeight()/2);
            setRegion(walkAnimation.getKeyFrame(stateTime, true));
        }
    }

    @Override
    protected void defineEnemy() {
        BodyDef bdef = new BodyDef();
        bdef.position.set(getX(),getY());
        bdef.type = BodyDef.BodyType.DynamicBody;
        b2body = world.createBody(bdef);

        FixtureDef fdef = new FixtureDef();
        PolygonShape cuerpito = new PolygonShape();
        

        Vector2[] cuerpo = new Vector2[4];
        cuerpo[0] = new Vector2(-20, 10).scl(1 / MegaCatastrofeNuclear.PPM);
        cuerpo[1] = new Vector2(10, 10).scl(1 / MegaCatastrofeNuclear.PPM);
        cuerpo[2] = new Vector2(-20, -15).scl(1 / MegaCatastrofeNuclear.PPM);
        cuerpo[3] = new Vector2(10,-15).scl(1 / MegaCatastrofeNuclear.PPM);

        cuerpito.set(cuerpo);

       fdef.filter.categoryBits = MegaCatastrofeNuclear.ENEMY_BIT;
        fdef.filter.maskBits = MegaCatastrofeNuclear.GROUND_BIT |
                MegaCatastrofeNuclear.COIN_BIT |
                MegaCatastrofeNuclear.BRICK_BIT |
                MegaCatastrofeNuclear.ENEMY_BIT |
                MegaCatastrofeNuclear.OBJECT_BIT |
                MegaCatastrofeNuclear.PLAYER_BIT;

        fdef.shape = cuerpito;
        b2body.createFixture(fdef).setUserData(this);



    }

    public void draw(Batch batch){
        if (!destroyed)
          super.draw(batch);
    }

    @Override
    public void hit() {
        setToDestroy = true;
    }
}
