package com.tildapumkins.game.lab.labgame.build;

import android.graphics.Bitmap;
import android.graphics.Point;
import android.graphics.Rect;

import com.tildapumkins.game.lab.labgame.images.GameImage;

/**
 * Построитель картинки с кадрами
 */
public class GameImageBuilder {

    /**
     * Строит картинку.
     * Вызывается, когда все кадры на картинке расположены в одну строчку.
     * @param id            идентификатор картинки
     * @param bitmap        картинка с фрагментами (кадрами)
     * @param frameWidth    ширина рамки кадра
     * @param frameHeight   высота рамки кадра
     * @param frameCount    количество кадров
     */
    public final GameImage build (int id, Bitmap bitmap, int frameWidth, int frameHeight, int frameCount) {
        return init(id, bitmap, frameWidth, frameHeight, frameCount, 1, frameCount);
    }

    /**
     * Строит картинку.
     * Вызывается, когда все кадры на картинке
     *  расположены в виде таблицы в несколько строк
     *  и последняя строка таблицы заполнена полностью.
     * @param id            идентификатор картинки
     * @param bitmap        картинка с фрагментами (кадрами)
     * @param frameWidth    ширина рамки кадра
     * @param frameHeight   высота рамки кадра
     * @param frameRows     кол-во строк с кадрами
     * @param framesPerRow  кол-во кадров в строке
     */
    public final GameImage build (int id, Bitmap bitmap, int frameWidth,
                                    int frameHeight, int frameRows, int framesPerRow) {
        return init(id, bitmap, frameWidth, frameHeight,
                frameRows * framesPerRow, frameRows, framesPerRow);
    }

    /**
     * Строит картинку.
     * Вызывается, когда все кадры на картинке
     *  расположены в виде таблицы в несколько строк
     *  и последняя строка таблицы заполнена НЕ полностью.
     * @param id            идентификатор картинки
     * @param bitmap        картинка с фрагментами (кадрами)
     * @param frameWidth    ширина рамки кадра
     * @param frameHeight   высота рамки кадра
     * @param frameCount    количество кадров
     * @param frameRows     кол-во строк с кадрами
     * @param framesPerRow  кол-во кадров в строке
     */
    public final GameImage build (int id, Bitmap bitmap, int frameWidth, int frameHeight,
                                    int frameCount, int frameRows, int framesPerRow) {
        return init(id, bitmap, frameWidth, frameHeight, frameCount, frameRows, framesPerRow);
    }

    /*
     * Создаёт объект
     * @param id            идентификатор картинки
     * @param bitmap        картинка с фрагментами (кадрами)
     * @param frameWidth    ширина рамки кадра
     * @param frameHeight   высота рамки кадра
     * @param frameCount    количество кадров
     * @param frameRows     кол-во строк с кадрами
     * @param framesPerRow  кол-во кадров в строке
     */
    private GameImage init (int id, Bitmap bitmap, int frameWidth, int frameHeight,
                             int frameCount, int frameRows, int framesPerRow) {
        // на основе загруженного из файлв Bitmap создаём новый ScaledBitmap
        Bitmap bmp = Bitmap.createScaledBitmap(
                bitmap, frameWidth * frameCount, frameHeight, false);
        // создаём и размечаем рамки кадров
        Rect[] rects = new Rect[frameCount];
        for (int i = 0; i < rects.length; i++) {
            rects[i] = new Rect();
            rects[i].left = (getColumn(i + 1, framesPerRow) - 1) * frameWidth;
            rects[i].top = (getRow(i + 1, framesPerRow) - 1) * frameHeight;
            rects[i].right = rects[i].left + frameWidth;
            rects[i].bottom = rects[i].top + frameHeight;
        }
        return new GameImage(id, bmp, rects);
    }

    // выдаёт номер колонки по индексу (номеру) кадра
    private int getColumn(int index, int framesPerRow) {
        int col = index % framesPerRow;
        return col > 0 ? col : framesPerRow;
    }

    // выдаёт номер строки по индексу (номеру) кадра
    private int getRow(int index, int framesPerRow) {
        int row = index / framesPerRow;
        return index % framesPerRow > 0 ? row + 1 : row;
    }
}
