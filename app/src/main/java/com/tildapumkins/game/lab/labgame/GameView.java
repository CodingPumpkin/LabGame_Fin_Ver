package com.tildapumkins.game.lab.labgame;

import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.view.MotionEvent;
import android.view.SurfaceHolder;
import android.view.SurfaceView;

import com.tildapumkins.game.lab.labgame.entities.GameTile;
import com.tildapumkins.game.lab.labgame.entities.GameSprite;
import com.tildapumkins.game.lab.labgame.maps.Camera;

public class GameView extends SurfaceView implements Runnable {

    Thread gameThread = null;
    private SurfaceHolder surfaceHolder;
    private Canvas canvas;
    private Paint paint;
    private GameState gState;

    private AccsMeter accsMeter;

    volatile boolean stop;  // true - игра остановлена, иначе запущена
    private long fps;       // tracks the game frame rate

    public GameView(long fps, GameState gState, AccsMeter accsMeter) {
        super(gState.getContext());
        this.gState = gState;
        surfaceHolder = getHolder();
        paint = new Paint();
        this.fps = fps;
        stop = false;
        this.accsMeter = accsMeter;
    }

    /**
     * Стартует игру в фоновом потоке.
     * Содержит главный игровой цил.
     */
    @Override
    public void run() {
        long timeThisFrame; // используется для вычисления fps

        // запускаем акселерометр
        accsMeter.resume();

        // главный игровой цикл
        while (!stop) {

            // фиксируем временную метку начала операций обновления-прорисовки
            long startFrameTime = System.currentTimeMillis();

            // для всех спрайтов обновляем их собственные движения (анимацию), если есть
            for (GameSprite gameSprite : gState.getSprites()) {
                gameSprite.update(fps == 0 ? 1 : fps);
            }

            /*
                Складываем значения перемещения от акселерометра
                    с текущим положение игрового персонажа,
                    соответственно по двум координатам X и Y.
                Таким образом получаем новую позицию игрового персонажа.
             */
            GameSprite player = gState.getPlayer();
            float x = accsMeter.getValue().x + player.getLocation().left;
            float y = accsMeter.getValue().y + player.getLocation().top;
            /*
                Но прежде, чем переходить в новую позицию,
                    сначала проверяем её на пересечения с тайлами (плитками).
             */
            if (player.detect(gState.getGameMap().toTiles(x, y), gState.getGameMap().getLayer())) {
                // Если пересечения нет, передвигаем игровой персонаж в новую позицию.
                player.move(accsMeter.getValue().x, accsMeter.getValue().y);
                // Фиксируем камеру, т.е. перемещаем её так,
                //  чтобы игровой персонаж оказался в центре окна камеры вида.
                gState.getCamera().fix();
            }

            // отрисовываем игровой мир
            draw();

            // фиксируем временную метку завершения операций обновления-прорисовки
            timeThisFrame = System.currentTimeMillis() - startFrameTime;

            // вычисляем реальный fps
            if (timeThisFrame >= 1) {
                fps = 1000 / timeThisFrame;
            }
        }

        // останавливаем акселерометр
        accsMeter.pause();
    }

    /**
     * Отрисовывает игровой мир на канве (rendering).
     */
    private void draw() {

        if (surfaceHolder.getSurface().isValid()) {

            // блокируем канву, чтобы исключить визуализацию прорисовки
            canvas = surfaceHolder.lockCanvas();

            // устанавливаем фоновый цвет BLACK
            canvas.drawColor(Color.BLACK);

            /*
                "Выхватываем" из общего массива игровых клеток клетки,
                    которые попадают в окно камеры вида.
                И поочерёдно их проматриваем
             */
            Camera camera = gState.getCamera();
            Rect area = gState.getGameMap().toTiles(camera.getScreen(), 1);

            GameTile[][] tiles = gState.getGameMap().getLayer().getTiles();
            for (int y = area.top; y <= area.bottom; y++) {
                for (int x = area.left; x <= area.right; x++) {
                    GameTile tile = tiles[y][x];
                    // проверяем, если очередная клетка не пустая (содержит тайл)
                    if (tile != null && camera.isVisible(tile.getLocation())) {
                        // то рисуем тайл на канве
                        canvas.drawBitmap(
                                tile.getBitmap(),
                                tile.getFrame(),
                                tile.getView(camera.getScreen()),
                                paint);
                    }
                }
            }

            /*
                Проверяем каждый спрайт.
                Если спрайт попадает в окно камеры вида, то отрисовываем его.
                Таким образом спрайты отрисовываются поверх тайлов,
                    а игровой персонаж ещё и поверх всех спрайтов, т.к.
                    находится в конце массива спрайтов.
             */
            for (GameSprite gameSprite : gState.getSprites()) {
                if (camera.isVisible(gameSprite.getLocation())) {
                    canvas.drawBitmap(
                            gameSprite.getBitmap(),
                            gameSprite.getFrame(),
                            gameSprite.getView(camera.getScreen()),
                            paint);
                }
            }

            /*
                Отрисовываем на канве текст.
                Текст будет виден поверх всего.
             */
            paint.setColor(Color.WHITE);
            paint.setTextSize(45);
            canvas.drawText("FPS:" + fps, 20, 40, paint);

            /*
                После того, как канва готова - разблокируем её,
                    и она будет показана на экране.
             */
            surfaceHolder.unlockCanvasAndPost(canvas);
        }
    }

    public void pause() {
        stop = true;
        try {
            gameThread.join();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    public void resume() {
        stop = false;
        gameThread = new Thread(this);
        gameThread.start();
    }

    @Override
    public boolean onTouchEvent(MotionEvent motionEvent) {

        switch (motionEvent.getAction() & MotionEvent.ACTION_MASK) {
            case MotionEvent.ACTION_DOWN:
                GameSprite player = gState.getPlayer();
                if (!player.isActive()) {
                    player.start();
                } else {
                    player.stop();
                }
                break;
        }
        return true;
    }
}
