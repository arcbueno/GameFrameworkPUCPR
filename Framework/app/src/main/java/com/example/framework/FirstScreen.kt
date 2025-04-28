package com.example.framework

import android.graphics.Color
import android.graphics.Paint
import android.view.MotionEvent

class FirstScreen(private val game: Game) : Screen(game) {

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
        canvas.drawText("Toque para iniciar!!!", canvas.width/2f, 100f, paint)
        paint.color = Color.GREEN
        canvas.drawRect(300f, 300f, 780f, 1500f, paint)
        paint.color = Color.WHITE
        paint.textSize = 30f
        canvas.drawText("Copyright FrameWork 2025", canvas.width/2f, 1700f, paint)
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