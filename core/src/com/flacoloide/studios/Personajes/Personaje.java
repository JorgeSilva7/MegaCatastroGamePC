package com.flacoloide.studios.Personajes;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.EdgeShape;
import com.badlogic.gdx.physics.box2d.FixtureDef;
import com.badlogic.gdx.physics.box2d.PolygonShape;
import com.badlogic.gdx.physics.box2d.World;
import com.flacoloide.studios.MegaCatastrofeNuclear;
import com.flacoloide.studios.Screens.PlayScreen;

/**
 * Created by Coke on 23-12-2015.
 */
public class Personaje extends Sprite {
    private static final int        FRAME_COLS = 4;
    private static final int        FRAME_ROWS = 1;

    Animation walkAnimation;
    Texture walkSheet;
    Texture stand;
    Texture jump;
    TextureRegion[] walkFrames;
    TextureRegion [] standFrames;
    TextureRegion [] jumpFrames;
    SpriteBatch spriteBatch;
    TextureRegion  currentFrame;


    float stateTime;

    public enum State {FALLING, JUMPING, STANDING, RUNNING };
    public State currentState;
    public State previousState;
    public World world;

    public static float x=1;


    public Body b2body;
    private Animation runAnimation;
    private Animation standAnimation;
    private Animation jumpAnimation;
    private float stateTimer;
    private boolean  runningRight;

    public Personaje(PlayScreen screen){
        this.world = screen.getWorld();
        currentState = State.STANDING;
        previousState = State.STANDING;
        runningRight = true;        stateTimer = 0;


        walkSheet = new Texture(Gdx.files.internal("run.png"));
        stand = new Texture(Gdx.files.internal("stand.png"));
        jump = new Texture(Gdx.files.internal("jump.png"));

        TextureRegion[][] tmp = TextureRegion.split(walkSheet, walkSheet.getWidth()/FRAME_COLS, walkSheet.getHeight()/FRAME_ROWS);              // #10
        walkFrames = new TextureRegion[FRAME_COLS * FRAME_ROWS];
        int index = 0;
        for (int i = 0; i < FRAME_ROWS; i++) {
            for (int j = 0; j < FRAME_COLS; j++) {
                walkFrames[index++] = tmp[i][j];
            }
        }

        TextureRegion[][] tmpp = TextureRegion.split(stand, stand.getWidth()/1, stand.getHeight()/1);
        standFrames = new TextureRegion[1 * 1];
        int indexx = 0;
        for (int i = 0; i < 1; i++) {
            for (int j = 0; j < 1; j++) {
                standFrames[indexx++] = tmpp[i][j];
            }
        }

        standAnimation = new Animation(0.00f, standFrames);

        runAnimation = new Animation(0.1f, walkFrames);
        spriteBatch = new SpriteBatch();
        stateTime = 0f;


        TextureRegion[][] tmppp = TextureRegion.split(jump, jump.getWidth()/1, jump.getHeight()/1);
        jumpFrames = new TextureRegion[1 * 1];
        int indexxx = 0;
        for (int i = 0; i < 1; i++) {
            for (int j = 0; j < 1; j++) {
                jumpFrames[indexxx++] = tmppp[i][j];
            }
        }

        jumpAnimation = new Animation(0.1f,jumpFrames);

        defineMario();
        //53,70
        setBounds(0, 0, 53 / MegaCatastrofeNuclear.PPM, 70 / MegaCatastrofeNuclear.PPM);
        //setRegion(Stand);

    }




    public void update(float dt){
        setPosition(b2body.getPosition().x-(getWidth()/2), b2body.getPosition().y-getHeight()/2);
        setRegion(getFrames(dt));

    }


    public TextureRegion getFrames(float dt){
        currentState = getState();

        TextureRegion region;
        switch (currentState){
            case JUMPING:
                region = jumpAnimation.getKeyFrame(stateTimer);
                break;
            case RUNNING:
                region = runAnimation.getKeyFrame(stateTimer, true);
                break;
            case FALLING:
            case STANDING:
            default:
                region = standAnimation.getKeyFrame(stateTimer, true);
                break;
        }

        if ((b2body.getLinearVelocity().x < 0 || !runningRight) && !region.isFlipX()){
            region.flip(true, false);
            runningRight= false;
        }
        else if ((b2body.getLinearVelocity().x > 0 || runningRight) && region.isFlipX()){
            region.flip(true, false);
            runningRight= true;
        }

        stateTimer = currentState == previousState ? stateTimer +dt :0;
        previousState = currentState;
        return region;
    }

    public State getState(){
        if(b2body.getLinearVelocity().y>0 || (b2body.getLinearVelocity().y < 0 && previousState == State.JUMPING))
            return State.JUMPING;
        else if(b2body.getLinearVelocity().y<0)
            return State.FALLING;
        else if(b2body.getLinearVelocity().x != 0)
            return State.RUNNING;
        else
            return State.STANDING;
    }

    public void defineMario(){
        BodyDef bdef = new BodyDef();
        bdef.position.set(1,1);
        bdef.type = BodyDef.BodyType.DynamicBody;
        b2body = world.createBody(bdef);

        FixtureDef fdef = new FixtureDef();
        PolygonShape cuerpito = new PolygonShape();

        Vector2[] cuerpo = new Vector2[4];
        cuerpo[0] = new Vector2(-10, 20).scl(1 / MegaCatastrofeNuclear.PPM);
        cuerpo[1] = new Vector2(5, 20).scl(1 / MegaCatastrofeNuclear.PPM);
        cuerpo[2] = new Vector2(-10, -25).scl(1 / MegaCatastrofeNuclear.PPM);
        cuerpo[3] = new Vector2(5,-25).scl(1 / MegaCatastrofeNuclear.PPM);

        cuerpito.set(cuerpo);

        fdef.filter.categoryBits = MegaCatastrofeNuclear.PLAYER_BIT;
        fdef.filter.maskBits = MegaCatastrofeNuclear.GROUND_BIT |
                MegaCatastrofeNuclear.COIN_BIT |
                MegaCatastrofeNuclear.BRICK_BIT |
                MegaCatastrofeNuclear.ENEMY_BIT |
                MegaCatastrofeNuclear.OBJECT_BIT |
                MegaCatastrofeNuclear.ENEMYY_BIT |
                MegaCatastrofeNuclear.ITEM_BIT;

        fdef.shape = cuerpito;
        b2body.createFixture(fdef);
    }
    public float getStateTimer(){
        return stateTimer;
    }


}
