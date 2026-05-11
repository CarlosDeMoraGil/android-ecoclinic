package com.carlosdmg.ecoclinic.feature.user.data.remote

import android.util.Log
import com.carlosdmg.ecoclinic.feature.user.domain.User
import com.google.firebase.Firebase
import com.google.firebase.firestore.firestore
import kotlinx.coroutines.tasks.await
import org.koin.core.annotation.Single

@Single
class UsersFirestoreRemoteDataSource {

    val db = Firebase.firestore

    fun saveUser(user: User) {
        db.collection("patients")
            .document(user.userId)
            .set(user.toDbModel())

            .addOnSuccessListener {
                Log.d("@dev", "Usuario almacenado, con exito")
            }
            .addOnFailureListener {
                Log.d("@dev", "Error al guardar usurio")
            }
    }

    suspend fun getUserById(userId: String?): User? =
        userId?.let { userId ->
            db.collection("patients")
                .document(userId)
                .get()
                .await()
                .toObject(UserDbModel::class.java)
                ?.toModel()
        }

}