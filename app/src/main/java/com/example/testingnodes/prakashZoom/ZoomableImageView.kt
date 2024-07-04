package com.example.testingnodes.prakashZoom

import android.content.Context
import android.graphics.Matrix
import android.util.AttributeSet
import android.view.MotionEvent
import android.view.ScaleGestureDetector

class ZoomableImageView(context: Context, attrs: AttributeSet) : androidx.appcompat.widget.AppCompatImageView(context, attrs) {

    private val matrix = Matrix()
    private var scale = 1.0f
    private val scaleGestureDetector: ScaleGestureDetector

    init {
        scaleGestureDetector = ScaleGestureDetector(context, ScaleListener())
        imageMatrix = matrix
        scaleType = ScaleType.MATRIX
    }

    override fun onTouchEvent(event: MotionEvent): Boolean {
        scaleGestureDetector.onTouchEvent(event)

        val action = event.action
        when (action and MotionEvent.ACTION_MASK) {
            MotionEvent.ACTION_DOWN -> {
                // Single touch event
            }
            MotionEvent.ACTION_MOVE -> {
                // Handle panning
                if (event.pointerCount == 1) {
                    matrix.postTranslate(event.x - previousX, event.y - previousY)
                    imageMatrix = matrix
                }
            }
            MotionEvent.ACTION_POINTER_DOWN -> {
                // Multitouch event, ignore panning
            }
        }

        previousX = event.x
        previousY = event.y
        return true
    }

    private inner class ScaleListener : ScaleGestureDetector.SimpleOnScaleGestureListener() {
        override fun onScale(detector: ScaleGestureDetector): Boolean {
            scale *= detector.scaleFactor
            scale = Math.max(1.0f, Math.min(scale, 5.0f)) // Limit the scale range
            matrix.setScale(scale, scale, detector.focusX, detector.focusY)
            imageMatrix = matrix
            return true
        }
    }

    private var previousX = 0f
    private var previousY = 0f
}
