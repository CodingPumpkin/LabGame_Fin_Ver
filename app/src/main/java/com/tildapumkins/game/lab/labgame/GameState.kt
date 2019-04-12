package com.tildapumkins.game.lab.labgame

import android.content.Context
import android.graphics.Bitmap
import android.graphics.Point

import com.tildapumkins.game.lab.labgame.animation.Animator
import com.tildapumkins.game.lab.labgame.entities.GameSprite
import com.tildapumkins.game.lab.labgame.images.GameImage
import com.tildapumkins.game.lab.labgame.maps.Camera
import com.tildapumkins.game.lab.labgame.maps.GameMap

class GameState(
        val context: Context,
        val bitmaps: Array<Bitmap>,
        val images: Array<GameImage>,
        val animators: Array<Animator>,
        val sprites: Array<GameSprite>,
        playerIndex: Int,
        val screenSize: Point,
        val camera: Camera,
        val gameMap: GameMap
) {
    var player: GameSprite? = null
        private set

    init {
        setPlayer(playerIndex)
    }

    fun setPlayer(playerIndex: Int) {
        player = sprites[playerIndex]
    }
}
