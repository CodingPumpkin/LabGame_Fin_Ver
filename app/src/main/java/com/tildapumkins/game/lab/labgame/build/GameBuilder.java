package com.tildapumkins.game.lab.labgame.build;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Point;

import com.tildapumkins.game.lab.labgame.GameState;
import com.tildapumkins.game.lab.labgame.R;
import com.tildapumkins.game.lab.labgame.animation.Animator;
import com.tildapumkins.game.lab.labgame.detect.TouchDetector;
import com.tildapumkins.game.lab.labgame.entities.GameSprite;
import com.tildapumkins.game.lab.labgame.images.GameImage;
import com.tildapumkins.game.lab.labgame.maps.Camera;
import com.tildapumkins.game.lab.labgame.maps.GameMap;
import com.tildapumkins.game.lab.labgame.maps.MapLayer;

import java.io.IOException;

public class GameBuilder {

//    GameState gState;

    public GameState build (Context context, int screenWidth, int screenHeight) throws IOException {
    //    GameState gState = new GameState(context, screenWidth, screenHeight);
        //gState.setBitmaps(loadBitmaps(context));
        Point screenSize = new Point(screenWidth, screenHeight);
        Bitmap[] bitmaps = loadBitmaps(context);
        //gState.setImages(createImages(gState.getBitmaps()));
        GameImage[] images = createImages(bitmaps);
        MapLayer[] layers = new MapLayer[1];
        MapLayerBuilder mapLayerBuilder = new MapLayerBuilder();
        layers[0] = mapLayerBuilder.build(context, images, new Point(500, 500), "dungeon.txt");
        layers[0].setVisible(true);
        //gState.setGameMap(new GameMap(layers));
        GameMap gameMap = new GameMap(layers);
        //gState.setAnimators(createAnimators(gState.getImages()));
        Animator[] animators = createAnimators(images);
        //gState.setGameSprites(createSprites(gState));
        GameSprite[] sprites = createSprites(images, animators, gameMap);
        //gState.setCamera(createCamera(gState));
        Camera camera = createCamera(screenSize, sprites[2]);
        return new GameState(
                context, bitmaps, images, animators, sprites, 2,
                screenSize, camera, gameMap);
    }

    private Bitmap[] loadBitmaps(Context context) {
        Bitmap[] bitmaps = new Bitmap[3];
        bitmaps[0] = BitmapFactory.decodeResource(context.getResources(), R.drawable.ball_64);
        bitmaps[1] = BitmapFactory.decodeResource(context.getResources(), R.drawable.chocolate_64);
        bitmaps[2] = BitmapFactory.decodeResource(context.getResources(), R.drawable.coin3_64);
        bitmaps[2] = BitmapFactory.decodeResource(context.getResources(), R.drawable.coin3_64);
        return bitmaps;
    }

    private GameImage[] createImages(Bitmap[] bitmaps) {
        GameImage[] images = new GameImage[3];
        GameImageBuilder builder = new GameImageBuilder();
        images[0] = builder.build(100, bitmaps[0], 64, 64, 1);
        images[1] = builder.build(1, bitmaps[1], 64, 64, 1);
        images[2] = builder.build(2, bitmaps[2], 64, 64, 6);
        return images;
    }

    private Animator[] createAnimators(GameImage[] images) {
        Animator[] animators = new Animator[1];
        animators[0] = new Animator(images[2].getBitmap(), images[2].getAllFrames(),120);
        return animators;
    }

    private GameSprite[] createSprites(GameImage[] images, Animator[] animators, GameMap gameMap) {
        GameSprite[] gameSprites = new GameSprite[3];

        gameSprites[0] = new GameSprite(1, images[2], animators[0], -1, null);
        gameSprites[0].set_to_cell(253, 253);
        gameSprites[0].start();

        gameSprites[1] = new GameSprite(1, images[2], animators[0], -1, null);
        gameSprites[1].set_to_cell(266, 264);
        gameSprites[1].start();

        gameSprites[2] = new GameSprite(
                0, images[0], null, -1, gameMap.getMapRect());
        gameSprites[2].setSensitive(new TouchDetector(gameSprites[2]));
        gameSprites[2].set_to_cell(250, 250);

        return gameSprites;
    }

    private final Camera createCamera(Point screenSize, GameSprite sprite) {
        Camera camera = new Camera(screenSize, sprite);
        camera.fix();
        return camera;
    }
}
