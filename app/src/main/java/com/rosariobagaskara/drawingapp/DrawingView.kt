package com.rosariobagaskara.drawingapp

import android.content.Context
import android.graphics.*
import android.util.AttributeSet
import android.view.MotionEvent
import android.view.View
import androidx.constraintlayout.widget.ConstraintSet


class DrawingView(context: Context, attrs: AttributeSet): View(context, attrs) {

    private var drawPath : CustomPath? = null
    private var bitmap: Bitmap? = null
    private var drawPaint: Paint? = null
    private var canvasPaint: Paint? = null
    private var brushSize: Float = 0.toFloat()
    private var color = Color.BLACK
    private var canvas: Canvas? = null
    init {
        setupDrawing()
    }

    override fun onSizeChanged(w: Int, h: Int, oldw: Int, oldh: Int) {
        super.onSizeChanged(w, h, oldw, oldh)
        bitmap = Bitmap.createBitmap(w, h, Bitmap.Config.ARGB_8888)
        canvas = Canvas(bitmap!!)
    }

    override fun onDraw(canvas: Canvas) {
        super.onDraw(canvas)
        canvas.drawBitmap(bitmap!!, 0f, 0f, canvasPaint)
        if(!drawPath!!.isEmpty){
            drawPaint!!.strokeWidth = drawPath!!.brushThickness
            drawPaint!!.color = drawPath!!.color
            canvas.drawPath(drawPath!!, drawPaint!!)
        }
    }

    override fun onTouchEvent(event: MotionEvent?): Boolean {
        val touchX = event?.x
        val touchY = event?.y
        when(event?.action){
            MotionEvent.ACTION_DOWN -> {
                drawPath!!.color = color
                drawPath!!.brushThickness = brushSize

                drawPath!!.reset()
                drawPath!!.moveTo(touchX!!, touchY!!)
            }
            MotionEvent.ACTION_MOVE -> {
                drawPath!!.lineTo(touchX!!, touchY!!)
            }
            MotionEvent.ACTION_UP -> {
                drawPath!!.color = color
                drawPath!!.brushThickness = brushSize
                drawPath!!.reset()
                drawPath!!.moveTo(touchX!!, touchY!!)
            }
            else -> return false
        }
        invalidate()
        return true
    }

    private fun setupDrawing() {
        drawPath = CustomPath(color, brushSize)
        drawPaint = Paint()
        drawPaint!!.color = color
        drawPaint!!.style = Paint.Style.STROKE
        drawPaint!!.strokeJoin = Paint.Join.ROUND
        drawPaint!!.strokeCap = Paint.Cap.ROUND
        canvasPaint = Paint(Paint.DITHER_FLAG)
        brushSize = 20.toFloat()
    }

    inner internal class CustomPath(var color: Int, var brushThickness: Float): Path() {

    }
}