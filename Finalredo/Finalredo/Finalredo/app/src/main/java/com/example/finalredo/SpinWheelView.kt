package com.example.finalredo

import android.annotation.SuppressLint
import android.content.Context
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import android.graphics.RectF
import android.util.AttributeSet
import android.view.View
import java.util.Random

class SpinWheelView(context: Context, attrs: AttributeSet) : View(context, attrs) {

    private val wheelPaint = Paint().apply {
        isAntiAlias = true
        style = Paint.Style.FILL
    }

    private val circlePaint = Paint().apply {
        isAntiAlias = true
        color = Color.BLACK
        style = Paint.Style.FILL
    }

    private val segmentColors = listOf(Color.RED, Color.GREEN, Color.BLUE, Color.YELLOW)

    fun spinWheel() {
        val random = Random()
        val rotations = 5 + random.nextInt(5)  // Between 5 and 10 full rotations
        val rotationDegrees = rotations * 360f + random.nextInt(360)  // Add random end position
        animate().rotation(rotationDegrees).setDuration(3000).start()
    }

    @SuppressLint("DrawAllocation")
    override fun onDraw(canvas: Canvas) {
        val segmentDegree = 360f / segmentColors.size
        val rect = RectF(0f, 0f, width.toFloat(), height.toFloat())

        for (i in segmentColors.indices) {
            wheelPaint.color = segmentColors[i]
            canvas.drawArc(rect, i * segmentDegree, segmentDegree, true, wheelPaint)
        }

        canvas.drawCircle(width / 2f, height / 2f, 40f, circlePaint)  // Draw center circle
    }
}