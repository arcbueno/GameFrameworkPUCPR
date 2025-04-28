package com.example.framework

data class Coordinate(
    var x: Float,
    var y: Float
) {
    fun setCoordinate(x: Float, y: Float) {
        this.x = x
        this.y = y
    }

    fun isNearby(coordinate: Coordinate, margin: Float = 0f): Boolean {
        return (x >= coordinate.x - margin && x <= coordinate.x + margin) &&
               (y >= coordinate.y - margin && y <= coordinate.y + margin)

    }
}