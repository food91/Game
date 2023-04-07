package com.example.mygame

import android.graphics.Bitmap
import android.graphics.Canvas
import android.graphics.Rect


class Utils {

    /*------------------------------------
	 * 绘制图片
	 *
	 * @param		x 屏幕上的x坐标
	 * @param		y 屏幕上的y坐标
	 * @param		w 要绘制的图片的宽度
	 * @param		h 要绘制的图片的高度
	 * @param		bx图片上的x坐标
	 * @param		by图片上的y坐标
	 *
	 * @return		null
	 ------------------------------------*/
   companion object{
  fun drawImage(
      canvas: Canvas, blt: Bitmap?,
      x: Int?, y: Int?, w: Int?, h: Int?, bx: Int?, by: Int?
    ) {
        var src: Rect? = Rect() // 图片
        var dst: Rect? = Rect() // 屏幕
      if (src != null) {
          if (bx != null) {
              src.left = bx
          }
      }
      if (src != null) {
          if (by != null) {
              src.top = by
          }
      }
      if (src != null) {
          if (bx != null) {
              src.right = bx + w!!
          }
      }
      if (src != null) {
          if (by != null) {
              src.bottom = by + h!!
          }
      }
      if (dst != null) {
          if (x != null) {
              dst.left = x
          }
      }
      if (dst != null) {
          if (y != null) {
              dst.top = y
          }
      }
      if (dst != null) {
          if (x != null) {
              dst.right = x + w!!
          }
      }
      if (dst != null) {
          if (y != null) {
              dst.bottom = y + h!!
          }
      }
      if (blt != null) {
          if (dst != null) {
              canvas.drawBitmap(blt, src, dst, null)
          }
      }
        src = null
        dst = null
    }
    }

}