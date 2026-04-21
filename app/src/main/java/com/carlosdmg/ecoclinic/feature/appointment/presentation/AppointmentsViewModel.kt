package com.carlosdmg.ecoclinic.feature.appointment.presentation

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.carlosdmg.ecoclinic.feature.appointment.domain.Appointment
import com.carlosdmg.ecoclinic.feature.appointment.domain.GetAppointmentsUseCase
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import org.koin.android.annotation.KoinViewModel

@KoinViewModel
class AppointmentsViewModel(private val getAppointmentsUseCase: GetAppointmentsUseCase) :
    ViewModel() {


    private val _uiState = MutableLiveData<UiState>()
    val uiState: LiveData<UiState> get() = _uiState

    fun loadAppointments(patientId: String) {
        _uiState.value = UiState(isLoading = true)
        viewModelScope.launch(Dispatchers.IO) {
            val appointment = getAppointmentsUseCase(patientId)
            _uiState.postValue(
                UiState(
                    appointments = appointment.getOrNull(),
                    isLoading = false
                )
            )
        }
    }

    data class UiState(
        val isLoading: Boolean = false,
        val appointments: List<Appointment>? = null
    )
}

