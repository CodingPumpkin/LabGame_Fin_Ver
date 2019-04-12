package com.tildapumkins.game.lab.labgame.animation;

import android.graphics.Bitmap;
import android.graphics.Rect;

import org.jetbrains.annotations.Contract;

/**
 * Простой Аниматор.
 * Управляет анимацией (собственными движениями) спрайта.
 */
public final class Animator implements Animated {

    private Bitmap bitmap;      /// картинка с фрагментами анимации
    private Rect[] frames;      /// рамки кадров анимации
    private int index;          /// индекс текущего кадра (рамки) анимации
    private long frameTicker;   /// момент времени последней смены кадра
    private boolean active;     /// TRUE - анимация активна, FALSE - анимация не активна
    private int frameTime;      /// скорость анимации

    /**
     * Конструктор
     * @param bitmap        картинка с фрагментами анимации
     * @param frames        рамки кадров анимации
     * @param frameTime     скорость анимации
     *                      (интервал времени между сменой кадров анимации)
     */
    public Animator(Bitmap bitmap, Rect[] frames, int frameTime) {
        this.bitmap = bitmap;
        this.frames = frames;
        this.frameTime = frameTime;
        index = 0;
        frameTicker = 0L;
    }

    /**
     * @return  картинка с фрагментами анимации спрайта
     */
    @Contract(pure = true)
    @Override
    public final Bitmap getBitmap() {
        return bitmap;
    }

    /**
     * @return  очередную рамку фрагмента анимации спрайта
     */
    @Override
    public final Rect getNext() {
        long time = System.currentTimeMillis();
        if (active) {
            if (time > frameTicker + frameTime) {
                frameTicker = time;
                index++;
                if (index >= frames.length) {
                    index = 0;
                }
            }
        }
        return frames[index];
    }

    /**
     * Определяет активна ли анимация
     * @return  TRUE-если анимация активна, FALSE-если анимация не активна
     */
    @Contract(pure = true)
    @Override
    public final boolean isActive() {
        return active;
    }

    /**
     * Запускает анимацию с текущего кадра
     */
    @Override
    public final void start() {
        active = true;
    }

    /**
     * Запускает анимацию с заданным аниматором
     * @param index     индекс аниматора
     */
    @Override
    public final void start(int index) {
        active = true;
    }

    /**
     * Запускает анимацию с заданным аниматором
     *  и с заданного кадра
     * @param index         индекс аниматора
     * @param indexFrame    индекс кадра
     */
    public final void start(int index, int indexFrame) {
        this.index = indexFrame;
        active = true;
    }

    /**
     * Останавливает анимацию
     */
    @Override
    public final void stop() {
        active = false;
    }

    @Override
    public void setActive(boolean b) {

    }
}
