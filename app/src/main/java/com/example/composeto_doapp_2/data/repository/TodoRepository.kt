package com.example.composeto_doapp_2.data.repository

import com.example.composeto_doapp_2.data.TodoEntity
import kotlinx.coroutines.flow.Flow

interface TodoRepository {
    suspend fun getAllTodosStream() : Flow<List<TodoEntity>>

    suspend fun insertTodo(todo : TodoEntity)

    suspend fun updateTodo(todo : TodoEntity)

    suspend fun deleteTodo(todo : TodoEntity)
}