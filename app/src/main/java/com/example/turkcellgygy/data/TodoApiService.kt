package com.example.turkcellgygy.data

import com.example.turkcellgygy.model.Todo
import retrofit2.http.GET

interface TodoApiService {
    //imza tutarlar
    @GET(value= "todos")// BU İSTEĞİ ATIYORUM APİYE
    suspend fun getTodos() : List<Todo> //: işaaretinden sonra bu fonksiyonun geri dönüş tipi List<Todo>
    //CEVABIN NE ZAMAN GELECEĞİ BELLİ DEĞİLSE, YAVAŞ GELEBİLEN BİR İSTEKSE bu isteği bekletilecek bir istekse (yani todoları göstermeden önce buradan apiden toof objesini(içinde id,title vs var) bu bekletilen bir istek olduğu için suspend olarak bekeltmem lazım. yani istek(obje) gelene kadar beklesin
    //UI da kilitlenme yavşamamak için suspend kullanıyoruz yani
    // suspend fonksiyonu olmasa obje(istek) gelene kadar rhreadi kitler, başka fonksiyon arkadan geçmez.
    // Ama suspend olunca obje için suspend olan fonksiyonu bekler diğer fonksiyonlar devame der uygulamanın diğer kısımları çalışır


}