package com.example.turkcellgygy.data

import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory


//todoapiservicede soyut hali var(imzası var, şimdi somutlaştırıcaz.
//obje yaptım çünkü classtan bir sürü instance yapabilir objectden ise sadece 1 tane instance yapar. Sadeec bir tane istek atacak service lazım birden fazla gerek yok o yüzden object.
object RetrofitClient {
    //lazy napar sadece bu objeye ilk ihtiyaç duyulduğunda oluşturur. Birden fazla oluşturmazbu objeden. Birden fazla kez istek oluşturamazsın.
    val api: TodoApiService by lazy {
        Retrofit.Builder()
            .baseUrl("https://jsonplaceholder.typicode.com")
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(TodoApiService::class.java)
    }
}
