package com.example.json

import android.app.Dialog
import android.content.Context
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.net.ConnectivityManager
import android.net.NetworkCapabilities
import android.os.Build
import android.os.Bundle
import android.util.Log
import android.view.View
import android.view.Window
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AppCompatActivity
import com.example.json.databinding.ActivityMainBinding
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding  // Ініціалізац   ія ViewBinding
    private var selectedZodiacSign: String? = null

//    override fun onCreate(savedInstanceState: Bundle?) {
//        super.onCreate(savedInstanceState)
//        binding = ActivityMainBinding.inflate(layoutInflater)
//        setContentView(binding.root)  // Замінює стандартний setContentView()
//
////        val call = RetrofitInstance.api.getTodos()
//        //RetrofitInstance.api.getTodos() викликає метод getTodos() з інтерфейсу TodoApi.
//        //call є об'єктом типу Call<List<Todo>>, що представляє відкладений HTTP-запит. Запит ще не виконується на цьому етапі.
//
//        //enqueue — це метод, який виконує запит синхронно.
//        //Він приймає об'єкт Callback, що має дві основні функції:
//        //onResponse — викликається, коли сервер відповідає.
//        //onFailure — викликається у випадку помилки (наприклад, проблеми з мережею)

//        lifecycleScope.launchWhenCreated {
//            try {
//                val response = RetrofitInstance.api.getTodos_corotin()
//                if (response.isSuccessful) {
//                    val todos = response.body()
//                    //response.body() повертає тіло відповіді (body) від сервера у вигляді об'єкта, який ти вказав у сигнатурі API-запиту. У твоєму випадку:
//                    //kotlin
//                    //Копіювати код
//                    //suspend fun getTodos_corotin(): Response<List<Todo>>
//                    withContext(Dispatchers.Main) {
//                        todos?.let {
//                            for (index in it.indices step 3) {  // Проходимо через кожен третій елемент
//                                val todo = it[index]
//                                Log.d("MainActivity", "ID: ${todo.id}, Title: ${todo.title}")
//                            }
//                        }
//                    }
//                }
//            } catch (e: Exception) {
//                withContext(Dispatchers.Main) {
//                    Log.e("MainActivity", "Error: ${e.message}")
//                }
//            }
//        }

    @RequiresApi(Build.VERSION_CODES.O)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)

        if (isInternetAvailable()) {
            setContentView(binding.root)
        } else {
            setContentView(R.layout.activity_internet)
        }
        val seasons = mutableListOf("Winter", "Spring", "Summer", "Autumn")
        val zodiacSigns = mutableListOf(
            mutableListOf("Capricorn", "Aquarius", "Pisces"), // Winter
            mutableListOf("Aries", "Taurus", "Gemini"),       // Spring
            mutableListOf("Cancer", "Leo", "Virgo"),          // Summer
            mutableListOf("Libra", "Scorpio", "Sagittarius")  // Autumn
        )

        val adapter = ExpanderListAdapter(this, seasons, zodiacSigns)
        binding.lists.setAdapter(adapter)
        val zodiacSign = intent.getStringExtra("zodiacSign")
        val horoscopeText = intent.getStringExtra("horoscopeText")
//        Pinned.addPinnedShortcut(this, "Aries", "Today is a good day!")
        if (zodiacSign != null && horoscopeText != null) {
            selectedZodiacSign = zodiacSign
            RequestClick()
        }
    }

    fun onZodiacSelected(zodiacSign: String) {
        selectedZodiacSign = zodiacSign
    }

    fun RequestClick(view: View? = null) {
        val dialog = Dialog(this)
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE)
        dialog.setCancelable(false)
        dialog.setContentView(R.layout.requst_layout)
        dialog.window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))

        val textView = dialog.findViewById<TextView>(R.id.textView)
        if (selectedZodiacSign != null) {
            val call = RetrofitInstance.api.getDailyHoroscope("$selectedZodiacSign")
            call.enqueue(object : Callback<HoroscopeResponse> {
                override fun onResponse(call: Call<HoroscopeResponse>, response: Response<HoroscopeResponse>) {
                    if (response.isSuccessful) {
                        val horoscopeResponse = response.body()
                        val horoscopeText = horoscopeResponse?.data?.horoscope_data ?: "No horoscope available"
                        textView.text = horoscopeText

                        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N_MR1) {
                            ShortcutHelper.addSimpleShortcut(this@MainActivity, selectedZodiacSign.toString(), horoscopeText)
                        }
                    } else {
                        Log.e("MainActivity", "Response error: ${response.code()}")
                    }
                }

                override fun onFailure(call: Call<HoroscopeResponse>, t: Throwable) {
                    Log.e("MainActivity", "Error: ${t.message}")
                }
            })
        } else {
            textView.text = "No zodiac sign selected."
        }

        // Обробка натискання кнопки
        val button = dialog.findViewById<Button>(R.id.button)
        button.setOnClickListener {
            Toast.makeText(this, "Good luck!", Toast.LENGTH_SHORT).show()
            dialog.dismiss()
        }

        dialog.show() // Показати діалог
    }

    @RequiresApi(Build.VERSION_CODES.M)
    private fun isInternetAvailable(): Boolean {
        val connectivityManager = getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
        val network = connectivityManager.activeNetwork
        val capabilities = connectivityManager.getNetworkCapabilities(network)
        return capabilities != null && capabilities.hasCapability(NetworkCapabilities.NET_CAPABILITY_INTERNET)
    }

}