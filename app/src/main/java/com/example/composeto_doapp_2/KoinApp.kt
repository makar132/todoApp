package com.example.composeto_doapp_2

import android.app.Application
import androidx.room.Room
import com.example.composeto_doapp_2.data.AppDatabase
import com.example.composeto_doapp_2.data.repository.TodoRepository
import com.example.composeto_doapp_2.data.repository.TodoRepositoryImplementation
import com.example.composeto_doapp_2.vm.TodoViewModel
import org.koin.android.ext.android.get
import org.koin.androidx.compose.get
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.core.context.startKoin
import org.koin.core.scope.get
import org.koin.dsl.bind
import org.koin.dsl.module

class KoinApp : Application() {
    override fun onCreate() {
        super.onCreate()
        startKoin {
            modules(module {
                single {
                    Room
                        .databaseBuilder(this@KoinApp, AppDatabase::class.java, "db")
                        .build()
                }
                single {
                    TodoRepositoryImplementation(database = get())
                } bind TodoRepository::class
             //viewModel { TodoViewModel(get()) }
            })
        }
    }
}