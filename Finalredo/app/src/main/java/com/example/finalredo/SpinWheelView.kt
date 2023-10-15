package com.example.finalredo


import android.content.Context
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import android.graphics.RectF
import android.util.AttributeSet
import android.view.View
import java.util.Random

class SpinWheelView(context: Context, attrs: AttributeSet) : View(context, attrs) {

    private val rect = RectF()


    private val wheelPaint = Paint().apply {
        isAntiAlias = true
        style = Paint.Style.FILL
    }

    private val circlePaint = Paint().apply {
        isAntiAlias = true
        color = Color.BLACK
        style = Paint.Style.FILL
    }

    var segments: List<String> = emptyList()
    private var segmentColors: List<Int> = listOf(Color.RED, Color.GREEN, Color.BLUE, Color.YELLOW) // Default colors

    interface OnSegmentSelectedListener {
        fun onSegmentSelected(segmentIndex: Int)
    }

    private var listener: OnSegmentSelectedListener? = null

    fun setOnSegmentSelectedListener(listener: OnSegmentSelectedListener) {
        this.listener = listener
    }

    fun updateSegments(segmentList: List<String>) {
        this.segments = segmentList

        // Assign colors for the new segments
        segmentColors = generateColorsForSegments(segmentList.size)

        invalidate()
    }
    private fun generateColorsForSegments(size: Int): List<Int> {
        val colors = mutableListOf<Int>()

        for (i in 0 until size) {
            if (i < segmentColors.size) {
                colors.add(segmentColors[i])
            } else {
                colors.add(generateRandomColor())
            }
        }

        return colors
    }

    fun spinWheel() {
        val random = Random()
        val rotations = 5 + random.nextInt(5)  // Between 5 and 10 full rotations
        val endRotationDegree = random.nextInt(360)  // Random end position
        val rotationDegrees = rotations * 360f + endRotationDegree
        animate().rotation(rotationDegrees).setDuration(3000).withEndAction {
            val segmentDegree = 360f / segments.size
            val landedSegmentIndex = (endRotationDegree / segmentDegree).toInt()
            listener?.onSegmentSelected(landedSegmentIndex)
        }.start()
    }

    override fun onDraw(canvas: Canvas) {
        rect.set(0f, 0f, width.toFloat(), height.toFloat())
        val segmentDegree = 360f / segments.size

        for (i in segments.indices) {
            wheelPaint.color = segmentColors[i]
            canvas.drawArc(rect, i * segmentDegree, segmentDegree, true, wheelPaint)
        }

        canvas.drawCircle(width / 2f, height / 2f, 40f, circlePaint)
    }


    private fun generateRandomColor(): Int {
        val random = Random()
        return Color.argb(255, random.nextInt(256), random.nextInt(256), random.nextInt(256))
    }
}