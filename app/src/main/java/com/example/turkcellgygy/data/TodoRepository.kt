package com.example.turkcellgygy.data

import com.example.turkcellgygy.model.Todo

//viewmodeli hepsi bir yere gelecek şekilde toplamak için repository oluşturuyorum.
//işlemleri viewmodelde değil repositoryde yaparım, retrofictle ilgili değişmeleri(refactor) hepsinde tek fonksiyonda çağırırısam yani repoda yaparsam viewmodelde  satır değiştirmicem tek buradan değiştiricem.
class TodoRepository {
    suspend fun getTodos(): List<Todo> {
        return RetrofitClient.api.getTodos()
    }
}