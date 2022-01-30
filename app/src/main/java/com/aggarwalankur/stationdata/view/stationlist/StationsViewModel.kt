package com.aggarwalankur.stationdata.view.stationlist

import androidx.lifecycle.*
import com.aggarwalankur.stationdata.data.StationRepository
import com.aggarwalankur.stationdata.network.Departure
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import timber.log.Timber
import javax.inject.Inject

@HiltViewModel
class StationsViewModel @Inject constructor(
    private val stationRepository: StationRepository
) : ViewModel(){
    private val _departures = MutableLiveData<List<Departure>>()
    val departures: LiveData<List<Departure>>
        get() = _departures

    private val _status = MutableLiveData<LoadStatus>()
    val status: LiveData<LoadStatus>
        get() = _status

    val empty: LiveData<Boolean> = Transformations.map(_departures) {
        Timber.d("List it.isEmpty = ${it.isEmpty()}")
        it.isEmpty()
    }

    init {
        _status.value = LoadStatus.LOADING()

        getStationDepartures()
    }

    private fun getStationDepartures() {
        viewModelScope.launch {
            _status.value = LoadStatus.LOADING()
            try {
                _departures.value = stationRepository.getStationDataFromApi()
                Timber.d("List size = ${departures.value?.size}")
                _status.value = LoadStatus.SUCCESS()
            } catch (e: Exception) {
                _status.value = LoadStatus.ERROR()
                _departures.value = ArrayList()
            }
        }
    }

    fun refresh() {
        getStationDepartures()
    }
}

sealed class LoadStatus {
    class SUCCESS : LoadStatus()
    class LOADING : LoadStatus()
    class ERROR : LoadStatus()
}