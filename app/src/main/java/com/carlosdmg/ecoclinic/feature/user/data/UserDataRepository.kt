package com.carlosdmg.ecoclinic.feature.user.data

import com.carlosdmg.ecoclinic.feature.user.data.remote.UsersFirestoreRemoteDataSource
import com.carlosdmg.ecoclinic.feature.user.domain.User
import com.carlosdmg.ecoclinic.feature.user.domain.UserRepository
import org.koin.core.annotation.Single

@Single
class UserDataRepository(private val remoteDataSource: UsersFirestoreRemoteDataSource) :
    UserRepository {

    override suspend fun saveUser(user: User) {
        remoteDataSource.saveUser(user)
    }

    override suspend fun getUserById(userId: String): User? {
        return remoteDataSource.getUserById(userId)
    }
}