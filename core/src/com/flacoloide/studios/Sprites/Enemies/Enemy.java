package com.flacoloide.studios.Sprites.Enemies;

import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.World;
import com.flacoloide.studios.Screens.PlayScreen;

/**
 * Created by Coke on 25-11-2015.
 */
public abstract class Enemy extends Sprite {
    protected World world;
    protected PlayScreen screen;
    public Body b2body;
    public static Vector2 velocity;

    public Enemy(PlayScreen screen, float x, float y){
        this.world = screen.getWorld();
        this.screen = screen;
        setPosition(x, y);
        defineEnemy();
        velocity = new Vector2(-3, -2);
        b2body.setActive(false);
    }

    protected abstract void defineEnemy();
    public abstract void hit();
    public abstract void update(float dt);
    public void reverseVelocity(boolean x, boolean y){
        if(x)
            velocity.x = -velocity.x;
        if(y)
            velocity.y = -velocity.y;
    }

}

