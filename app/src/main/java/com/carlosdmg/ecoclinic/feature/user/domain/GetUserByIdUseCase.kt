package com.carlosdmg.ecoclinic.feature.user.domain

import org.koin.core.annotation.Single

@Single
class GetUserByIdUseCase (private val repository: UserRepository) {

    suspend operator fun invoke(userId: String?): User? {
        return repository.getUserById(userId)
    }

}