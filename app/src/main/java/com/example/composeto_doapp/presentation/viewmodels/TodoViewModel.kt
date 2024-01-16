package com.example.composeto_doapp.presentation.viewmodels


import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.composeto_doapp.data.local.entities.TodoEntity
import com.example.composeto_doapp.data.repository.TodoRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import org.koin.core.component.KoinComponent
import org.koin.core.component.inject

class TodoViewModel : ViewModel(), KoinComponent {
    // MutableStateList to hold the list of items

    private val repo : TodoRepository by inject()

    private var _loadingScreen = MutableStateFlow(true)
    val loadingScreen : StateFlow<Boolean> = _loadingScreen

    private val _todoList : MutableStateFlow<List<TodoEntity>> = MutableStateFlow(emptyList())
    val todoList = _todoList.asStateFlow()


    fun init() {
        viewModelScope.launch {
            getAllTodos()
        }


    }

    private fun startLoading() {
        _loadingScreen.value = true
    }

    private fun stopLoading() {
        _loadingScreen.value = false
    }

    private suspend fun getAllTodos() {
        viewModelScope.launch {
            startLoading()
            try {

                delay(1500)
                withContext(Dispatchers.Default) {

                    _todoList.value = repo.getAllTodosStream().first()

                }

            } finally {
                stopLoading()
            }

            withContext(Dispatchers.Default) {
                repo.getAllTodosStream().collectLatest {
                    _todoList.value = it
                }
            }

        }


    }

    fun addTodo(newTodoEntity : TodoEntity) {
        viewModelScope.launch {

            withContext(Dispatchers.Default) {
                repo.insertTodo(todo = newTodoEntity)
            }
        }

    }


    fun removeTask(todoEntity : TodoEntity) {
        viewModelScope.launch {
            withContext(Dispatchers.Default) {
                repo.deleteTodo(todoEntity)
            }
        }
    }

    fun updateTask(todoEntity : TodoEntity) {
        viewModelScope.launch {
            withContext(Dispatchers.Default) {
                repo.updateTodo(todoEntity)
            }
        }
    }


}