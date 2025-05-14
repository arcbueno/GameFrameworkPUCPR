package com.example.framework

import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import android.util.Log
import android.view.MotionEvent
import kotlin.math.abs

class SecondScreen(private val game: Game) : Screen(game) {

    private var snake: Snake
    private var music: Music? = null
    private var points: Int = 0
    private var food: Ball? = null
    private var exitButton: Button
    private var elapsedTime = 0f
    private val backgroundColor = Color.BLUE
    private var tounchCoordinate: Coordinate? = null

    init {
        paint.color = Color.BLUE
        try {
            val descriptor = game.context.assets.openFd("music.mp3")
            music = Music(descriptor)
        } catch (e: Exception) {
            Log.d("FrameWork!", "Problemas ao carregar o áudio")
        }
        music?.setLooping(true)
        music?.setVolume(1f)
        music?.play()

        snake = Snake(0f, 300f)

        exitButton = Button(
            Coordinate(15f, 15f),
            Coordinate(115f, 115f),
            Color.CYAN,
            { game.actualScreen = FirstScreen(game) },
            "Sair"
        )


    }

    override fun update(et: Float) {
        elapsedTime = et
        snake.update(et)
    }


    override fun draw() {
        if (food == null) {
            generateNewFoodPosition()
        }

        canvas.drawColor(Color.BLUE)

        paint.color = Color.WHITE
        paint.textSize = 100f
        paint.textAlign = Paint.Align.CENTER
        canvas.drawText("Pontos: $points", canvas.width - 250f, 100f, paint)

        exitButton.onDraw(canvas, paint)

        food?.render(canvas, Color.RED)
        snake.render(canvas)

        readScreen()
    }

    private fun readScreen() {
        if (food != null && snake.currentCoordinate.isNearby(food!!.currentCoordinate, 50f)) {
            onEat()
        }
        if( (snake.x <= 0f || snake.x >= canvas.width.toFloat() || snake.y <= 0f || snake.y >= canvas.height.toFloat())) {
            game.actualScreen = ThirdScreen(game)
        }
    }

    override fun handleEvent(event: Int, x: Float, y: Float) {
//        Log.v("Position y", y.toString())
//        Log.v("Position x", x.toString())
        Log.v("TOUCH","onTouch: " + MotionEvent.actionToString(event))

        if (event == MotionEvent.ACTION_DOWN) {
            tounchCoordinate = Coordinate(x, y)
        }
        if(event == MotionEvent.ACTION_MOVE){
            val newCoordinate = Coordinate(x, y)
            if (tounchCoordinate != null) {
                val deltaX = newCoordinate.x - tounchCoordinate!!.x
                val deltaY = newCoordinate.y - tounchCoordinate!!.y

                if (abs(deltaX) > abs(deltaY)) {
                    if (deltaX > 0) {
                        snake.setNewDirection(Direction.RIGHT)
                    } else {
                        snake.setNewDirection(Direction.LEFT)
                    }
                } else {
                    if (deltaY > 0) {
                        snake.setNewDirection(Direction.DOWN)
                    } else {
                        snake.setNewDirection(Direction.UP)
                    }
                }
            }
            tounchCoordinate = newCoordinate
        }
    }

    private fun onEat() {
        points++
        food = null
        generateNewFoodPosition()
        snake.onEat()
    }

    private fun generateNewFoodPosition() {
        val x = (Math.random() * (canvas.width - 100)).toFloat()
        val y = (Math.random() * (canvas.height - 100)).toFloat()
        paint.color = Color.RED
        food = Ball(x, y)
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

class Button(
    val topLeft: Coordinate,
    val bottomRight: Coordinate,
    val color: Int,
    val onTap: () -> Unit,
    val text: String
) {
    fun onDraw(canvas: Canvas, paint: Paint) {
        paint.color = color
        canvas.drawRect(topLeft.x, topLeft.y, bottomRight.x, bottomRight.y, paint)
        paint.color = Color.WHITE
        paint.textSize = 50f
        paint.textAlign = Paint.Align.CENTER
        canvas.drawText(text, (topLeft.x + bottomRight.x) / 2, bottomRight.y - 25f, paint)
    }

    fun isInside(x: Float, y: Float): Boolean {
        return x >= topLeft.x && x <= bottomRight.x && y >= topLeft.y && y <= bottomRight.y
    }

    fun onTap() {
        onTap.invoke()
    }
}