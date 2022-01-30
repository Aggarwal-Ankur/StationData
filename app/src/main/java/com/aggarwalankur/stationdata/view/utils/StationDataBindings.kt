package com.aggarwalankur.stationdata.view.utils

import android.view.View
import android.widget.Button
import android.widget.ProgressBar
import android.widget.TextView
import androidx.databinding.BindingAdapter
import androidx.recyclerview.widget.RecyclerView
import com.aggarwalankur.stationdata.network.dto.Departure
import com.aggarwalankur.stationdata.view.stationlist.DeparturesAdapter
import com.aggarwalankur.stationdata.view.stationlist.LoadStatus
import timber.log.Timber

/**
 * [BindingAdapter]s for the [Departures]s list.
 */
@BindingAdapter("app:items")
fun setItems(listView: RecyclerView, items: List<Departure>?) {
    items?.let {
        (listView.adapter as DeparturesAdapter).submitList(items)
    }
}

/**
 * [BindingAdapter]s for the [LoadStatus]
 */
@BindingAdapter("progressBarVisibility")
fun bindProgressBar(progressBar: ProgressBar, status: LoadStatus?) {
    Timber.d("List items in recyclerview status = ${status}")

    if (status is LoadStatus.LOADING){
        progressBar.visibility = View.VISIBLE
    } else {
        progressBar.visibility = View.GONE
    }

}

/**
 * [BindingAdapter]s for the [LoadStatus]
 */
@BindingAdapter("retryButtonVisibility")
fun bindRetryButton(button: Button, status: LoadStatus?) {
    if (status is LoadStatus.ERROR){
        button.visibility = View.VISIBLE
    } else {
        button.visibility = View.GONE
    }
}

/**
 * [BindingAdapter]s for the [LoadStatus]
 */
@BindingAdapter("emptyListTvVisibility")
fun bindEmptyListTv(textView: TextView, status: LoadStatus?) {
    if (status is LoadStatus.ERROR){
        textView.visibility = View.VISIBLE
    } else {
        textView.visibility = View.GONE
    }
}