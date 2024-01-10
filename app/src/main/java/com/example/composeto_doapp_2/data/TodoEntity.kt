package com.example.composeto_doapp_2.data

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import java.sql.Date
import java.text.SimpleDateFormat
import java.util.Locale

@Entity("Todos")
data class TodoEntity(
    @PrimaryKey(autoGenerate =true) val todoId:Int = 0,
    @ColumnInfo("title") val title: String,
    @ColumnInfo("description") val description: String,
    @ColumnInfo("state") val isCompleted: Boolean,

)
val TodoEntity.addDate:String
    get() =
        SimpleDateFormat("yyyy/MM/dd hh:mm", Locale.getDefault()).format(Date(System.currentTimeMillis()))
