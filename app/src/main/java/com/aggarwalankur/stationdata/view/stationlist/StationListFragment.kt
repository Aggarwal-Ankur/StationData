package com.aggarwalankur.stationdata.view.stationlist

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.aggarwalankur.stationdata.R
import com.aggarwalankur.stationdata.databinding.FragmentStationListBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class StationListFragment : Fragment() {

    private lateinit var viewDataBinding: FragmentStationListBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        viewDataBinding = FragmentStationListBinding.inflate(inflater, container, false)
            .apply {
                //viewmodel = viewModel
            }

        viewDataBinding.button.setOnClickListener() {
            viewDataBinding.button.text = getString(R.string.ok)
        }
        return viewDataBinding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        // Set the lifecycle owner to the lifecycle of the view
        viewDataBinding.lifecycleOwner = this.viewLifecycleOwner
    }
}