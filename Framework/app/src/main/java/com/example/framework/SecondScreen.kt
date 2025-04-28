package com.example.framework

import android.graphics.Color
import android.util.Log
import android.view.MotionEvent

class SecondScreen(private val game: Game) : Screen(game) {

    private var ball: Ball
    private var music: Music? = null

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
        canvas.drawColor(Color.BLACK)
        canvas.drawRect(300f, 300f, 780f, 1500f, paint)
        ball.render(canvas)
    }

    override fun handleEvent(event: Int, x: Float, y: Float) {
        if (event == MotionEvent.ACTION_DOWN) {
//            onExit()
            game.actualScreen = FirstScreen(game)
        }
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