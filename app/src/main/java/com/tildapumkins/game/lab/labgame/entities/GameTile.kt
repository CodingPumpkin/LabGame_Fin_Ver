package com.tildapumkins.game.lab.labgame.entities

import android.graphics.Bitmap
import android.graphics.Rect
import android.graphics.RectF

import org.jetbrains.annotations.Contract

/**
 * Тайл (плитка).
 * Элементарный (единичный) статический фрагмент карты мира
 */
open class GameTile
/**
 * Конструктор тайла
 * @param type      тип тайла
 * @param bitmap    картинка с образами тайла
 * @param frame     рамка тайла на картинке
 * @param scale     масштаб тайла
 */
(type: Int, bitmap: Bitmap, frame: Rect, scale: Float) {

    /**
     * @return  тип тайла
     */
    @get:Contract(pure = true)
    var type: Int = 0
        protected set         /// тип тайла
    /**
     * Возвращает картинку с кадрами
     * @return ссылка на картинку с кадрами
     */
    @get:Contract(pure = true)
    var bitmap: Bitmap
        protected set    /// картинка c образами тайла
    /**
     * Возвращает рамку кадра на картинке с кадрами
     * @return рамка кадра
     */
    @get:Contract(pure = true)
    var frame: Rect
        protected set       /// рамка образа тайла на картинке
    /**
     * Место тайла на карте игрового мира
     * @return  расположение тайла
     */
    @get:Contract(pure = true)
    var location: RectF
        protected set   /// место тайла на карте мира (рамка тайла)
    /**
     * @return  рамка ядра тайла
     */
    @get:Contract(pure = true)
    var core: RectF
        protected set       /// ядро тайла (рамка ядра внутри рамки тайла)
    protected var view: RectF       /// место тайла в на экране (в локальной системе координат)
    /**
     * Возвращает значение видимости тайла
     * @return TRUE-тайл видим, FALSE-тайл невидим
     */
    @get:Contract(pure = true)
    var isVisible: Boolean = false
        protected set  /// TRUE-тайл видим, FALSE-тайл невидим

    init {
        this.type = type
        this.bitmap = bitmap
        this.frame = frame
        /*
            По умолчанию тайл устанавливается
                в крайнюю левую вершнюю позицию игрового мира.
            Если задан масштаб (scale > 0), то рамка тайла масштабируется.
         */
        if (scale <= 0) {
            location = RectF(0f, 0f, frame.right.toFloat(), frame.bottom.toFloat())
        } else {
            location = RectF(0f, 0f,
                    frame.right * scale, frame.bottom * scale)
        }
        view = RectF(location)
        // Рамка ядра тайла по умолчанию устанавливается равным рамке тайла.
        core = RectF(location)
        isVisible = true
    }

    /**
     * Изменяет параметры ядра тайла по умолчанию
     * @param left      левая граница
     * @param top       верхняя граница
     * @param right     правая граница
     * @param bottom    нижняя граница
     */
    fun setCore(left: Float, top: Float, right: Float, bottom: Float) {
        core.set(left, top, right, bottom)
    }

    /**
     * Вычисляет позицию тайла в локальной системе координат экрана
     * (окна камеры вида)
     * @param screen    рамка окна камеры вида
     * @return          рамка тайла в локальной системе координат
     */
    fun getView(screen: RectF): RectF {
        view.left = location.left - screen.left
        view.top = location.top - screen.top
        view.right = location.right - screen.left
        view.bottom = location.bottom - screen.top
        return view
    }

    /**
     * Устанавливает тайл на карту мира
     * @param left  координата X игрового мира
     * @param top   координата Y игрового мира
     */
    fun set_to(left: Float, top: Float) {
        // вычисляем смещение рамки ядра тайла от рамки тайла
        val dx = core.left - location.left
        val dy = core.top - location.top
        // устанавливаем тайл
        location.offsetTo(left, top)
        // устанавливаем ядро тайла
        core.offsetTo(left + dx, top + dy)
    }

    /**
     * Устанавливает тайл на клетку карты мира
     * @param left  координата X клетки игрового мира
     * @param top   координата Y клетки игрового мира
     */
    fun set_to_cell(left: Float, top: Float) {
        location.offsetTo(
                left * location.width(),
                top * location.height()
        )
    }

    /**
     * Делает тайл невидимым
     */
    fun hide() {
        isVisible = false
    }

    /**
     * Делает тайл видимым
     */
    fun show() {
        isVisible = true
    }

}
