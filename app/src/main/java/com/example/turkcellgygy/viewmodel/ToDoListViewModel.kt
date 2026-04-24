package com.example.turkcellgygy.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.turkcellgygy.data.TodoRepository
import com.example.turkcellgygy.model.Todo
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class ToDoListViewModel : ViewModel() {
    private val repository = TodoRepository() //repodan todoları, verileri çekicem.

    //şimdi bu todoları çekiyorum, bunlar: başarılı alabilrim,hata alabilirim, - saniyede loading olarak alabilirim.
    // basarili state
    private val _todos = MutableStateFlow<List<Todo>>(emptyList())
    val todos: StateFlow<List<Todo>> = _todos.asStateFlow()
    //todoların,gelmesini beklerkenki state
    private val _isLoading = MutableStateFlow<Boolean>(false)
    val isloading: StateFlow<Boolean> = _isLoading.asStateFlow()

//hatalı state, gelmedi todolar(dbden dolayı, api den gelemedi vs gibi çeşit çeşit sorunlar)
    private val _error = MutableStateFlow<String?>(null) // hata yoksa null olur o yüzden nullableb
    val error: StateFlow<String?> = _error.asStateFlow()

    init {//init fonksiyonu uygulama başlatıldığında otomatik çalışır.
        fetchTodos()
    }

//state akışını başlatıyoruz, sıralı
    fun fetchTodos(){
        viewModelScope.launch {
            _isLoading.value = true; // istek atmaya başlattık
            _error.value = null;

            try{
                val result= repository.getTodos() // rsult cevaplari alır
                _todos.value = result
            }catch (e: Exception){
                _error.value = e.message ?: "Bir Hata Oluştu"
            }finally {
                _isLoading.value = false;
            }
        }
    }

    fun delete(id: Int){
        viewModelScope.launch{
            try{
                repository.delete(id)
                // Hocanın istediği gibi: veriyi veritabanından sildikten sonra listeyi yeniliyoruz.
                fetchTodos()
            }catch (e: Exception){
                println(e.message)
            }
        }

    }

    fun addTodo(title: String){
        viewModelScope.launch{
            try{
                // Geçici bir id atıyoruz (Supabase tarafında auto-increment ise veritabanı kendi id'sini verebilir, ancak model zorunlu kıldığı için rastgele atıyoruz)
                val newTodo = Todo(id = (1000..99999).random(), title = title)
                repository.addToDo(newTodo)
                
                // Hocanın istediği gibi: veriyi ekledikten sonra listeyi yeniliyoruz.
                fetchTodos()
            }catch (e: Exception){
                println(e.message)
            }
        }
    }
}
