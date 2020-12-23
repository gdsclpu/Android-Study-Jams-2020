package com.example.lpuactivity.util

import android.content.Context
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import android.graphics.Path
import android.util.AttributeSet
import android.view.View

class SlantView(private val mContext: Context, attrs: AttributeSet?) :
    View(mContext, attrs) {

    var paint: Paint
    var path: Path? = null
    init {
        setWillNotDraw(false)
        paint = Paint(Paint.ANTI_ALIAS_FLAG)
    }
    override fun onDraw(canvas: Canvas) {
        val w = width
        val h = height
        paint.strokeWidth = 2F
        paint.color = Color.WHITE
        paint.style = Paint.Style.FILL_AND_STROKE
        paint.isAntiAlias = true
        path = Path()
        path!!.fillType = Path.FillType.EVEN_ODD
        path!!.moveTo(0F, (h/1.2).toFloat())
        path!!.lineTo(0F, h.toFloat())
        path!!.lineTo(w.toFloat(), h.toFloat())
        path!!.close()
        canvas.drawPath(path!!, paint)
    }


}