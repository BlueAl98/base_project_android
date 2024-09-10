package com.example.baseproyect.presentation.login


import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.baseproyect.domain.exeptions.ResultState
import com.example.baseproyect.domain.model.Login
import com.example.baseproyect.domain.usecase.LoginUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject


@HiltViewModel
class LoginViewModel @Inject constructor (
  private val  useCase: LoginUseCase
) : ViewModel() {

    private val _login = MutableStateFlow<ResultState<Login>>(ResultState.Start)
    val login: MutableStateFlow<ResultState<Login>> = _login

    private val _token = MutableStateFlow("")
    val token: StateFlow<String> get() = _token


    fun login(username:String, password:String) {
        viewModelScope.launch {
            useCase.login(username, password).collect{
                _login.value = it
            }
        }
    }

    fun resetState() {
        _login.value = ResultState.Start
    }

    fun getToken() {
        viewModelScope.launch {
            _token.value = useCase.getToken() ?: ""
        }
    }

    fun saveToken(token:String) {
        viewModelScope.launch {
            useCase.saveToken(token)
        }
    }

    fun deleteToken() {
        viewModelScope.launch {
          useCase.deleteToken()
        }
    }


}