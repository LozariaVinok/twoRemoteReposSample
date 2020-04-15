package irina.activityreminder.ui.widget

import android.content.Context
import android.graphics.Canvas
import android.graphics.Paint
import android.graphics.RectF
import android.util.AttributeSet
import android.view.View
import androidx.core.content.res.ResourcesCompat
import androidx.databinding.BindingAdapter
import irina.activityreminder.R
import kotlin.math.min


class PieProgressView(context: Context?, attrs: AttributeSet?) :
    View(context, attrs) {

    var max = 100
        set(value) {
            field = value
            invalidate()
        }
    var progress: Float = 0F
        set(value) {
            field = value
            invalidate()
        }
    private val startAngle = -90
    private val progressPaint: Paint
    private val backgroundPaint: Paint
    private val circleRect: RectF = RectF()
    private val progressRect: RectF = RectF()
    private val circleStrokeWidth = convertDpToPx(context!!, 16.0F)

    override fun onDraw(canvas: Canvas) {
        canvas.drawArc(
            circleRect,
            0.0F,
            360.0F,
            true,
            backgroundPaint
        )
        val sweepAngleCircle = 360 - 360 * progress / max.toFloat()
        canvas.drawArc(progressRect, startAngle.toFloat(), sweepAngleCircle, true, progressPaint)
    }

    override fun onMeasure(widthMeasureSpec: Int, heightMeasureSpec: Int) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec)
        val width = resolveSize(
            DEFAULT_VIEW_SIZE,
            widthMeasureSpec
        )
        val height = resolveSize(
            DEFAULT_VIEW_SIZE,
            heightMeasureSpec
        )
        val viewSize = min(width, height)
        circleRect.left = (width - viewSize) / 2.toFloat() + circleStrokeWidth / 2
        circleRect.top = (height - viewSize) / 2.toFloat() + circleStrokeWidth / 2
        circleRect.bottom = circleRect.top + viewSize - circleStrokeWidth
        circleRect.right = circleRect.left + viewSize - circleStrokeWidth

        progressRect.left = (width - viewSize) / 2.toFloat() + circleStrokeWidth * 3 / 2
        progressRect.top = (height - viewSize) / 2.toFloat() + circleStrokeWidth * 3 / 2
        progressRect.bottom = progressRect.top + viewSize - circleStrokeWidth * 3
        progressRect.right = progressRect.left + viewSize - circleStrokeWidth * 3
        setMeasuredDimension(width, height)
    }


    fun convertDpToPx(context: Context, dp: Float): Float {
        return dp * context.resources.displayMetrics.density
    }

    companion object {
        private const val DEFAULT_VIEW_SIZE = 96

    }

    init {
        val backgroundColor: Int = ResourcesCompat.getColor(resources, R.color.colorAccent, null)
        val progressColor: Int = ResourcesCompat.getColor(resources, R.color.colorAccent, null)
        backgroundPaint = Paint(Paint.ANTI_ALIAS_FLAG)
        backgroundPaint.color = backgroundColor
        backgroundPaint.strokeWidth = circleStrokeWidth
        backgroundPaint.style = Paint.Style.STROKE
        progressPaint = Paint(Paint.ANTI_ALIAS_FLAG)
        progressPaint.color = progressColor
        progressPaint.style = Paint.Style.FILL
    }
}

@BindingAdapter("app:progress")
fun setProgress(pieProgressView: PieProgressView, progress: Float) {
    pieProgressView.progress = progress
}

@BindingAdapter("app:max")
fun setMax(pieProgressView: PieProgressView, max: Int) {
    pieProgressView.max = max
}