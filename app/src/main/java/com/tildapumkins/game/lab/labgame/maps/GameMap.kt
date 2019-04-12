package com.tildapumkins.game.lab.labgame.maps

import android.graphics.Point
import android.graphics.PointF
import android.graphics.Rect
import android.graphics.RectF

import org.jetbrains.annotations.Contract

class GameMap
/**
 * Котнструктор
 * @param layers    все слои карты
 */
(private val layers: Array<MapLayer>      /// все слои карты
)//tileSize = new Point(Integer.MAX_VALUE, Integer.MAX_VALUE);
//sizeInTiles = new Point(0, 0);
//mapSize = new PointF(0f, 0f);
/*
        for (int i = 0; i < layers.length; i++) {
            MapLayer layer = layers[i];
            Point sz = layer.getTileSize();
            if (sz.x < tileSize.x && sz.y < tileSize.y) {
                tileSize.x = sz.x;
                tileSize.y = sz.y;
                sz = layer.getSizeInTiles();
                sizeInTiles.x = sz.x;
                sizeInTiles.y = sz.y;
                PointF szf = layer.getLayerSize();
                mapSize.x = szf.x;
                mapSize.y = szf.y;
                layerIndex = i;
            }
        }
        *///mapRect = new RectF(0, 0, mapSize.x, mapSize.y);
{
    //private Point tileSize;         /// минимальный размер тайла
    //private Point sizeInTiles;      /// размер карты в тайлах
    //private PointF mapSize;         /// размер карты в пикселях
    //private RectF mapRect;          /// рамка карты в пикселях
    private var layerIndex: Int = 0         /// индекс слоя с минимальным размером тайла

    /**
     * @return  видимый слой
     */
    val layer: MapLayer
        @Contract(pure = true)
        get() = layers[layerIndex]

    /**
     * @return  размер тайла в пикселях
     */
    val tileSize: Point
        get() = layers[layerIndex].tileSize

    /**
     * @return  размер карты в тайлах
     */
    val sizeInTiles: Point
        get() = layers[layerIndex].sizeInTiles

    /**
     * @return  размер карты в пикселях
     */
    val mapSize: PointF
        get() = layers[layerIndex].layerSize

    /**
     * @return  рамку карты в пикселях
     */
    val mapRect: RectF
        get() = layers[layerIndex].layerRect

    /**
     * Выбирает активный (видимый) слой,
     * остальные слои невидимы и неактивны
     */
    @Contract(pure = true)
    fun selectLayer(layerIndex: Int) {
        layers[this.layerIndex].isVisible = false
        this.layerIndex = layerIndex
        layers[this.layerIndex].isVisible = true
    }

    /**
     * Преобразует рамку с размерами в пикселях в рамку с размерами в тайлах
     * @param rectPixels    рамка с размерами в пикселях
     * @param expand        определяет на сколько расширить полученную рамку в тайлах со всех сторон
     * @return              рамка с размерами в тайлах
     */
    fun toTiles(rectPixels: RectF, expand: Int): Rect {
        return layers[layerIndex].toTiles(rectPixels, expand)
    }

    /**
     * Преобразует точку с координатами x, Y в пикселях в координаты клетки в тайлах
     * @param x     X координата точки в пикселях
     * @param y     Y координата точки в пикселях
     * @return      точка с размерами в тайлах
     */
    fun toTiles(x: Float, y: Float): Point {
        return layers[layerIndex].toTiles(x, y)
    }
}
