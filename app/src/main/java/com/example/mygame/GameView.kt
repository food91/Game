package com.example.mygame

import android.content.Context
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import android.graphics.PixelFormat
import android.util.AttributeSet
import android.util.Log
import android.view.SurfaceHolder
import android.view.SurfaceView
import kotlin.math.log


class GameView:SurfaceView,
    SurfaceHolder.Callback,Runnable{
    enum class GameState {
        Menu, Playing, Over
    }
    private var gamestate:GameState = GameState.Menu
    var thread : Thread? = null
    lateinit var canvas: Canvas
    var paint: Paint
    var menux = 0
    var isend : Boolean = true;
    constructor(context: Context) : this(context, null, 0)
    constructor(context: Context, attrs : AttributeSet?) : this(context, attrs, 0)
    constructor(context: Context, attrs: AttributeSet?, defStyleAttr : Int) :
            super(context, attrs, defStyleAttr) {
        holder.addCallback(this)
        setZOrderOnTop(true)
        holder.setFormat(PixelFormat.TRANSLUCENT)
    }
    init {
        paint = Paint()
        paint.isAntiAlias = true //笔迹平滑
        thread = Thread(this);
        isend=false

    }

    fun mDraw(){
        try {
            Log.d("jin","mDraw")
            canvas = holder.lockCanvas()
            canvas.drawColor(android.graphics.Color.WHITE)
            when(gamestate){
                GameState.Menu->menuDraw(canvas)
                GameState.Over->overDraw(canvas)
                GameState.Playing->playingDraw(canvas)
            }
        }catch (e:Exception){
            e.printStackTrace()
        }finally {
            holder.unlockCanvasAndPost(canvas)
        }
    }

    fun menuDraw(canvas:Canvas){
        Log.d("jin","menu")
        paint.color = Color.RED
        paint.textSize = 50F
        canvas.drawText("谢欠扁" +
                "冒险记", 150F, 200F,paint)
        canvas.drawText("按任意键盘继续", ((100+menux)%width).toFloat(), 500F,paint)
        menux+=50
     }

    override fun run() {
        Log.d("jin","run")
        while(!isend){
       //     mLogin()
            mDraw()
            try {
                Thread.sleep(500)
            }catch (e:java.lang.Exception){
                e.printStackTrace()
            }
    }
}

    fun mLogin(){
//        when(gamestate){
//            GameState.Menu->
//            GameState.Over->
//            GameState.Playing->
//        }
    }

    fun overDraw(canvas: Canvas){

    }

    fun playingDraw(canvas: Canvas){

    }

    override fun surfaceCreated(p0: SurfaceHolder) {
        thread?.start()
    }

    override fun surfaceChanged(p0: SurfaceHolder, p1: Int, p2: Int, p3: Int) {

    }

    override fun surfaceDestroyed(p0: SurfaceHolder) {
        isend = true;
    }

}