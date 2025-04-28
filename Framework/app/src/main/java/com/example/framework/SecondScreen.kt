package com.example.framework

import android.graphics.Color
import android.graphics.Paint
import android.util.Log
import android.view.MotionEvent

class SecondScreen(private val game: Game) : Screen(game) {

    private var ball: Ball
    private var music: Music? = null
    private var points: Int = 0
    private var food: Ball? = null

    init {
        paint.color = Color.BLUE
        ball = Ball(0f, 300f)
        try {
            val descriptor = game.context.assets.openFd("music.mp3")
            music = Music(descriptor)
        } catch (e: Exception) {
            Log.d("FrameWork!", "Problemas ao carregar o áudio")
        }
        music?.setLooping(true)
        music?.setVolume(1f)
        music?.play()

    }

    override fun update(et: Float) {
        ball.update(et)
    }


    override fun draw() {
        if(food == null){
            generateNewFoodPosition()
        }

        canvas.drawColor(Color.BLACK)
        paint.color = Color.WHITE
        paint.textSize = 100f
        paint.textAlign = Paint.Align.CENTER
        canvas.drawText("Toque aqui para sair", canvas.width/2f, 100f, paint)
        canvas.drawText("Pontos: $points", canvas.width/2f, 200f, paint)
        paint.color = Color.BLUE
        canvas.drawRect(300f, 300f, 780f, 1500f, paint)
        ball.render(canvas)
        food?.render(canvas, Color.RED)

        readScreen()
    }

    private fun readScreen(){
        if(food!=null && ball.currentCoordinate.isNearby(food!!.currentCoordinate, 50f)){
            onEat()
        }
    }

    override fun handleEvent(event: Int, x: Float, y: Float) {
        Log.v("Position y", y.toString())
        Log.v("Position x", x.toString())
        if (event == MotionEvent.ACTION_DOWN && y <= 100f && y >= 0f) {
            game.actualScreen = FirstScreen(game)
        }

        if (event == MotionEvent.ACTION_DOWN) {
            when(ball.direction){
                Direction.UP -> ball.setNewDirection(Direction.RIGHT)
                Direction.DOWN -> ball.setNewDirection(Direction.LEFT)
                Direction.LEFT -> ball.setNewDirection(Direction.UP)
                Direction.RIGHT -> ball.setNewDirection(Direction.DOWN)
            }
        }
    }

    fun onEat(){
        points++
        food = null
        generateNewFoodPosition()
    }

    fun generateNewFoodPosition(){
        var x = (Math.random() * (canvas.width - 100)).toFloat()
        var y = (Math.random() * (canvas.height - 100)).toFloat()
        paint.color = Color.RED
        food = Ball(x, y)
        food?.limitTopLeft = ball.limitTopLeft
        food?.limitTopRight = ball.limitTopRight
        food?.limitBottomLeft = ball.limitBottomLeft
        food?.limitBottomRight = ball.limitBottomRight
    }

    override fun onPause() {
        music?.pause()
        music?.dispose()
    }

    override fun onResume() {

    }

    override fun backPressed() {
        game.actualScreen = FirstScreen(game)
    }

    override fun onExit() {
        music?.pause()
        music?.dispose()
    }
}