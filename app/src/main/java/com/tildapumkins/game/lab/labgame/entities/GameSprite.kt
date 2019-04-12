package com.tildapumkins.game.lab.labgame.entities

import android.graphics.Point
import android.graphics.RectF

import com.tildapumkins.game.lab.labgame.animation.Animated
import com.tildapumkins.game.lab.labgame.detect.Direct
import com.tildapumkins.game.lab.labgame.detect.Sensitive
import com.tildapumkins.game.lab.labgame.images.GameImage
import com.tildapumkins.game.lab.labgame.maps.MapLayer

import org.jetbrains.annotations.Contract

/**
 * Спрайт.
 * Любая активная сущность игрового мира,
 * в т.ч. игровой персонаж
 */
class GameSprite
/**
 * Конструктор
 * @param type          тип спрайта
 * @param images        все картинки
 * @param animator      все анимации движений спрайта
 * @param scale         масштаб на экране
 * @param area          границы области обитания
 */
(type: Int, private val images: GameImage   /// все картинки
 , private val animator: Animated?  /// все анимации движений спрайта
 , scale: Float, area: RectF?) : GameTile(type, images.bitmap, images.getFrame(0), scale) {
    private val indexFrame: Int     /// индекс текущего фрейма на картинке
    /**
     * @return  границы области обитания
     */
    /**
     * Устанавливает границы области обитания
     * @param area  границы области обитания
     */
    @get:Contract(pure = true)
    var area: RectF? = null         /// границы области обитания
    private var detector: Sensitive? = null /// способность осязать
    /**
     * Возвращает состояние активности спрайта
     * @return  TRUE - спрайт активен, FALSE - не активен
     */
    @get:Contract(pure = true)
    var isActive: Boolean = false
        private set     /// активность спрайта

    init {
        this.area = area ?: location
        indexFrame = 0
        isActive = true
    }

    /**
     * Перемещает спрайт по карте мира
     * @param dx    смещение координаты X игрового мира
     * @param dy    смещение координаты Y игрового мира
     */
    fun move(dx: Float, dy: Float) {
        var dx = dx
        var dy = dy
        val info = detector!!.detect()
        if (Direct.LEFT.check(info) && dx < 0 || Direct.RIGHT.check(info) && dx > 0) {
            dx = 0f
        }
        if (Direct.TOP.check(info) && dy < 0 || Direct.BOTTOM.check(info) && dy > 0) {
            dy = 0f
        }
        if (dx != 0f || dy != 0f) {
            // перемещаем спрайт
            location.offset(dx, dy)
            // перемещаем ядро спрайта
            core.offset(dx, dy)
        }
    }

    /**
     * Выбирает очередной кадр анимации спрайта, если она есть.
     */
    fun update(fps: Long) {
        if (animator != null && animator.isActive) {
            frame = animator.next
        } else {
            frame = images.getFrame(indexFrame)
        }
    }

    /**
     * Устанавливает способность осязать препятствия (границы мира, тайлы или другие спрайты)
     * @param detector  датчик осязания
     */
    fun setSensitive(detector: Sensitive) {
        this.detector = detector
    }

    /**
     * Устанавливает способность осязать препятствия (границы мира, тайлы или другие спрайты)
     * @param p
     * @param layer
     */
    fun detect(p: Point, layer: MapLayer): Boolean {
        return detector!!.detect(p, layer)
    }

    /**
     * Запускает все движения спрайта
     */
    fun start() {
        isActive = true
        animator?.start()
    }

    /**
     * Останавливат все движения спрайта
     */
    fun stop() {
        isActive = false
        animator?.stop()
    }
}
