package com.flacoloide.studios.Tools;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Contact;
import com.badlogic.gdx.physics.box2d.ContactImpulse;
import com.badlogic.gdx.physics.box2d.ContactListener;
import com.badlogic.gdx.physics.box2d.Fixture;
import com.badlogic.gdx.physics.box2d.Manifold;
import com.flacoloide.studios.MegaCatastrofeNuclear;
import com.flacoloide.studios.Personajes.Personaje;
import com.flacoloide.studios.Scenes.Hud;
import com.flacoloide.studios.Screens.GameOverScreen;
import com.flacoloide.studios.Screens.PlayScreen;
import com.flacoloide.studios.Sprites.Enemies.Enemy;
import com.flacoloide.studios.Sprites.InteractiveTileObject;
import com.flacoloide.studios.Sprites.Items.Objeto;

/**
 * Created by Coke on 25-11-2015.
 */
public class WorldContactListener implements ContactListener {
    public static int cont=1;
    public int contEnem=0;
    PlayScreen screen;
    MegaCatastrofeNuclear game;
    private Sound soundObjeto;
    public static Sound gatoDie;
    private static int score;

    @Override
    public void beginContact(Contact contact) {
        Fixture fixA  = contact.getFixtureA();
        Fixture fixB = contact.getFixtureB();
        gatoDie = MegaCatastrofeNuclear.manager.get("audio/sounds/gatoDie.mp3", Sound.class);
        soundObjeto = MegaCatastrofeNuclear.manager.get("audio/sounds/coin.wav", Sound.class);
        int cDef = fixA.getFilterData().categoryBits | fixB.getFilterData().categoryBits;

        if(fixA.getUserData() == "head" || fixB.getUserData() == "head"){
            Fixture head = fixA.getUserData() == "head" ? fixA : fixB;
            Fixture object = head == fixA ? fixB : fixA;

            if(object.getUserData() instanceof InteractiveTileObject){
                ((InteractiveTileObject) object.getUserData()).onHit();
            }
        }

        switch (cDef){
            case MegaCatastrofeNuclear.PLAYER_BIT |  MegaCatastrofeNuclear.OBJECT_BIT:
                nextLevel();
                Gdx.app.log("Game", "1");
                break;
            case  MegaCatastrofeNuclear.PLAYER_BIT |  MegaCatastrofeNuclear.BRICK_BIT:
                muere();
                Gdx.app.log("Game", "2");
                break;
            case  MegaCatastrofeNuclear.PLAYER_BIT |  MegaCatastrofeNuclear.ENEMY_BIT:
                if(contEnem==0) {
                    muere();
                    Gdx.app.log("Game", "3");
                    contEnem=1;
                }
                break;
            case  MegaCatastrofeNuclear.PLAYER_BIT |  MegaCatastrofeNuclear.COIN_BIT:
                PlayScreen.player.b2body.applyLinearImpulse(new Vector2(0.7f, 6.5f), PlayScreen.player.b2body.getWorldCenter(), true);
                break;
            case  MegaCatastrofeNuclear.BARRA_BIT|  MegaCatastrofeNuclear.PLAYER_BIT:
                ((Objeto)fixB.getUserData()).hit();
                soundObjeto.play();
                screen.hud.addScore(100);
                break;

        }
    }

    @Override
    public void endContact(Contact contact) {

    }

    public void muere(){
        PlayScreen.player.b2body.applyLinearImpulse(new Vector2(0, 0), PlayScreen.player.b2body.getWorldCenter(), true);
        screen.hud.bajarVida();
        if(screen.hud.level=="1" && screen.hud.vidas>0){
            screen.hud.score=0;
            screen.game.setScreen(new PlayScreen(screen.game, "Level1/Level1.tmx"));
        }
        if(screen.hud.level=="2" && screen.hud.vidas>0){
            screen.hud.score=score;
            screen.game.setScreen(new PlayScreen(screen.game, "Level1/Level2.tmx"));
        }
        if(screen.hud.vidas<1){
            PlayScreen.murio=true;
            Enemy.velocity= new Vector2(0,0);
            screen.game.setScreen(new GameOverScreen(game));
        }
    }
    public void nextLevel(){
        if(cont==1){
            cont++;
            score=screen.hud.score;
            screen.hud.level="2";
            screen.game.setScreen(new PlayScreen(screen.game, "Level1/Level2.tmx"));
        }
    }
    @Override
    public void preSolve(Contact contact, Manifold oldManifold) {

    }

    @Override
    public void postSolve(Contact contact, ContactImpulse impulse) {

    }

}
