package com.tildapumkins.game.lab.labgame.maps

import android.graphics.Point
import android.graphics.RectF

import com.tildapumkins.game.lab.labgame.entities.GameTile

import org.jetbrains.annotations.Contract

/**
 * Камера вида.
 * Перемещается по игровому миру вместе с игровым персонажем.
 * Игровой персонаж всегда в центре окна камеры.
 * Рамка (окно) камеры равна размеру экрана мобильного устройства
 */
class Camera
/**
 * Конструктор
 * @param screenSize    размеры экрана мобильного устройства (ширина и высота)
 * @param target        цель слежения - игровой персонаж
 */
(screenSize: Point, private val target: GameTile    /// цель - игровой персонаж, за которой наблюдает камера
) {

    /**
     * @return  рамку окна камеры вида (рамку экрана)
     */
    @get:Contract(pure = true)
    val screen: RectF       /// рамка окна камеры

    init {
        screen = RectF(0f, 0f, screenSize.x.toFloat(), screenSize.y.toFloat())
    }

    /**
     * Проверяет попала ли рамка объекта в окно камеры.
     * Объект видим, если его рамка внутри рамки окна камеры
     * или пересекается с нею.
     * @param rect  рамка исследуемого объекта
     * @return      TRUE-если объект видим, FALSE-если нет
     */
    fun isVisible(rect: RectF): Boolean {
        return screen.contains(rect.left, rect.top, rect.right, rect.bottom) || screen.intersects(rect.left, rect.top, rect.right, rect.bottom)
    }

    /**
     * Устанавливает центр рамки окна камеры вида
     * на центр рамки наблюдаемой сущности
     */
    fun fix() {
        val location = target.location
        screen.offsetTo(
                screen.left + location.centerX() - screen.centerX(),
                screen.top + location.centerY() - screen.centerY()
        )
    }
}
