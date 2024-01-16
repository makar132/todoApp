package com.example.composeto_doapp.data.local.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy.Companion.REPLACE
import androidx.room.Query
import androidx.room.Update
import com.example.composeto_doapp.data.local.entities.TodoEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface TodoDao {
    @Query("Select * From 'Todos' Order by state")
    fun getAllTodos() : Flow<List<TodoEntity>>

    @Insert(onConflict = REPLACE)
    suspend fun insertTodo(todo : TodoEntity)

    @Update
    suspend fun updateTodo(todo : TodoEntity)

    @Delete
    suspend fun deleteTodo(todo : TodoEntity)
}