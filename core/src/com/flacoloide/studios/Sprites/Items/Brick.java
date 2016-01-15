package com.flacoloide.studios.Sprites.Items;

import com.badlogic.gdx.maps.MapObject;
import com.flacoloide.studios.MegaCatastrofeNuclear;
import com.flacoloide.studios.Screens.PlayScreen;
import com.flacoloide.studios.Sprites.InteractiveTileObject;

/**
 * Created by Coke on 24-11-2015.
 */
public class Brick extends InteractiveTileObject {

    public Brick(PlayScreen screen, MapObject object){
        super(screen, object);
        fixture.setUserData(this);
        setCategoryFilter(MegaCatastrofeNuclear.BRICK_BIT);
    }



    @Override
    public void onHit() {

    }


}
