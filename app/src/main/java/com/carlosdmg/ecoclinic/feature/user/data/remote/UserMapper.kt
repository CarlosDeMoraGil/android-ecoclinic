package com.carlosdmg.ecoclinic.feature.user.data.remote

import com.carlosdmg.ecoclinic.feature.user.domain.User

fun User.toDbModel(): UserDbModel{
    return UserDbModel(this.userId, this.name, this.surname, this.address, this.email, this.age, this.gender,this.phoneNumber)
}

fun UserDbModel.toModel(): User{
    return User(this.userId, this.name, this.surname, this.address, this.email, this.age, this.gender,this.phoneNumber)
}