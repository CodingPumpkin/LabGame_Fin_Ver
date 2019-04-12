package com.tildapumkins.game.lab.labgame.maps

import android.graphics.Point
import android.graphics.PointF
import android.graphics.Rect
import android.graphics.RectF

import com.tildapumkins.game.lab.labgame.entities.GameTile

import org.jetbrains.annotations.Contract

/**
 * Слой тайлов (плиток).
 * Группирует тайлы
 */
class MapLayer
/**
 * Конструктор
 * @param tiles     тайлы
 */
(
        /**
         * @return  все тайлы слоя
         */
        @get:Contract(pure = true)
        val tiles: Array<Array<GameTile>> /// все тайлы слоя
) {
    /**
     * Возвращает значение видимости слоя
     * @return  TRUE- слой видим, FALSE-слой невидим
     */
    /**
     * Устанавливает значение видимости слоя
     * @param visible   видимость слоя
     * TRUE- слой видим, FALSE-слой невидим
     */
    @get:Contract(pure = true)
    var isVisible: Boolean = false    /// видимость слоя
    /**
     * @return  размер тайла
     */
    @get:Contract(pure = true)
    val tileSize: Point     /// размер тайла
    /**
     * @return  размер слоя в тайлах
     */
    @get:Contract(pure = true)
    val sizeInTiles: Point  /// размер слоя в тайлах
    /**
     * @return  рамка слоя в пикселях
     */
    @get:Contract(pure = true)
    val layerRect: RectF    /// рамка слоя в пикселях
    /**
     * @return  размер слоя в пикселях
     */
    @get:Contract(pure = true)
    val layerSize: PointF   /// размер слоя в пикселях
    private val rectTiles: Rect     /// размер рамки в тайлах
    private val cells: Point        /// координты в клетках (тайлах)

    init {
        sizeInTiles = Point(tiles[0].size, tiles.size)
        // ищем первый непустой
        var frame: Rect? = null
        for (j in 0 until sizeInTiles.y) {
            for (i in 0 until sizeInTiles.x) {
                if (tiles[j][i] != null) {
                    frame = tiles[j][i].frame
                }
            }
        }
        tileSize = Point(frame!!.width(), frame.height())
        layerSize = PointF((sizeInTiles.x * tileSize.x).toFloat(), (sizeInTiles.y * tileSize.y).toFloat())
        rectTiles = Rect(0, 0, 0, 0)
        cells = Point(0, 0)
        layerRect = RectF(0f, 0f, layerSize.x, layerSize.y)
        // по умолчанию слой не видим
        isVisible = false
    }

    /**
     * Преобразует рамку с размерами в пикселях в рамку с размерами в тайлах
     * @param rectPixels    рамка с размерами в пикселях
     * @param expand        определяет на сколько расширить полученную рамку в тайлах со всех сторон
     * @return              рамка с размерами в тайлах
     */
    fun toTiles(rectPixels: RectF, expand: Int): Rect {
        rectTiles.left = (rectPixels.left / tileSize.x).toInt() - 1 - expand
        if (rectTiles.left < 0) rectTiles.left = 0
        rectTiles.top = (rectPixels.top / tileSize.y).toInt() - 1 - expand
        if (rectTiles.top < 0) rectTiles.top = 0
        rectTiles.right = (rectPixels.right / tileSize.x).toInt() + 1 + expand
        if (rectTiles.right > sizeInTiles.x) rectTiles.right = sizeInTiles.x
        rectTiles.bottom = (rectPixels.bottom / tileSize.y).toInt() + 1 + expand
        if (rectTiles.bottom > sizeInTiles.y) rectTiles.bottom = sizeInTiles.y
        return rectTiles
    }

    /**
     * Преобразует точку с координатами x, Y в пикселях в координаты клетки в тайлах
     * @param x     X координата точки в пикселях
     * @param y     Y координата точки в пикселях
     * @return      точка с размерами в тайлах
     */
    fun toTiles(x: Float, y: Float): Point {
        cells.x = (x / tileSize.x).toInt()
        if (cells.x < 0) cells.x = 0
        cells.y = (y / tileSize.y).toInt()
        if (cells.y < 0) cells.y = 0
        return cells
    }
}
