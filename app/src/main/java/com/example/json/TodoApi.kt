package com.example.json


import retrofit2.Call
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Query

interface HoroscopeApi  {
    //@GET("/todos"):
    //Анотація Retrofit, що вказує, що цей метод робить GET-запит на вказаний шлях (/todos).
    @GET("/todos")
    fun getTodos(): Call<List<Todo>>
    //fun getTodos(): Call<List<Todo>>:
    //Метод getTodos викликає GET-запит і повертає об'єкт Call<List<Todo>>:
    //Call — це обгортка, яка представляє HTTP-запит. Його можна виконати асинхронно або синхронно.
    //List<Todo> — очікується список об'єктів типу Todo.
    @GET("/todos")//асинхронне
    suspend fun getTodos_corotin(): Response<List<Todo>>

    //@Headers("API-Key: your_api_key_here")
    //@POST("users/add")
    // для подачі json формат
    @GET("get-horoscope/daily")
    fun getDailyHoroscope(
        @Query("sign") sign: String,
        @Query("day") day: String = "TODAY"
    ): Call<HoroscopeResponse>
}