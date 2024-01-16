package com.example.composeto_doapp.data.repository

import com.example.composeto_doapp.data.local.dao.TodoDao
import com.example.composeto_doapp.data.local.entities.TodoEntity
import kotlinx.coroutines.flow.Flow

class TodoRepositoryImplementation( private val dao : TodoDao) :
    TodoRepository {
    override suspend fun getAllTodosStream() : Flow<List<TodoEntity>> = dao.getAllTodos()
    override suspend fun insertTodo(todo : TodoEntity) = dao.insertTodo(todo)
    override suspend fun updateTodo(todo : TodoEntity) = dao.updateTodo(todo)
    override suspend fun deleteTodo(todo : TodoEntity) = dao.deleteTodo(todo)
}