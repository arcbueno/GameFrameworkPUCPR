package com.example.framework

import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint

class Ball(var x: Float, var y: Float) {

    companion object {
        private const val vel = 200
    }

    val paint = Paint()

    init {
        paint.setColor(Color.RED)
    }

    fun update(et: Float) {
        x += vel * et / 1000f
    }

    fun render(canvas: Canvas) {
        canvas.drawCircle(x, y, 50f, paint)
    }
}