package com.example.phoneauthentication.logic.repository

import androidx.lifecycle.LiveData
import com.example.phoneauthentication.data.models.User
import com.example.phoneauthentication.logic.dao.UserDao

class UserRepository(private val userDao: UserDao) {
    val getUserDetails: LiveData<List<User>> = userDao.getUserDetails()



    suspend fun addUser(user: User) {
            userDao.addUser(user)
    }

    fun checkNumber(number: String) : Boolean {
        return userDao.checkNumber(number)
    }

    fun getNumberDetails(number: String): List<User> {
        return userDao.getNumberDetails(number)
    }

}