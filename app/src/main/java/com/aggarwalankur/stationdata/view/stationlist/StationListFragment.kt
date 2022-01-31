package com.aggarwalankur.stationdata.view.stationlist

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.navArgs
import androidx.recyclerview.widget.DividerItemDecoration
import com.aggarwalankur.stationdata.R
import com.aggarwalankur.stationdata.databinding.FragmentStationListBinding
import dagger.hilt.android.AndroidEntryPoint
import timber.log.Timber

@AndroidEntryPoint
class StationListFragment : Fragment() {

    private lateinit var viewDataBinding: FragmentStationListBinding

    private val viewModel by viewModels<StationsViewModel>()

    private lateinit var listAdapter: DeparturesAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        viewDataBinding = FragmentStationListBinding.inflate(inflater, container, false)
            .apply {
                viewmodel = viewModel
            }

        return viewDataBinding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        // Set the lifecycle owner to the lifecycle of the view
        viewDataBinding.lifecycleOwner = this.viewLifecycleOwner

        val decoration = DividerItemDecoration(activity, DividerItemDecoration.VERTICAL)
        viewDataBinding.list.addItemDecoration(decoration)

        setupListAdapter()
        setupPullToRefresh()
    }

    private fun setupListAdapter() {
        val viewModel = viewDataBinding.viewmodel
        if (viewModel != null) {
            listAdapter = DeparturesAdapter(viewModel)
            viewDataBinding.list.adapter = listAdapter
        } else {
            Timber.w("ViewModel not initialized when attempting to set up adapter.")
        }
    }

    private fun setupPullToRefresh() {
        val viewModel = viewDataBinding.viewmodel
        viewDataBinding.swipeLayout.setOnRefreshListener {
            viewDataBinding.swipeLayout.isRefreshing = false
            viewModel?.refresh()
        }
    }
}