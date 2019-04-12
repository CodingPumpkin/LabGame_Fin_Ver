package com.tildapumkins.game.lab.labgame.animation

import android.graphics.Bitmap
import android.graphics.Rect

/**
 * Аниматор.
 * Управляет анимацией (собственными движениями) спрайта.
 */
interface Animated {

    /**
     * Возвращает картинку с фрагментами анимации спрайта
     * @return
     */
    val bitmap: Bitmap

    /**
     * Возвращает очередную рамку фрагмента анимации спрайта
     * @return
     */
    val next: Rect

    /**
     * Определяет активна ли анимация
     * @return  TRUE-если анимация активна, FALSE-если анимация не активна
     */
    var isActive: Boolean

    /**
     * Запускает анимацию с текущего кадра
     */
    fun start()

    /**
     * Запускает анимацию с заданным аниматором
     * @param index     индекс аниматора
     */
    fun start(index: Int)

    /**
     * Запускает анимацию с заданным аниматором
     * и с заданного кадра
     * @param index         индекс аниматора
     * @param indexFrame    индекс кадра
     */
    fun start(index: Int, indexFrame: Int)

    /**
     * Останавливает анимацию
     */
    fun stop()

}
