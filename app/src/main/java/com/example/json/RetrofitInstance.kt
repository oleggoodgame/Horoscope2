package com.example.json

import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object RetrofitInstance {

        val api: HoroscopeApi by lazy {
            Retrofit.Builder()
                .baseUrl("https://horoscope-app-api.vercel.app/api/v1/")
                .addConverterFactory(GsonConverterFactory.create())
                .build()
                .create(HoroscopeApi::class.java)
        }
    }
    //
    //Singleton-об'єкт. Гарантує, що ми маємо лише один екземпляр Retrofit у всьому додатку.
    //by lazy:
    //
    //Лінива ініціалізація (lazy initialization). Це означає, що об'єкт api буде створено тільки тоді, коли він вперше буде використаний. Це зберігає ресурси і підвищує продуктивність.
    //Retrofit.Builder():
    //
    //Створює об'єкт Retrofit, який буде відповідати за відправку HTTP-запитів.
    //.baseUrl("https://jsonplaceholder.typicode.com"):
    //
    //Встановлює базовий URL для всіх запитів. Усі запити, визначені в інтерфейсі TodoApi, будуть додаватися до цього URL.
    //.addConverterFactory(GsonConverterFactory.create()):
    //
    //GsonConverterFactory конвертує JSON-відповіді у Kotlin об'єкти (використовуючи Gson).
    //.create(TodoApi::class.java):
    //
    //Створює реалізацію інтерфейсу TodoApi для Retrofit, щоб ми могли викликати методи (getTodos).
