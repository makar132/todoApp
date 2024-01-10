package com.example.composeto_doapp_2

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.animateColorAsState
import androidx.compose.animation.core.Spring
import androidx.compose.animation.core.animateFloat
import androidx.compose.animation.core.infiniteRepeatable
import androidx.compose.animation.core.keyframes
import androidx.compose.animation.core.rememberInfiniteTransition
import androidx.compose.animation.core.spring
import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.scaleIn
import androidx.compose.animation.scaleOut
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyItemScope
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AddCircle
import androidx.compose.material.icons.filled.Check
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme.colorScheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.rotate
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.Dialog
import androidx.compose.ui.window.DialogProperties
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.composeto_doapp_2.data.TodoEntity
import com.example.composeto_doapp_2.data.addDate
import com.example.composeto_doapp_2.ui.theme.ComposeTodoApp2Theme
import com.example.composeto_doapp_2.vm.TodoViewModel

class MainActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState : Bundle?) {
        super.onCreate(savedInstanceState)



        setContent {
            ComposeTodoApp2Theme {
                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize(), color = colorScheme.background
                ) {

                    ToDoApp()
                }
            }
        }
    }
}


@Composable
fun ToDoApp(todoViewModel : TodoViewModel = viewModel()) {
    // Compose code to arrange TaskList and TaskInput

    val tasks by todoViewModel.todoList.collectAsState()
    val showLoader by todoViewModel.loadingScreen.collectAsState()
    val (dialogopen, setDialogOpen) = remember {
        mutableStateOf(false)
    }

    val (title, setTitle) = remember { mutableStateOf("") }
    val (description, setDescription) = remember { mutableStateOf("") }
    val (updateTodoId, setUpdateTodoId) = remember {
        mutableIntStateOf(0)
    }
    val (updateTodoState, setUpdateTodoState) = remember {

        mutableStateOf(false)
    }



    if (dialogopen) {
        Dialog(onDismissRequest = {
            setUpdateTodoId(0)
            setUpdateTodoState(false)
            setTitle("")
            setDescription("")
            setDialogOpen(false)
        }) {
            // Compose code for adding a new task


            val (emptyTitleError, setEmptyTitleError) = remember { mutableStateOf(false) }

            val (emptyDescriptionError, setEmptyDescriptionError) = remember { mutableStateOf(false) }

            Column(
                verticalArrangement = Arrangement.Center,
                horizontalAlignment = Alignment.Start,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(15.dp)
                    .clip(RoundedCornerShape(8.dp))
                    .background(colorScheme.primary)
                    .padding(8.dp)
            ) {

                OutlinedTextField(
                    modifier = Modifier.fillMaxWidth(),
                    value = title,
                    onValueChange = {
                        if (it.isEmpty()) {
                            setEmptyTitleError(true)
                        } else {
                            setEmptyTitleError(false)
                        }
                        setTitle(it)

                    },
                    label = { Text(text = "Title") },
                    colors = OutlinedTextFieldDefaults.colors(
                        focusedBorderColor = Color.White,
                        focusedLabelColor = Color.White,
                        focusedTextColor = Color.White
                    ),
                    isError = emptyTitleError
                )
                Spacer(modifier = Modifier.padding(6.dp))
                OutlinedTextField(
                    modifier = Modifier.fillMaxWidth(),
                    value = description,
                    onValueChange = {
                        if (it.isEmpty()) {
                            setEmptyDescriptionError(true)
                        } else {
                            setEmptyDescriptionError(false)
                        }
                        setDescription(it)
                    },
                    label = { Text("Description") },
                    colors = OutlinedTextFieldDefaults.colors(
                        focusedBorderColor = Color.White,
                        focusedLabelColor = Color.White,
                        focusedTextColor = Color.White
                    ),
                    isError = emptyDescriptionError
                )

                Spacer(modifier = Modifier.padding(12.dp))

                Column(
                    verticalArrangement = Arrangement.Center,
                    horizontalAlignment = Alignment.CenterHorizontally,
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Button(modifier = Modifier.fillMaxWidth(),
                        shape = RoundedCornerShape(10.dp),
                        colors = ButtonDefaults.buttonColors(containerColor = colorScheme.secondary),
                        onClick = {
                            if (title.isNotEmpty() && description.isNotEmpty()) {
                                todoViewModel.addTodo(
                                    TodoEntity(
                                        todoId = updateTodoId,
                                        title = title,
                                        description = description,
                                        isCompleted = updateTodoState
                                    )
                                )
                                setUpdateTodoId(0)
                                setUpdateTodoState(false)
                                setTitle("")
                                setDescription("")
                                setDialogOpen(false)

                            } else {
                                if (title.isEmpty()) {
                                    setEmptyTitleError(true)
                                }
                                if (description.isEmpty()) {
                                    setEmptyDescriptionError(true)
                                }
                            }


                        }) {
                        Text(text = "submit", color = Color.White)
                    }
                }
            }


        }
    }
    Scaffold(containerColor = colorScheme.secondary, floatingActionButton = {
        FloatingActionButton(contentColor = Color.White,
            containerColor = colorScheme.primary,
            onClick = { setDialogOpen(true) }) {
            Icon(Icons.Default.AddCircle, contentDescription = null)
        }
    }) { paddings ->
        Box(
            modifier = Modifier
                .fillMaxSize(),
            contentAlignment = Alignment.Center
        ) {
            AnimatedVisibility(
                visible = showLoader,
                enter = scaleIn(),
                exit = fadeOut()
            ) {
                LoadingDialog()
            }
            AnimatedVisibility(
                visible = tasks.isEmpty() && !showLoader,
                enter = scaleIn() + fadeIn(),
                exit = scaleOut() + fadeOut()
            ) {
                Text(text = "No Todos Yet!", color = Color.White, fontSize = 22.sp)
            }
            AnimatedVisibility(
                visible = tasks.isNotEmpty() && !showLoader,
                enter = scaleIn() + fadeIn(),
                exit = scaleOut() + fadeOut()
            ) {
                LazyColumn(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(
                            bottom = paddings.calculateBottomPadding() + 8.dp,
                            top = 8.dp,
                            end = 8.dp,
                            start = 8.dp
                        ),
                    verticalArrangement = Arrangement.spacedBy(8.dp),
                    state = rememberLazyListState()
                ) {

                    items(tasks.sortedBy { it.isCompleted }, key = { it.todoId }) { todoEntity ->

                        TodoItem(
                            todo = todoEntity,
                            onClick = {
                                todoViewModel.updateTask(
                                    todoEntity.copy(isCompleted = !todoEntity.isCompleted)
                                )
                            }, onEdit = {
                                setTitle(todoEntity.title)
                                setDescription(todoEntity.description)
                                setUpdateTodoId(todoEntity.todoId)
                                setUpdateTodoState(todoEntity.isCompleted)
                                setDialogOpen(true)


                            },
                            onDelete = {
                                todoViewModel.removeTask(todoEntity)
                            }
                        )
                    }
                }

            }


        }
    }

}

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun LazyItemScope.TodoItem(
    todo : TodoEntity,
    onClick : () -> Unit,
    onDelete : () -> Unit,
    onEdit : () -> Unit
) {
    val color by animateColorAsState(
        targetValue = if (todo.isCompleted) Color(0xff24d65f) else Color(
            0xffff6363
        ), animationSpec = tween(250), label = ""
    )

    Box(
        modifier = Modifier
            .fillMaxWidth()
            .animateItemPlacement(
                animationSpec = spring(
                    dampingRatio = Spring.DampingRatioHighBouncy,
                    stiffness = Spring.StiffnessLow
                )
            ), contentAlignment = Alignment.BottomEnd
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .clip(RoundedCornerShape(5.dp))
                .background(color)
                .clickable {
                    onClick()
                }
                .padding(
                    horizontal = 8.dp,
                    vertical = 16.dp
                ),
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Row(
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                Box(
                    modifier = Modifier
                        .size(25.dp)
                        .clip(CircleShape)
                        .background(colorScheme.primary)
                        .padding(4.dp),
                    contentAlignment = Alignment.Center
                ) {
                    Row {
                        AnimatedVisibility(
                            visible = todo.isCompleted,
                            enter = scaleIn() + fadeIn(),
                            exit = scaleOut() + fadeOut()
                        ) {
                            Icon(Icons.Default.Check, contentDescription = null, tint = color)
                        }
                    }
                    Row {
                        AnimatedVisibility(
                            visible = !todo.isCompleted,
                            enter = scaleIn() + fadeIn(),
                            exit = scaleOut() + fadeOut()
                        ) {
                            Icon(Icons.Default.Close, contentDescription = null, tint = color)
                        }
                    }
                }
                Column {
                    Text(
                        text = todo.title,
                        fontWeight = FontWeight.Bold,
                        fontSize = 18.sp,
                        color = Color.White
                    )
                    Text(text = todo.description, fontSize = 12.sp, color = Color(0xffebebeb))
                }
            }
            Row(
                modifier = Modifier
                    .width(70.dp)
                    .height(45.dp),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Box(
                    modifier = Modifier
                        .size(30.dp)
                        .clip(CircleShape)
                        .background(colorScheme.primary)
                        .padding(4.dp),
                    contentAlignment = Alignment.Center
                ) {
                    Icon(
                        Icons.Default.Edit,
                        tint = Color.White,
                        contentDescription = null,
                        modifier = Modifier.clickable {
                            onEdit()
                        })
                }

                Box(
                    modifier = Modifier
                        .size(30.dp)
                        .clip(CircleShape)
                        .background(colorScheme.primary)
                        .padding(4.dp),
                    contentAlignment = Alignment.Center
                ) {
                    Icon(
                        Icons.Default.Delete,
                        tint = Color.White,
                        contentDescription = null,
                        modifier = Modifier.clickable {
                            onDelete()
                        })

                }

            }

        }
        Text(text = todo.addDate, fontSize = 15.sp)
    }
}

