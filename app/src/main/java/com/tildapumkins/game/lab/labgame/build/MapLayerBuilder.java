package com.tildapumkins.game.lab.labgame.build;

import android.content.Context;
import android.graphics.Point;

import com.tildapumkins.game.lab.labgame.entities.GameTile;
import com.tildapumkins.game.lab.labgame.images.GameImage;
import com.tildapumkins.game.lab.labgame.maps.MapLayer;

import org.jetbrains.annotations.NotNull;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

public final class MapLayerBuilder {

    @NotNull
    public final MapLayer build(Context context, GameImage[] images,
                                @NotNull Point sizeInTiles, String filePath) throws IOException {
        InputStreamReader inReader;
        BufferedReader reader;
        GameTile[][] tiles = new GameTile[sizeInTiles.y][sizeInTiles.x];
        int y = 0;
        String s;
        InputStream inStream = context.getResources().getAssets().open(filePath);
        try {
            inReader = new InputStreamReader(inStream);
            try {
                reader = new BufferedReader(inReader);
                try {
                    while ((s = reader.readLine()) != null && y < sizeInTiles.y) {
                        for (int x = 0; x < sizeInTiles.x; x++) {
                            char ch = (x < s.length()) ? s.charAt(x) : ' ';
                            tiles[y][x] = null;
                            switch (ch) {
                                case '#':
                                case '1':
                                    GameImage image = findImage(1, images);
                                    assert image != null;
                                    tiles[y][x] =
                                            new GameTile(
                                                    0, image.getBitmap(),
                                                    image.getFrame(0), -1);
                                    tiles[y][x].set_to_cell(x, y);
                                    break;
                            }
                        }
                        y++;
                    }
                } finally {
                    reader.close();
                }
            } finally {
                inReader.close();
            }
        } finally {
            inStream.close();
        }
        return new MapLayer(tiles);
    }

    private GameImage findImage(int id, GameImage[] images) {
        for (GameImage image : images) {
            if (image.getId() == id) return image;
        }
        return null;
    }

}
