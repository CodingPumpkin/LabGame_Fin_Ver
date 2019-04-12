package com.tildapumkins.game.lab.labgame;

import android.content.Context;
import android.graphics.Point;
import android.hardware.SensorManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Display;
import android.view.Window;
import android.view.WindowManager;

import com.tildapumkins.game.lab.labgame.build.GameBuilder;

import java.io.IOException;

public class MainActivity extends AppCompatActivity {

    private GameView gameView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        getWindow().setFlags(
                WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);
        requestWindowFeature(Window.FEATURE_NO_TITLE);

        Display display = getWindowManager().getDefaultDisplay();
        Point resolution = new Point();
        display.getSize(resolution);

        GameState gState = null;
        try {
            gState = new GameBuilder().build(this, resolution.x, resolution.y);
        } catch (IOException e) {
            e.printStackTrace();
        }
        SensorManager mSensorManager = (SensorManager) getSystemService(Context.SENSOR_SERVICE);
        AccsMeter accsMeter = new AccsMeter(mSensorManager, 50);
        assert gState != null;
        gameView = new GameView(60, gState, accsMeter);

        setContentView(gameView);
    }

    @Override
    protected void onPause() {
        super.onPause();
        gameView.pause();
    }

    @Override
    protected void onResume() {
        super.onResume();
        gameView.resume();
    }
}

