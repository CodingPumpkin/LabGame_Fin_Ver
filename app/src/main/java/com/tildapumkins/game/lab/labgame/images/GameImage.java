package com.tildapumkins.game.lab.labgame.images;

import android.graphics.Bitmap;
import android.graphics.Point;
import android.graphics.Rect;

import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.NotNull;

/**
 * Картинка с кадрами
 */
public final class GameImage {

    private int id;             /// идентификатор
    private Bitmap bitmap;      /// картинка с кадрами одного размера
    private Rect[] rects;       /// все рамки кадров
    private Point frameSize;    /// размер кадра

    /**
     * Конструктор.
     * @param bitmap    картинка с кадрами одного размера
     * @param rects     все рамки кадров
     */
    public GameImage(int id, Bitmap bitmap, @NotNull Rect[] rects) {
        this.id = id;
        this.bitmap = bitmap;
        this.frameSize = new Point(rects[0].width(), rects[0].height());
        this.rects = rects;
    }

    /**
     * @return  иденификатор картинки
     */
    @Contract(pure = true)
    public final int getId() {
        return id;
    }

    /**
     * @return  картинку
     */
    @Contract(pure = true)
    public final Bitmap getBitmap() {
        return bitmap;
    }

    /**
     * @param index     индекс кадра
     * @return  рамку кадра картинки по заданному индексу
     */
    @Contract(pure = true)
    public final Rect getFrame(int index) {
        return rects[index];
    }

    /**
     * @return  все рамки кадров картинки
     */
    @Contract(pure = true)
    public final Rect[] getAllFrames() {
        return rects;
    }
}
