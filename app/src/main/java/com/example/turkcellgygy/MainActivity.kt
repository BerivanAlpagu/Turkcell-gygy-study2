package com.example.turkcellgygy

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material3.Button
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.lifecycle.viewmodel.compose.viewModel

import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.turkcellgygy.model.Todo
import com.example.turkcellgygy.viewmodel.ToDoListViewModel


// Burada ekran tanımlarını yap.
sealed class Screen(val route: String) {
    data object Register: Screen("register")
    data object Homepage: Screen("homepage")
}

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            Scaffold{
                paddingValues ->
                MyNavigatableApp(Modifier.padding(paddingValues))
            }
        }
    }
}

@Composable
fun MyNavigatableApp(modifier: Modifier) {
    val navController = rememberNavController()
    // Magic String
    Column() {
        NavHost(navController=navController, startDestination = Screen.Homepage.route){
            composable(Screen.Register.route) { RegisterScreen(modifier, navController) }
            composable(Screen.Homepage.route) { Homepage(modifier) }
        }
    }

}

@Composable
fun Homepage(modifier: Modifier) {
    //stateleri yazıcam. viewmole ihtiyacım var yazmka için

    val todoViewModel: ToDoListViewModel = viewModel()

    val todos by todoViewModel.todos.collectAsState() // collect ile stateleri topla.
    val isLoading by todoViewModel.isloading.collectAsState()
    val error by todoViewModel.error.collectAsState()

    Column(modifier = modifier) {
        when {
            isLoading -> { Text("Yükleniyor") }
            error != null -> { Text("Hata Oluştu: $error") }
            else -> { ToDoList(todos){}}
        }
    }
}

@Composable
fun ToDoList(toDoList: List<Todo>, onDelete: (Int) -> Unit){
    //var toDoList= remember {mutableStateListOf ("veri 1", "veri 2")} //BUNU BURADA TANIMLAMİCAZ STATEHOSTİN YAPICAZ

    LazyColumn(modifier =Modifier.fillMaxSize()){
        itemsIndexed(toDoList){
                index,todo ->
            Row(modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically){
                Text(todo.title)
                Text(todo.completed.toString())
                Text(todo.id.toString())
                IconButton(onClick = {onDelete(index)}) {
                    Icon(imageVector = Icons.Default.Delete, contentDescription = "Sil")
                }
            }
        }

    }
}

@Composable
fun AddToDo(onAdd: (String) -> Unit) {
    var text =remember { mutableStateOf( value = "initial")}

    Column(modifier = Modifier.fillMaxWidth()){
        TextField(
            value = text.value, //static old sürece ekran güncellenmesi
            onValueChange = { newValue -> text.value = newValue}
        )
        Button(onClick = {
            onAdd(text.value)
            text.value = " " })
        {
            Text("Tıkla")
        }

    }
}
