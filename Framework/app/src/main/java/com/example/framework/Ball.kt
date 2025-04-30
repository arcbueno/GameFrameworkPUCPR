package com.example.framework

import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import android.util.Log

open class Ball(var x: Float, var y: Float) {
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

     fun update(et: Float, margin: Float = 0f) {
        when (direction) {
            Direction.UP -> updateY(et, margin)
            Direction.DOWN -> updateY(et, margin)
            Direction.LEFT -> updateX(et, margin)
            Direction.RIGHT -> updateX(et, margin)
        }
        currentCoordinate = Coordinate(x, y)
    }

    private fun updateX(et: Float, margin: Float = 0f) {

        if (direction == Direction.LEFT) {
            x -= (vel * et / 1000f) + margin
        } else {
            x += (vel * et / 1000f) - margin
        }
    }

    private fun updateY(et: Float, margin: Float = 0f) {
        if (direction == Direction.UP) {
            y -= (vel * et / 1000f) + margin
        } else {
            y += (vel * et / 1000f) - margin
        }
    }

    fun setNewDirection(direction: Direction) {
        this.direction = direction
    }

     fun render(canvas: Canvas, initialColor: Int? = null, customSize: Float? = null, customX: Float? = null, customY: Float? = null) {
        paint.color = initialColor ?: Color.GREEN
        canvas.drawCircle(customX?:x, customY?:y,  customSize ?: 50f, paint)
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