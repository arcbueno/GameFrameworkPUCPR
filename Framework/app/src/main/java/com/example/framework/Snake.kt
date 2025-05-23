package com.example.framework

import android.graphics.Canvas
import android.graphics.Color
import android.util.Log
import androidx.annotation.Size
import androidx.core.graphics.toColorInt

class Snake(x: Float, y: Float) {
    var body: MutableList<Ball> = mutableListOf()
    private var coordinatesBeforeTurn = Coordinate(0f, 0f)
    private var startedTurn = false
    private var isUpdating = false
//    private val coordinatesDirectionsToTurn: MutableMap<Coordinate, Direction> = mutableMapOf()
    // Criar lista de funções que devem ser executadas caso alguma esteja sendo executada
    // Criar booleano indicando se existe alguma função sendo executada no momento
    init {
        if(body.isEmpty()){
            body.add(Ball(x, y))
        }
    }

    var direction = body.first().direction
    var currentCoordinate = body.first().currentCoordinate
    var x = body.first().x
    var y = body.first().y

    private fun updateInfo(){
         direction = body.first().direction
         currentCoordinate = body.first().currentCoordinate
         x = body.first().x
         y = body.first().y
    }

    fun onEat(){
        val newBallPosition = calculateNewBallPosition()
        val direction = body.last().direction
        body.add(Ball(newBallPosition.x, newBallPosition.y))
        Log.v("Snake", "Added 1 body")
        body.last().setNewDirection(direction)
        updateInfo()
    }

    private fun calculateNewBallPosition() : Coordinate{
        var newBallPosition = Coordinate(0f,0f)
        if(body.last().direction == Direction.UP){
            newBallPosition = Coordinate(body.last().x, body.last().y + 110f)
        }
        if(body.last().direction == Direction.DOWN){
            newBallPosition = Coordinate(body.last().x, body.last().y - 110f)
        }
        if(body.last().direction == Direction.RIGHT){
            newBallPosition = Coordinate(body.last().x - 110f, body.last().y)
        }
        if(body.last().direction == Direction.LEFT){
            newBallPosition = Coordinate(body.last().x + 110f, body.last().y)
        }
        return newBallPosition
    }


     fun update(et: Float) {
         if(isUpdating){
             Log.v("Snake", "Already updating")
             return
         }
         body.first().update(et)
         if(body.size>1){
             if(startedTurn){
                 updateTurning(et)
             }
             else {
                 for (i in 1..<body.size){
                     body[i].update(et)
                 }
             }
         }
         updateInfo()
    }

    private fun updateTurning(et: Float){

        isUpdating = true
        Log.v("Snake", "Turning")
        for (i in 1..<body.size){
//            for(coordinate in coordinatesDirectionsToTurn.keys){
//                if(body[i].currentCoordinate.isNearby(coordinate, margin = 2f)){
//                    Log.v("Snake", "Turning to: ${coordinatesDirectionsToTurn[coordinate]}")
//                    body[i].setNewDirection(coordinatesDirectionsToTurn[coordinate]!!)
//
//                }
//                if(body.last().currentCoordinate.isNearby(coordinate, margin = 2f)){
//                    Log.v("Snake", "Removing $coordinate}")
//                    coordinatesDirectionsToTurn.remove(coordinate)
//                }
//            }

            if(body[i].direction != body[i-1].direction && body[i].currentCoordinate.isNearby(coordinatesBeforeTurn, margin = 2f)){
                Log.v("Snake", "$i is turning")
                body[i].setNewDirection(body[i-1].direction)

            }
            if(body.last().direction == body.first().direction){
                startedTurn = false
                Log.v("Snake", "Turning finished")
            }

            body[i].update(et)

            isUpdating = false
        }
    }

    fun setNewDirection(direction: Direction){
        Log.v("Snake", "New direction: $direction")
        if(body.first().direction == direction){
            Log.v("Snake", "Already in this direction")
            return
        }
        if(isUpdating){
            Log.v("Snake", "Already updating")
            return
        }
        if(startedTurn){
            Log.v("Snake", "Already turning")
            return
        }

        if(body.size > 1){
            if(body.first().direction != direction){
                val currentCoordinate = body.first().currentCoordinate
//                coordinatesDirectionsToTurn[currentCoordinate] = direction
                coordinatesBeforeTurn = currentCoordinate
                startedTurn = true
                Log.v("Snake", "Coordinates before: $currentCoordinate")
            }
        }
        body.first().setNewDirection(direction)
        updateInfo()
    }

     fun render(canvas: Canvas, initialColor: Int? = null) {
        body.first().render(canvas, initialColor)
         if(body.size>1){
             for (i in 1..<body.size){
                 renderByDirection(body[i], i, canvas)
             }
         }
    }

//    private fun renderByPrevious(canvas: Canvas, currentBall: Ball, previousBall: Ball){
//        var newBallPosition = Coordinate(0f,0f)
//        val baseX = previousBall.x
//        val baseY = previousBall.y
//        if(body.first().direction == Direction.UP){
//            newBallPosition = Coordinate(baseX, baseY + (35f))
//        }
//        if(body.first().direction == Direction.DOWN){
//            newBallPosition = Coordinate(baseX, baseY - (35f))
//        }
//        if(body.first().direction == Direction.RIGHT){
//            newBallPosition = Coordinate(baseX - (35f ), baseY)
//        }
//        if(body.first().direction == Direction.LEFT){
//            newBallPosition = Coordinate(baseX + (35f) , baseY)
//        }
//        currentBall.setNewDirection(previousBall.direction)
//        currentBall.render(canvas,"#a3e598".toColorInt(), 40f,newBallPosition.x, newBallPosition.y )
//    }

    private fun renderByDirection(ball: Ball, position: Int, canvas: Canvas){
//        var newBallPosition = Coordinate(0f,0f)
//        val baseX = body[position-1].x
//        val baseY = body[position-1].y
//        if(ball.direction == Direction.UP){
//            newBallPosition = Coordinate(baseX, baseY + (45f))
//        }
//        if(ball.direction == Direction.DOWN){
//            newBallPosition = Coordinate(baseX, baseY - (45f))
//        }
//        if(ball.direction == Direction.RIGHT){
//            newBallPosition = Coordinate(baseX - (45f), baseY)
//        }
//        if(ball.direction == Direction.LEFT){
//            newBallPosition = Coordinate(baseX + (45f) , baseY)
//        }
//        ball.x = newBallPosition.x
//        ball.y = newBallPosition.y
        ball.render(canvas,"#a3e598".toColorInt(), 50f,ball.x, ball.y )
    }
}