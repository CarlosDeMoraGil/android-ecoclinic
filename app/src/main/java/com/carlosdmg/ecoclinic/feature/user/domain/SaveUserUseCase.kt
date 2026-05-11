package com.carlosdmg.ecoclinic.feature.user.domain

import org.koin.core.annotation.Single

@Single
class SaveUserUseCase (private val repository: UserRepository) {

    suspend operator fun invoke(user: User){
        repository.saveUser(user)
    }

}