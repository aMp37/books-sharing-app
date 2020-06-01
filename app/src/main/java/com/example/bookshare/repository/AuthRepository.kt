package com.example.bookshare.repository

import androidx.lifecycle.LiveData

interface AuthRepository<T> {
    val networkState: LiveData<NetworkState>
    suspend fun signInUser(user: T): Boolean
    suspend fun signUpUser(user: T): Boolean
    suspend fun updateCurrentUser(oldPassword: String, user: T): Boolean
    fun signOutUser()
    fun getCurrentUser(): T?

    //-1 login error
    //-2 signup error
    sealed class NetworkState{
        data class Loading(val operation: Operation): NetworkState()
        data class Success(val operation: Operation): NetworkState()
        data class Error(val operation: Operation, val errorCode: Int): NetworkState()
    }

    enum class Operation{
        LOGIN,
        SIGNUP,
        UPDATE
    }
}