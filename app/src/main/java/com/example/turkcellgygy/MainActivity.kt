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
import androidx.compose.material.icons.filled.Close
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
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel

import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.turkcellgygy.model.Todo
import com.example.turkcellgygy.viewmodel.ToDoListViewModel
import io.github.jan.supabase.createSupabaseClient
import io.github.jan.supabase.postgrest.Postgrest


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
            else -> {
                AddToDo(onAdd = { title -> todoViewModel.addTodo(title) })
                ToDoList(todos, onDelete = { id -> todoViewModel.delete(id) })
            }
        }
    }
}


@Composable
fun ToDoList(toDoList: List<Todo>, onDelete: (Int) -> Unit){
    LazyColumn(modifier =Modifier.fillMaxSize()){
        // Başlık kısmı tekil bir eleman olduğu için "item" içerisine almazsam direk row dersem hata alır lazycolumndan dolayı
        item {
            Row(modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically){
                Text("TODO LISTESI", modifier = Modifier.padding(16.dp))
            }
        }

        itemsIndexed(toDoList){ index,todo ->
            Row(modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically) {
                Text(todo.id.toString())
                Text(todo.title)
                IconButton(onClick = {
                    onDelete(todo.id)
                }) {
                    Icon(imageVector = Icons.Default.Close, contentDescription = "Sil")
                }
            }
        }
    }
}

@Composable
fun AddToDo(onAdd: (String) -> Unit) {
    var text = remember { mutableStateOf("") } // Başlangıç değerini boş yaptık

    Column(modifier = Modifier.fillMaxWidth().padding(16.dp)){
        TextField(
            value = text.value, 
            onValueChange = { newValue -> text.value = newValue},
            label = { Text("Yeni Todo Ekle") },
            modifier = Modifier.fillMaxWidth()
        )
        Button(
            onClick = {
                if (text.value.isNotBlank()) {
                    onAdd(text.value)
                    text.value = "" // Ekledikten sonra inputu temizle
                }
            },
            modifier = Modifier.padding(top = 8.dp).align(Alignment.End)
        )
        {
            Text("Ekle")
        }
    }
}
