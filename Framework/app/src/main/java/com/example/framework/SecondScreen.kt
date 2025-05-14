package com.example.framework

import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import android.util.Log
import android.view.MotionEvent

class SecondScreen(private val game: Game) : Screen(game) {

    private var snake: Snake
    private var music: Music? = null
    private var points: Int = 0
    private var food: Ball? = null
    private var topButton: Button
    private var bottomButton: Button
    private var leftButton: Button
    private var rightButton: Button
    private var exitButton: Button
    private var elapsedTime = 0f

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

        topButton = Button(
            Coordinate((canvas.width / 2) - 100f, canvas.height - 400f),
            Coordinate((canvas.width / 2) + 100f, canvas.height - 300f),
            Color.DKGRAY,
            { snake.setNewDirection(Direction.UP) },
            "CIMA"
        )

        bottomButton = Button(
            Coordinate((canvas.width / 2) - 100f, canvas.height - 200f),
            Coordinate((canvas.width / 2) + 100f, canvas.height - 100f),
            Color.DKGRAY,
            { snake.setNewDirection(Direction.DOWN) },
            "BAIXO"
        )
        leftButton = Button(
            Coordinate((canvas.width / 2) - 415f, canvas.height - 300f),
            Coordinate((canvas.width / 2) - 150f, canvas.height - 200f),
            Color.DKGRAY,
            { snake.setNewDirection(Direction.LEFT) },
            "ESQUERDA"
        )
        rightButton = Button(
            Coordinate((canvas.width / 2) + 150f, canvas.height - 300f),
            Coordinate((canvas.width / 2) + 400f, canvas.height - 200f),
            Color.DKGRAY,
            { snake.setNewDirection(Direction.RIGHT) },
            "DIREITA"
        )

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


        topButton.onDraw(canvas, paint)
        bottomButton.onDraw(canvas, paint)
        leftButton.onDraw(canvas, paint)
        rightButton.onDraw(canvas, paint)
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
        Log.v("Position y", y.toString())
        Log.v("Position x", x.toString())

        if (event == MotionEvent.ACTION_DOWN) {
            if (leftButton.isInside(x, y)) {
                leftButton.onTap()
            }
            if (rightButton.isInside(x, y)) {
                rightButton.onTap()
            }
            if (topButton.isInside(x, y)) {
                topButton.onTap()
            }
            if (bottomButton.isInside(x, y)) {
                bottomButton.onTap()
            }
            if (exitButton.isInside(x, y)) {
                exitButton.onTap()
            }
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
//        food?.limitTopLeft = snake.limitTopLeft
//        food?.limitTopRight = snake.limitTopRight
//        food?.limitBottomLeft = snake.limitBottomLeft
//        food?.limitBottomRight = snake.limitBottomRight
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