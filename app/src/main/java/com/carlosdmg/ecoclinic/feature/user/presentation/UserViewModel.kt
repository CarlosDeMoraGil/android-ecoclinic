package com.carlosdmg.ecoclinic.feature.user.presentation

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.carlosdmg.ecoclinic.feature.user.domain.GetUserByIdUseCase
import com.carlosdmg.ecoclinic.feature.user.domain.User
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import org.koin.android.annotation.KoinViewModel

@KoinViewModel
class UserViewModel(private val getUserByIdUseCase: GetUserByIdUseCase) : ViewModel() {

    private val _uiState = MutableLiveData<UiState>()
    val uiState: LiveData<UiState> get() = _uiState

    fun getUserById(userId: String) {
        _uiState.value = UiState(isLoading = true)
        viewModelScope.launch(Dispatchers.IO) {
            val user = getUserByIdUseCase.invoke(userId)
            _uiState.postValue(
                UiState(isLoading = false, user)
            )
        }
    }

    data class UiState(
        val isLoading: Boolean = false,
        val user: User? = null,
    )

}