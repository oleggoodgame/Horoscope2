package com.example.json
//Це data class, яка представляє структуру даних, які ми отримаємо з API. У нашому випадку, кожен Todo об'єкт має 4 поля: completed, id, title, userId.
//
//Ця класова модель відповідає JSON-об'єкту, який приходить від сервера. Приклад JSON, що відповідає цьому класу:
data class Todo(
    val completed: Boolean,
    val id: Int,
    val title: String,
    val userId: Int
)

data class HoroscopeResponse(
    val data: HoroscopeData,
    val status: Int,
    val success: Boolean
)

data class HoroscopeData(
    val date: String,
    val horoscope_data: String
)