package com.example.framework

data class Coordinate(
    var x: Float,
    var y: Float
) {

    fun isNearby(coordinate: Coordinate, margin: Float = 0f): Boolean {
        // Check if the coordinate is within the margin of the current coordinate
        return (x >= coordinate.x - margin && x <= coordinate.x + margin) &&
               (y >= coordinate.y - margin && y <= coordinate.y + margin)

    }
}