package irina.activityreminder.ui.main

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import irina.activityreminder.R
import irina.activityreminder.databinding.MainFragmentBinding
import irina.activityreminder.ui.SchedulerService
import kotlinx.android.synthetic.main.main_fragment.view.*


class MainFragment : Fragment() {

    private lateinit var mainFragmentBinding: MainFragmentBinding
    private lateinit var viewModel: MainViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        mainFragmentBinding = DataBindingUtil.inflate(
            inflater,
            R.layout.main_fragment,
            container,
            false
        )
        return mainFragmentBinding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        view.settings_button.setOnClickListener { findNavController().navigate(R.id.action_mainFragment_to_settingsFragment) }
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel = ViewModelProvider(this).get(MainViewModel::class.java)
        mainFragmentBinding.vm = viewModel
        mainFragmentBinding.lifecycleOwner = this
        viewModel.isServiceShouldRun.observe(viewLifecycleOwner, Observer {
            if (it) {
                context?.let { it1 -> SchedulerService.startServiceIfItIsNotRunning(it1) }
            } else {
                context?.let { it1 -> SchedulerService.stopService(it1) }
            }
        })
    }

    override fun onResume() {
        super.onResume()
        viewModel.onResume()
    }

    override fun onPause() {
        super.onPause()
        viewModel.onPause()
    }


}
