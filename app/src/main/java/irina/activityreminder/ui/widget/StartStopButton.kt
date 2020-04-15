package irina.activityreminder.ui.widget

import android.content.Context
import android.util.AttributeSet
import androidx.appcompat.widget.AppCompatImageButton
import androidx.databinding.BindingAdapter
import androidx.vectordrawable.graphics.drawable.AnimatedVectorDrawableCompat
import irina.activityreminder.R

class StartStopButton : AppCompatImageButton {

    var isStarted = true
        set(value) {
            if (field != value) {
                animateStateChanging(value)
            }
            field = value
        }

    private fun animateStateChanging(isStarted: Boolean) {
        if (isStarted) {
            val avd =
                AnimatedVectorDrawableCompat.create(context, R.drawable.ic_start_to_stop_animated)
            setImageDrawable(avd)
            avd?.start()

        } else {
            val avd =
                AnimatedVectorDrawableCompat.create(context, R.drawable.ic_stop_to_start_animated)
            setImageDrawable(avd)
            avd?.start()
        }
    }

    constructor(context: Context, attrs: AttributeSet) : this(context, attrs, 0)

    constructor(context: Context, attrs: AttributeSet, defStyle: Int) : super(
        context,
        attrs,
        defStyle
    )


}

@BindingAdapter("app:isStarted")
fun setIsStarted(startStopButton: StartStopButton, isStarted: Boolean) {
    startStopButton.isStarted = isStarted
}