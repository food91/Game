package com.example.mygame

class NextEvent() {
    companion object{
       val DOWN = 4
       val LEFT = 1
       val RIGHT = 2
       val UP = 3
   }
    val QUIET = -1 //没有滑动
    var downX =0
    var downY = 0
    var upX=0
    var upY=0
    private var dir = 0
     init{
     init()
    }

    fun init(){
        this.dir = QUIET
        this.downX = -1
        this.downY = -1
        this.upX = -1
        this.upY = -1
    }

    fun getDir(): Int {
        val offsetX: Float
        val offsetY: Float
        offsetX = (upX - downX).toFloat()
        offsetY = (upY - downY).toFloat()
        if (Math.abs(offsetX) > Math.abs(offsetY)) {
            if (offsetX > 5) {
                dir = RIGHT
            } else if (offsetX < -5) {
                dir = LEFT
            }
        } else {
            if (offsetY > 5) {
                dir = DOWN
            } else if (offsetY < -5) {
                dir = UP
            }
        }
        return dir
    }


}