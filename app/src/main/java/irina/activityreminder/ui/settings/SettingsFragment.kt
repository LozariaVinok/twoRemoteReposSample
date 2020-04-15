package irina.activityreminder.ui.settings

import android.app.TimePickerDialog
import android.app.TimePickerDialog.OnTimeSetListener
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import irina.activityreminder.R
import irina.activityreminder.databinding.SettingsFragmentBinding
import kotlinx.android.synthetic.main.settings_fragment.*
import kotlinx.android.synthetic.main.settings_fragment.view.*


class SettingsFragment : Fragment() {

    private lateinit var settingsFragmentBinding: SettingsFragmentBinding
    private lateinit var viewModel: SettingsViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        settingsFragmentBinding = DataBindingUtil.inflate(
            inflater,
            R.layout.settings_fragment,
            container,
            false
        )
        settingsFragmentBinding.lifecycleOwner = this
        return settingsFragmentBinding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        view.decrease_value.setOnClickListener {
            val value = reminder_period_value.text.toString().toInt()
            if (value > 1) {
                reminder_period_value.text =
                    (reminder_period_value.text.toString().toInt() - 1).toString()
            }
        }
        view.increase_value.setOnClickListener {
            val value = reminder_period_value.text.toString().toInt()
            reminder_period_value.text = (value + 1).toString()
        }
        view.do_not_notify_from_value.setOnClickListener {
            val picker = TimePickerDialog(
                activity,
                OnTimeSetListener { _, sHour, sMinute ->
                    view.do_not_notify_from_value.text =
                        ("${sHour.toString().padStart(2, '0')}:${sMinute.toString()
                            .padStart(2, '0')}")
                },
                viewModel.doNotNotifyFromHour,
                viewModel.doNotNotifyFromMinutes,
                true
            )
            picker.show()
        }

        view.do_not_notify_to_value.setOnClickListener {
            val picker = TimePickerDialog(
                activity,
                OnTimeSetListener { _, sHour, sMinute ->
                    view.do_not_notify_to_value.text =
                        ("${sHour.toString().padStart(2, '0')}:${sMinute.toString()
                            .padStart(2, '0')}")
                },
                viewModel.doNotNotifyToHour,
                viewModel.doNotNotifyToMinutes,
                true
            )
            picker.show()
        }
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel = ViewModelProvider(this).get(SettingsViewModel::class.java)
        settingsFragmentBinding.vm = viewModel
    }

}
