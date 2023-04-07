package com.example  .mygame

import android.content.Context
import android.graphics.*
import android.text.Layout
import android.text.StaticLayout
import android.text.TextPaint
import android.util.AttributeSet
import android.util.Log
import android.view.MotionEvent
import android.view.SurfaceHolder
import android.view.SurfaceView


class GameView:SurfaceView,
    SurfaceHolder.Callback,Runnable{
    enum class GameState {
        Menu, Playing, Over,Suspend
    }
    private var gamestate:GameState = GameState.Menu
    var thread : Thread? = null
    lateinit var canvas: Canvas
    var nextEvent: NextEvent? = null
    var paint: Paint
    var movictime = 0
    var menux = 0
    var isend : Boolean = true;
    val textPaint: TextPaint
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
        nextEvent = NextEvent()
        movictime =0
        textPaint = TextPaint()
        textPaint.setARGB(0xFF, 0xFF, 0, 0)
        textPaint.textSize = 50.0f
    }

    override fun onTouchEvent(event: MotionEvent?): Boolean {
        if (event != null) {
            if(gamestate==GameState.Menu){
                if(event.action == MotionEvent.ACTION_DOWN){
                    gamestate = GameState.Suspend
                }
            }
            if(gamestate == GameState.Suspend){
                if(movictime>25){
                    gamestate = GameState.Playing
                }
            }
            if (event.action == MotionEvent.ACTION_DOWN) {
                nextEvent?.downX  = (event.x.toInt())
                nextEvent?.downX  = (event.y.toInt())
            } else if (event.action == MotionEvent.ACTION_UP) {
                nextEvent?.upX =(event.x.toInt())
                nextEvent?.upY =(event.y.toInt())
            }
        }
        return true
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
                GameState.Suspend->startMovice(canvas)
            }
        }catch (e:Exception){
            e.printStackTrace()
        }finally {
            holder.unlockCanvasAndPost(canvas)
        }
    }

    fun startMovice(canvas: Canvas){
        canvas.drawColor(android.graphics.Color.BLACK)
        var text = "    很久很久以前，谢欠扁同学流落到了一个\n孤岛上,他必须找到武器和离开这座岛屿..."
        var str =""
        if(movictime+1<text.length){
             for(i in 0 until movictime){
                 str+=text[i]
             }
        }
        else{
            str = text
        }
        val layout =
            StaticLayout(str, textPaint, width, Layout.Alignment.ALIGN_NORMAL,
                1.0f, 0.0f, true)
       layout.draw(canvas)
        movictime++;
    }

    fun menuDraw(canvas:Canvas){
        Log.d("jin","menu")
        paint.color = Color.RED
        paint.textSize = 50F
        canvas.drawText("谢欠扁" +
                "冒险记", 150F, 200F,paint)
        canvas.drawText("按任意键盘继续", ((100+menux)%width).toFloat(), 500F,paint)
        menux+=20
     }

    override fun run() {
        Log.d("jin","run")
        while(!isend){
            mLogin()
            mDraw()
            try {
                Thread.sleep(80)
            }catch (e:java.lang.Exception){
                e.printStackTrace()
            }
    }
}

    fun mLogin(){
        when(gamestate){
            GameState.Menu->menuLogic()
            GameState.Over->overLogic()
            GameState.Playing->playingLogic()
            GameState.Suspend->SuspendingLogic()
        }
        nextEvent!!.init()
    }

    fun SuspendingLogic(){

    }
    private fun menuLogic() {
        if (nextEvent!!.upX > 0) {
            gamestate = GameState.Playing
        }
    }

    private fun playingLogic() {
        if (nextEvent!!.getDir() == NextEvent.DOWN) {
            gamestate = GameState.Over
        }
    }

    private fun overLogic() {
        if (nextEvent!!.getDir() == NextEvent.RIGHT) {
            gamestate = GameState.Menu
        }
    }

    fun overDraw(canvas: Canvas){

    }

    fun playingDraw(canvas: Canvas){
        var image = resources.getDrawable(R.drawable.leadingrole)
        val srcRect = Rect(
            0, 0, image.intrinsicWidth,
            image.intrinsicHeight
        )
        val dstRect = Rect(srcRect)
        val bitmap = Bitmap.createBitmap(
            image.intrinsicWidth,
            image.intrinsicHeight, Bitmap.Config.ALPHA_8
        )
        val canvas = Canvas()
        canvas.drawBitmap(bitmap, srcRect, dstRect, paint)
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