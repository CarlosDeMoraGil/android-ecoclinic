package com.carlosdmg.ecoclinic.feature.user.domain

interface UserRepository {

    suspend fun saveUser(user: User)
    suspend fun getUserById(userId: String): User?

}