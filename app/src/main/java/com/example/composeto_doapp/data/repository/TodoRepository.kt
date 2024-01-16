package com.example.composeto_doapp.data.repository

import com.example.composeto_doapp.data.local.entities.TodoEntity
import kotlinx.coroutines.flow.Flow

interface TodoRepository {
    suspend fun getAllTodosStream() : Flow<List<TodoEntity>>

    suspend fun insertTodo(todo : TodoEntity)

    suspend fun updateTodo(todo : TodoEntity)

    suspend fun deleteTodo(todo : TodoEntity)
}