@Composable
fun Greeting(name : String, modifier : Modifier = Modifier) {
    Text(
        text = "Hello $name!", modifier = modifier
    )
}

@Composable
private fun ProgressIndicatorLoading(
    progressIndicatorSize : Dp,
    progressIndicatorColor : Color
) {
    val infiniteTransition = rememberInfiniteTransition(label = "")

    val angle by infiniteTransition.animateFloat(
        initialValue = 0f,
        targetValue = 360f,
        animationSpec = infiniteRepeatable(
            animation = keyframes {
                durationMillis = 600 // animation duration
            }
        ), label = ""
    )

    CircularProgressIndicator(
        progress = 1f,
        modifier = Modifier
            .size(progressIndicatorSize)
            .rotate(angle)
            .border(
                6.dp,
                brush = Brush.sweepGradient(
                    listOf(
                        Color.White, // add background color first
                        progressIndicatorColor.copy(alpha = 0.3f),
                        progressIndicatorColor
                    )
                ),
                shape = CircleShape
            ),
        strokeWidth = 1.dp,
        color = Color.White // Set background color
    )
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun LoadingDialog(
    cornerRadius : Dp = 16.dp,
    progressIndicatorColor : Color = colorScheme.secondary,
    progressIndicatorSize : Dp = 40.dp,

    ) {
    AlertDialog(
        onDismissRequest = { },
        properties = DialogProperties(usePlatformDefaultWidth = false),

        ) {
        Column(
            modifier = Modifier
                .padding(start = 42.dp, end = 42.dp) // margin
                .background(color = Color.White, shape = RoundedCornerShape(cornerRadius))
                .padding(top = 36.dp, bottom = 36.dp), // inner padding
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        )
        {
            ProgressIndicatorLoading(
                progressIndicatorSize = progressIndicatorSize,
                progressIndicatorColor = progressIndicatorColor
            )
            Spacer(modifier = Modifier.height(25.dp))
            Text(text = "loading ...")
        }
    }
}

@Preview(showBackground = true)
@Composable
fun GreetingPreview() {
    ComposeTodoApp2Theme {
        LoadingDialog()
    }
}