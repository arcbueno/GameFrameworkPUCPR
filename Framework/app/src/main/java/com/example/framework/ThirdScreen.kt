package com.example.framework

import android.graphics.Color
import android.graphics.Paint
import android.view.MotionEvent

class ThirdScreen(private val game: Game) : Screen(game) {
    init {
        paint.typeface = Fonts.chalkboard
        paint.textAlign = Paint.Align.CENTER
        paint.isFakeBoldText = true
        paint.textSkewX = 0.1f
    }

    override fun update(et: Float) {

    }

    override fun draw() {
        canvas.drawColor(Color.BLACK)
        paint.color = Color.WHITE
        paint.textSize = 100f
        canvas.drawText("Game Over", canvas.width/2f, 100f, paint)
        paint.textSize = 50f
        canvas.drawText("Toque para tentar novamente", canvas.width/2f, 300f, paint)
    }

    override fun handleEvent(event: Int, x: Float, y: Float) {
        if (event == MotionEvent.ACTION_DOWN) {
            game.actualScreen = SecondScreen(game)
        }
    }

    override fun onPause() {
        TODO("Not yet implemented")
    }

    override fun onResume() {
        TODO("Not yet implemented")
    }

    override fun backPressed() {
        TODO("Not yet implemented")
    }

    override fun onExit() {
        TODO("Not yet implemented")
    }
}