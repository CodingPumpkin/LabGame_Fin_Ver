package com.tildapumkins.game.lab.labgame.detect;

import android.graphics.Point;

import com.tildapumkins.game.lab.labgame.entities.GameSprite;
import com.tildapumkins.game.lab.labgame.entities.GameTile;
import com.tildapumkins.game.lab.labgame.maps.MapLayer;

public final class TouchDetector implements Sensitive {

    private int info;
    private GameSprite sprite;

    public TouchDetector(GameSprite sprite) {
        this.sprite = sprite;
        info = 0;
    }

    @Override
    public final boolean detect(Point p, MapLayer layer) {
        GameTile[][] tiles = layer.getTiles();
        if (tiles[p.y][p.x] == null &&
            tiles[p.y][p.x + 1] == null &&
            tiles[p.y + 1][p.x] == null &&
            tiles[p.y + 1][p.x + 1] == null) {
            return true;
        }
        return false;
    }

    @Override
    public final int detect(GameTile entity, boolean checkCore) {
        info = 0;
        if (checkCore) {
            if (this.sprite.getCore().left <= entity.getCore().left) {
                info |= Direct.LEFT.getValue();
            }
            if (this.sprite.getCore().top <= entity.getCore().top) {
                info |= Direct.TOP.getValue();
            }
            if (this.sprite.getCore().right >= entity.getCore().right) {
                info |= Direct.RIGHT.getValue();
            }
            if (this.sprite.getCore().bottom >= entity.getCore().bottom) {
                info |= Direct.BOTTOM.getValue();
            }
        } else {
            //RectF el = this.sprite.getLocation();
            if (this.sprite.getLocation().left <= entity.getLocation().left) {
                info |= Direct.LEFT.getValue();
            }
            if (this.sprite.getLocation().top <= entity.getLocation().top) {
                info |= Direct.TOP.getValue();
            }
            if (this.sprite.getLocation().right >= entity.getLocation().right) {
                info |= Direct.RIGHT.getValue();
            }
            if (this.sprite.getLocation().bottom >= entity.getLocation().bottom) {
                info |= Direct.BOTTOM.getValue();
            }
        }
        return info;
    }

    @Override
    public final int detect() {
        info = 0;
        if (sprite.getLocation().left <= sprite.getArea().left) {
            info |= Direct.LEFT.getValue();
        }
        if (sprite.getLocation().top <= sprite.getArea().top) {
            info |= Direct.TOP.getValue();
        }
        if (sprite.getLocation().right >= sprite.getArea().right) {
            info |= Direct.RIGHT.getValue();
        }
        if (sprite.getLocation().bottom >= sprite.getArea().bottom) {
            info |= Direct.BOTTOM.getValue();
        }
        return info;
    }
}
