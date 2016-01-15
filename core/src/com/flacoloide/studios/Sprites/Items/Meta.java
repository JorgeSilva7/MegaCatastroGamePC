package com.flacoloide.studios.Sprites.Items;

import com.badlogic.gdx.maps.MapObject;
import com.flacoloide.studios.MegaCatastrofeNuclear;
import com.flacoloide.studios.Screens.PlayScreen;
import com.flacoloide.studios.Sprites.InteractiveTileObject;

/**
 * Created by Coke on 18-12-2015.
 */
public class Meta extends InteractiveTileObject {

    public Meta(PlayScreen screen, MapObject object){
        super(screen, object);
        fixture.setUserData(this);
        setCategoryFilter(MegaCatastrofeNuclear.OBJECT_BIT);
    }

    @Override
    public void onHit() {

    }
}
