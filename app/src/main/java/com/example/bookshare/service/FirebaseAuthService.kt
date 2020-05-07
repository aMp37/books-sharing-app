package com.example.bookshare.service

import com.google.firebase.auth.AuthResult
import com.google.firebase.auth.FirebaseUser

interface FirebaseAuthService {
    suspend fun loginWithEmailAndPassword(email: String, password: String): AuthResult?
    suspend fun registerWithEmailAndPassword(email: String, password: String): AuthResult?
    fun logout()
    fun currentUser(): FirebaseUser?
}