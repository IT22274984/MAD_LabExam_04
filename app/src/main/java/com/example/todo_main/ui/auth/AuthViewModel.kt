package com.example.todo_main.ui.auth

import android.os.Parcel
import android.os.Parcelable
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.todo_main.data.repository.AuthRepository
import com.example.todo_main.util.UiState
import dagger.hilt.android.lifecycle.HiltViewModel
import dev.jord.todo.data.model.User
import dev.jord.todo.data.repository.AuthRepository
import dev.jord.todo.util.UiState
import javax.inject.Inject

@HiltViewModel
class AuthViewModel<User> @Inject constructor(
    val repository: AuthRepository
): ViewModel(), Parcelable {

    private val TAG = "AuthViewModel"
    private val _register = MutableLiveData<UiState<String>>()
    val register: LiveData<UiState<String>>
        get() = _register

    private val _login = MutableLiveData<UiState<String>>()
    val login: LiveData<UiState<String>>
        get() = _login

    private val _forgotPassword = MutableLiveData<UiState<String>>()
    val forgotPassword: LiveData<UiState<String>>
        get() = _forgotPassword

    constructor(parcel: Parcel) : this(TODO("repository")) {
    }

    fun register(email: String, password: String, user: User) {
        _register.value = UiState.Loading
        repository.registerUser(email = email, password = password, user = user) {
            _register.value = it
        }
    }

    fun login(email: String, password: String) {
        _login.value = UiState.Loading
        repository.loginUser(email, password) {
            _login.value = it
        }
    }

    fun forgotPassword(email: String) {
        _forgotPassword.value = UiState.Loading
        repository.forgotPassword(email) {
            _forgotPassword.value = it
        }
    }

    fun logout(result: () -> Unit){
        repository.logout(result)
    }

    fun getSession(result: (User?) -> Unit){
        repository.getSession(result)
    }

    override fun writeToParcel(parcel: Parcel, flags: Int) {

    }

    override fun describeContents(): Int {
        return 0
    }

    companion object CREATOR : Parcelable.Creator<AuthViewModel<Any?>> {
        override fun createFromParcel(parcel: Parcel): AuthViewModel<Any?> {
            return AuthViewModel(parcel)
        }

        override fun newArray(size: Int): Array<AuthViewModel<Any?>?> {
            return arrayOfNulls(size)
        }
    }
}

annotation class HiltViewModel
