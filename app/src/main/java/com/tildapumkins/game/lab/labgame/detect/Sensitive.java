package com.tildapumkins.game.lab.labgame.detect;

import com.tildapumkins.game.lab.labgame.entities.GameTile;
import com.tildapumkins.game.lab.labgame.maps.MapLayer;

import android.graphics.Point;

public interface Sensitive {

    //boolean detect(float x, float y);
    boolean detect(Point p, MapLayer layer);
    int detect(GameTile entity, boolean checkCore);
    int detect();
}

