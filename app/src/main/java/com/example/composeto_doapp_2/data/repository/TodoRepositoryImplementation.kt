package com.example.composeto_doapp_2.data.repository

import com.example.composeto_doapp_2.data.AppDatabase
import com.example.composeto_doapp_2.data.TodoEntity
import kotlinx.coroutines.flow.Flow

class TodoRepositoryImplementation(private val database : AppDatabase) : TodoRepository {
    private val dao=database.todoDao()
    override suspend fun getAllTodosStream() : Flow<List<TodoEntity>> = dao.getAllTodos()
    override suspend fun insertTodo(todo : TodoEntity) = dao.insertTodo(todo)
    override suspend fun updateTodo(todo : TodoEntity) = dao.updateTodo(todo)
    override suspend fun deleteTodo(todo : TodoEntity) = dao.deleteTodo(todo)
}