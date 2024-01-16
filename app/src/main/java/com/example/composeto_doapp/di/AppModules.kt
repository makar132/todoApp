package com.example.composeto_doapp.di

import com.example.composeto_doapp.data.local.database.AppDatabase
import com.example.composeto_doapp.data.repository.TodoRepository
import com.example.composeto_doapp.data.repository.TodoRepositoryImplementation
import com.example.composeto_doapp.presentation.viewmodels.TodoViewModel
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.bind
import org.koin.dsl.module

val appModule =
    module {
        single { AppDatabase.getDatabase(get()) }
        single { get<AppDatabase>().todoDao() }

        single { TodoRepositoryImplementation(dao = get()) } bind TodoRepository::class
        viewModel { TodoViewModel() }
    }
