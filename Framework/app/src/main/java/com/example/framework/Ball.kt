package com.example.framework

import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import android.util.Log

class Ball(var x: Float, var y: Float) {
    var limitTopLeft: Coordinate = Coordinate(0f, 0f)
    var limitTopRight: Coordinate = Coordinate(0f, 0f)
    var limitBottomLeft: Coordinate = Coordinate(0f, 0f)
    var limitBottomRight: Coordinate = Coordinate(0f, 0f)
    var direction: Direction = Direction.RIGHT
    var currentCoordinate: Coordinate = Coordinate(x, y)
    val paint = Paint()

    companion object {
        private const val vel = 200
    }

    init {
        paint.setColor(Color.RED)
    }

    fun update(et: Float) {
        when (direction) {
            Direction.UP -> updateY(et)
            Direction.DOWN -> updateY(et)
            Direction.LEFT -> updateX(et)
            Direction.RIGHT -> updateX(et)
        }
        currentCoordinate = Coordinate(x, y)
    }

    private fun updateX(et: Float) {
        Log.v("Elapsed Time", et.toString())

        if (direction == Direction.LEFT) {
            x -= vel * et / 1000f
        } else {
            x += vel * et / 1000f
        }
        Log.v("Position x", x.toString())
    }

    private fun updateY(et: Float) {
        if (direction == Direction.UP) {
            y -= vel * et / 1000f
        } else {
            y += vel * et / 1000f
        }
    }

    fun setNewDirection(direction: Direction) {
        this.direction = direction
    }

    fun render(canvas: Canvas, initialColor: Int? = null) {
        paint.color = initialColor ?: Color.GREEN
        canvas.drawCircle(x, y, 50f, paint)
        limitTopLeft = Coordinate(0f, 0f)
        limitTopRight = Coordinate(canvas.width.toFloat(), 0f)
        limitBottomLeft = Coordinate(0f, canvas.height.toFloat())
        limitBottomRight = Coordinate(canvas.width.toFloat(), canvas.height.toFloat())
    }
}

enum class Direction {
    UP,
    DOWN,
    LEFT,
    RIGHT
}