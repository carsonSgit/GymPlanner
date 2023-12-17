package com.example.gymplanner.accountpage.authentication

import androidx.lifecycle.ViewModel
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser

class AuthViewModel(/*private val authRepository: AuthRepository*/) : ViewModel() {
    private val authRepository = FirebaseAuth.getInstance()
    fun currentUser(): FirebaseUser? {
        return authRepository.currentUser
    }

    fun signUp(email: String, password: String, callback: (Boolean, String?) -> Unit) {
        authRepository.createUserWithEmailAndPassword(email, password)
            .addOnCompleteListener { task ->
                if (task.isSuccessful) {
                    // Sign up success
                    callback(true, null)
                } else {
                    // If sign in fails, display a message to the user.
                    callback(false, task.exception?.message)
                }
            }
    }

    fun signIn(email: String, password: String, callback: (Boolean, String?) -> Unit) {
        authRepository.signInWithEmailAndPassword(email, password)
            .addOnCompleteListener { task ->
                if (task.isSuccessful) {
                    // Sign in success
                    callback(true, null)
                } else {
                    // If sign in fails, display a message to the user.
                    callback(false, task.exception?.message)
                }
            }
    }

    fun signOut() {
        authRepository.signOut()
    }

    /*fun delete() {
        viewModelScope.launch {
            authRepository.delete()
        }
    }*/
}