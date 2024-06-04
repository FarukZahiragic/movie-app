package com.example.cineste

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query

/*
    MovieDAO interface for retrieving and entry of Movie data
    Author: Faruk Zahiragic
    Created on 27.05.2024
 */

@Dao
interface MovieDao {
    @Query("SELECT * FROM movie")
    suspend fun getAll(): List<Movie>
    @Insert
    suspend fun insertAll(vararg movies: Movie)
}