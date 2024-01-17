package com.example.compassapplication

import android.annotation.SuppressLint
import android.content.Context
import android.hardware.Sensor
import android.hardware.SensorEvent
import android.hardware.SensorEventListener
import android.hardware.SensorManager
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.animation.Animation
import android.view.animation.RotateAnimation
import android.widget.ImageView
import android.widget.TextView

class MainActivity : AppCompatActivity(),SensorEventListener {

lateinit var sensorManager: SensorManager
lateinit var directionSensor: Sensor
var currentPosition = 0f
    lateinit var needleImage:ImageView
    lateinit var directionText : TextView



    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        needleImage= findViewById(R.id.needleImage)
       directionText = findViewById(R.id.directionText)
        sensorManager = getSystemService(Context.SENSOR_SERVICE) as SensorManager
        directionSensor = sensorManager.getDefaultSensor(Sensor.TYPE_ORIENTATION)!!


    }

    @SuppressLint("SetTextI18n")
    override fun onSensorChanged(p0: SensorEvent?) {
        var degree = Math.round(p0!!.values[0])
        directionText.text = degree.toString() + "degree"
        var rotationAnimation = RotateAnimation(currentPosition,(-degree).toFloat(),Animation.RELATIVE_TO_SELF,0.5f,Animation.RELATIVE_TO_SELF,0.5f)

        rotationAnimation.duration = 210
        rotationAnimation.fillAfter = true
        needleImage.startAnimation(rotationAnimation)
        currentPosition = (-degree).toFloat()


    }

    override fun onAccuracyChanged(p0: Sensor?, p1: Int) {
    }

    override fun onResume() {
        super.onResume()
        sensorManager.registerListener(this,directionSensor,SensorManager.SENSOR_DELAY_NORMAL)

    }

    override fun onPause() {
        super.onPause()
        sensorManager.unregisterListener(this)
    }
}