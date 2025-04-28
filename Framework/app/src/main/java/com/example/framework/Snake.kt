package com.example.framework

class Snake {
    var body: MutableList<Ball> = mutableListOf()

    fun onEat(){
        body.add(Ball(body[body.size - 1].x, body[body.size - 1].y))
    }

    fun update(et: Float) {
        for (i in body.size - 1 downTo 1) {
            body[i].x = body[i - 1].x
            body[i].y = body[i - 1].y
        }
    }
}