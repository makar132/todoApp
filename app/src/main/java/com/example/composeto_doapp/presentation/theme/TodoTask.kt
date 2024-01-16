package com.example.composeto_doapp.presentation.theme

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Checkbox
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp

@Composable
fun TodoTask(
    modifier : Modifier = Modifier,
    taskTitle : String,
    _isChecked : Boolean = false
) {
    var isChecked by remember { mutableStateOf(_isChecked) }
    Row(
        modifier
            .fillMaxWidth()
            .background(Color.Black)
            .padding(15.dp),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.Start
    ) {
        Checkbox(checked = isChecked, onCheckedChange = { isChecked = isChecked.xor(true) })
        Text(
            text = taskTitle,
            color = if (isChecked) Color.Gray else Color.White,
            textDecoration = if (isChecked) TextDecoration.LineThrough else TextDecoration.None
        )
    }
}

@Preview(name = "To-doTask")
@Composable
private fun PreviewTodoTask() {
    TodoTask(taskTitle = "Test")
}