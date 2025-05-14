package com.example.framework

import android.graphics.Color
import android.graphics.Paint
import android.view.MotionEvent
import androidx.core.graphics.toColorInt

class FirstScreen(private val game: Game) : Screen(game) {
    private val snakeBodyColor = "#a3e598".toColorInt()
    init {
        paint.typeface = Fonts.chalkboard
        paint.textAlign = Paint.Align.CENTER
        paint.isFakeBoldText = true
        paint.textSkewX = 0.1f
    }

    override fun update(et: Float) {

    }

    override fun draw() {
        canvas.drawColor("#249d00".toColorInt())
        paint.color = Color.BLACK
        paint.textSize = 100f
        canvas.drawText("Snake_Case", canvas.width/2f, 300f, paint)
        paint.textSize = 50f
        canvas.drawText("Toque para começar", canvas.width/2f, 600f, paint)
        drawSnake()
        paint.color = Color.DKGRAY
        paint.textSize = 30f
        canvas.drawText("Copyright Snake_Case 2025", canvas.width/2f, 1700f, paint)
    }

    private fun drawSnake(){

        paint.color = snakeBodyColor
        for (i in 0..200 step 50) {
            canvas.drawCircle(canvas.width / 2f - i, canvas.height / 2f, 50f, paint)
        }
        paint.color = Color.GREEN
        canvas.drawCircle(canvas.width/2f, canvas.height/2f+50f, 50f, paint)
        paint.color = Color.RED
        canvas.drawCircle(canvas.width/2f, canvas.height/2f+100f, 15f, paint)
        paint.color = snakeBodyColor
        for (i in 0..350 step 50) {
            canvas.drawCircle(canvas.width / 2f - 200f, canvas.height / 2f + i, 50f, paint)
        }
        for (i in -150..200 step 50) {
            canvas.drawCircle(canvas.width / 2f + i, canvas.height / 2f + 350f, 50f, paint)
        }
        for (i in 300 downTo 50 step 50) {
            canvas.drawCircle(canvas.width / 2f + 200f, canvas.height / 2f + i, 50f, paint)
        }
    }

    override fun handleEvent(event: Int, x: Float, y: Float) {
        if (event == MotionEvent.ACTION_DOWN) {
            game.actualScreen = SecondScreen(game)
        }
    }

    override fun onPause() {

    }

    override fun onResume() {

    }

    override fun backPressed() {

    }

    override fun onExit() {
        TODO("Not yet implemented")
    }
}