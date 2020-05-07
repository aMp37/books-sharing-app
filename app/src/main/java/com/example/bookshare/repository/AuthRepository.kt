package com.example.bookshare.repository

import com.google.firebase.auth.FirebaseUser

interface AuthRepository {
    suspend fun signInUser(email: String, password: String): Boolean
    suspend fun signUpUser(email: String, password: String): Boolean

    fun signOutUser()
    fun getCurrentUser(): FirebaseUser?
}