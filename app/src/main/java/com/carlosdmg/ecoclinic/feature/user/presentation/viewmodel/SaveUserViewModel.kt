package com.carlosdmg.ecoclinic.feature.user.presentation.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.carlosdmg.ecoclinic.feature.user.domain.SaveUserUseCase
import com.carlosdmg.ecoclinic.feature.user.domain.User
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import org.koin.android.annotation.KoinViewModel

@KoinViewModel
class SaveUserViewModel(private val saveUserUseCase: SaveUserUseCase) : ViewModel() {

    private val _uiState = MutableLiveData<UiState>()
    val uiState: LiveData<UiState> get() = _uiState

    fun saveUser(user: User) {
        _uiState.value = UiState(isLoading = true)
        viewModelScope.launch(Dispatchers.IO) {
            saveUserUseCase.invoke(user)
        }
    }

    data class UiState(
        val isLoading: Boolean = false,
        val user: User? = null
    )
}