package com.example.framework

import android.graphics.Canvas
import android.graphics.Color
import android.util.Log
import androidx.annotation.Size
import androidx.core.graphics.toColorInt

class Snake(x: Float, y: Float) {
    var body: MutableList<Ball> = mutableListOf()


    init {
        if(body.isEmpty()){
            body.add(Ball(x, y))
        }
    }

    var currentCoordinate = body.first().currentCoordinate
    var x = body.first().x
    var y = body.first().y

    fun onEat(){
        val newBallPosition = calculateNewBallPosition()
        body.add(Ball(newBallPosition.x, newBallPosition.y))
        Log.v("Snake", "Added 1 body")
        body.forEach{
            it.setNewDirection(body.first().direction)
        }
    }

    private fun calculateNewBallPosition() : Coordinate{
        var newBallPosition = Coordinate(0f,0f)
        if(body.first().direction == Direction.UP){
            newBallPosition = Coordinate(body.last().x, body.last().y + 35f)
        }
        if(body.first().direction == Direction.DOWN){
            newBallPosition = Coordinate(body.last().x, body.last().y - 35f)
        }
        if(body.first().direction == Direction.RIGHT){
            newBallPosition = Coordinate(body.last().x - 35f, body.last().y)
        }
        if(body.first().direction == Direction.LEFT){
            newBallPosition = Coordinate(body.last().x + 35f, body.last().y)
        }
        return newBallPosition
    }


     fun update(et: Float) {
         body.first().update(et)
         if(body.size>1){
             for (i in 1..<body.size){
                 body[i].update(et, margin= 0f)
             }
         }
          currentCoordinate = body.first().currentCoordinate
          x = body.first().x
          y = body.first().y
    }

    fun setNewDirection(direction: Direction){
        body.forEach{
            it.setNewDirection(direction)
        }
    }

     fun render(canvas: Canvas, initialColor: Int? = null) {
        body.first().render(canvas, initialColor)
         if(body.size>1){
             for (i in 1..<body.size){
                 renderByDirection(body[i], i, canvas, i == 1)
             }
         }
    }

    private fun renderByDirection(ball: Ball, position: Int, canvas: Canvas, isFirst: Boolean){
        var newBallPosition = Coordinate(0f,0f)
        val baseX = body.first().x
        val baseY = body.first().y
        if(body.first().direction == Direction.UP){
            newBallPosition = Coordinate(baseX, baseY + (35f*position))
        }
        if(body.first().direction == Direction.DOWN){
            newBallPosition = Coordinate(baseX, baseY - (35f*position))
        }
        if(body.first().direction == Direction.RIGHT){
            newBallPosition = Coordinate(baseX - (35f * position), baseY)
        }
        if(body.first().direction == Direction.LEFT){
            newBallPosition = Coordinate(baseX + (35f*position) , baseY)
        }
        ball.render(canvas,"#a3e598".toColorInt(), 40f,newBallPosition.x, newBallPosition.y )
    }
}