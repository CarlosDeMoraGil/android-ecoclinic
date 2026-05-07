package com.carlosdmg.ecoclinic.app.data

import com.google.firebase.auth.AuthResult
import com.google.firebase.auth.FirebaseAuth
import org.koin.core.annotation.Single

@Single
class FirebaseAuthentication {

    private val auth = FirebaseAuth.getInstance()

    fun createUser(
        email: String,
        password: String,
        onResult: (Result<AuthResult>) -> Unit
    ) {
        auth.createUserWithEmailAndPassword(email, password)
            .addOnCompleteListener { task ->

                if (task.isSuccessful) {
                    onResult(Result.success(task.result))
                } else {
                    onResult(Result.failure(task.exception ?: Exception("Unknown error")))
                }
            }
    }
}