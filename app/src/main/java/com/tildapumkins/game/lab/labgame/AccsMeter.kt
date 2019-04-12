package com.tildapumkins.game.lab.labgame

import android.graphics.PointF
import android.hardware.Sensor
import android.hardware.SensorEvent
import android.hardware.SensorEventListener
import android.hardware.SensorManager

import com.tildapumkins.game.lab.labgame.entities.GameSprite
import com.tildapumkins.game.lab.labgame.maps.Camera
import com.tildapumkins.game.lab.labgame.maps.GameMap

/**
 * Аккселерометр-адавптер
 * Снимает показания с сенсора акселерометра
 * и преобразует их в перемещения игрового персонажа
 */
class AccsMeter
/**
 * Конструктор
 * @param mSensorManager    системный SensorManager
 * @param deltaTime         интервал времени за который усредняются показания акселерометра
 */
(private val mSensorManager: SensorManager   /// системный SensorManager
 , private val deltaTime: Long     /// интервал времени за который усредняются показания акселерометра
) : SensorEventListener {

    private val GRAVITY_CONST = 9.80665f   /// константа ускорение свободного падения
    private val rawValue: PointF    /// непосредственные показания акселерометра
    var value: PointF        /// усреднённые показания акселерометра в интервале времени deltaTime
    private var lastTimeStamp: Long = 0 /// предыдущая временная метка
    private val mAccelerometerSensor: Sensor    /// системный Sensor акселерометр

    init {
        mAccelerometerSensor = mSensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER)
        rawValue = PointF(0f, 0f)
        value = PointF(0f, 0f)
    }

    /**
     * Обрабатывает сигналы от сенсора акселерометра
     * преобразует значения проекций ускорения в проекции перемещение игрового персонада
     * Релизует метод интерфейса SensorEventListener
     * @param event     событие SensorEvent
     */
    override fun onSensorChanged(event: SensorEvent) {
        val timeStamp = System.currentTimeMillis()
        when (event.sensor.type) {
            Sensor.TYPE_ACCELEROMETER -> {
                rawValue.y = if (event.values[0] > GRAVITY_CONST) GRAVITY_CONST else event.values[0]
                rawValue.x = if (event.values[1] > GRAVITY_CONST) GRAVITY_CONST else event.values[1]
                value.x = (value.x + rawValue.x) / 2
                value.y = (value.y + rawValue.y) / 2
                if (timeStamp - lastTimeStamp > deltaTime) {
                    lastTimeStamp = timeStamp
                    value.x = rawValue.x
                    value.y = rawValue.y
                }
            }
        }
    }

    /**
     * Релизует метод интерфейса SensorEventListener
     * Ничего не делает
     * @param sensor
     * @param accuracy
     */
    override fun onAccuracyChanged(sensor: Sensor, accuracy: Int) {

    }

    /**
     * Регистрирует и запускает слушатель сигналов акселерометра
     */
    fun resume() {
        mSensorManager.registerListener(this, mAccelerometerSensor, 10000)
        lastTimeStamp = System.currentTimeMillis()
    }

    /**
     * Останавливает слушатель сигналов акселерометра
     */
    fun pause() {
        mSensorManager.unregisterListener(this)
        lastTimeStamp = -1
    }
}

