package com.c23ps419.petanikita.data.local.roomdb

import androidx.lifecycle.LiveData
import androidx.room.*
import com.c23ps419.petanikita.data.remote.response.User

@Dao
interface UserDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insert(user: User)

    @Update
    fun update(user: User)

    @Delete
    fun delete(user: User)

    @Query("DELETE FROM user")
    fun onLogoutDeleteTableData()

    @Query("SELECT * from user")
    fun getUserData(): LiveData<User>
}