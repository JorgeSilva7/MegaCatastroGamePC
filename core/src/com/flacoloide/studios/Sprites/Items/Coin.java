package com.flacoloide.studios.Sprites.Items;

import com.badlogic.gdx.maps.MapObject;
import com.badlogic.gdx.maps.tiled.TiledMapTileSet;
import com.flacoloide.studios.MegaCatastrofeNuclear;
import com.flacoloide.studios.Screens.PlayScreen;
import com.flacoloide.studios.Sprites.InteractiveTileObject;

/**
 * Created by Coke on 24-11-2015.
 */
public class Coin extends InteractiveTileObject {

    private static TiledMapTileSet tileSet;
    public Coin(PlayScreen screen, MapObject object){
        super(screen, object);
        fixture.setUserData(this);
        setCategoryFilter(MegaCatastrofeNuclear.COIN_BIT);
    }

    @Override
    public void onHit() {
    }
}
