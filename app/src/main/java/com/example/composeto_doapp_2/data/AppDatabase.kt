package com.example.composeto_doapp_2.data

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
@Database(entities = [TodoEntity::class], version = 1)
abstract class AppDatabase : RoomDatabase() {
    //abstract fun todoDao():TodoDao
    abstract fun todoDao():TodoDao
/*companion object{
    @Volatile
    private var Instance:AppDatabase? = null
    fun getDatabase (context : Context):AppDatabase{
        return Instance?: synchronized(this)
        {
            Room.databaseBuilder(context,AppDatabase::class.java,"db")
                .fallbackToDestructiveMigration()
                .build()
                .also { Instance = it }
        }
    }
}
 */
}
