package com.example.composeto_doapp_2.vm


import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.composeto_doapp_2.data.TodoEntity
import com.example.composeto_doapp_2.data.repository.TodoRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import org.koin.core.component.KoinComponent
import org.koin.core.component.inject

class TodoViewModel : ViewModel(), KoinComponent {
    // MutableStateList to hold the list of items

    private val repo : TodoRepository by inject()

    private var _loadingScreen = MutableStateFlow(false)
    val loadingScreen : StateFlow<Boolean> = _loadingScreen

    private val _todoList : MutableStateFlow<List<TodoEntity>> = MutableStateFlow(emptyList())
    val todoList = _todoList.asStateFlow()


    // Implement methods to add, remove, and update todos
    init {
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
                delay(1000)
                withContext(Dispatchers.Default) {
                    repo.getAllTodosStream().onEach { data ->
                        _todoList.value = data
                    }.launchIn(viewModelScope)
                }

            } finally {
                stopLoading()
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