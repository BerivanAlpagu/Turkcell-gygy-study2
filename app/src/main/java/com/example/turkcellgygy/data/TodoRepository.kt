package com.example.turkcellgygy.data

import com.example.turkcellgygy.di.SupabaseClient
import com.example.turkcellgygy.model.Todo
import io.github.jan.supabase.postgrest.postgrest

//viewmodeli hepsi bir yere gelecek şekilde toplamak için repository oluşturuyorum.
//işlemleri viewmodelde değil repositoryde yaparım, retrofictle ilgili değişmeleri(refactor) hepsinde tek fonksiyonda çağırırısam yani repoda yaparsam viewmodelde  satır değiştirmicem tek buradan değiştiricem.
class TodoRepository {
    private val db = SupabaseClient.supabaseClient.postgrest
    suspend fun getTodos(): List<Todo> {
        return db.from("todos").select().decodeList()
    }

    suspend fun addToDo(toDo: Todo) {
        db.from("todos").insert(toDo);
    }

    suspend fun delete(id: Int)
    {
        db.from("todos").delete{
            filter { eq("id",id) }
        }
    }
}