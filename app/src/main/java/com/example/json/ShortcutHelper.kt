package com.example.json

import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.content.pm.ShortcutInfo
import android.content.pm.ShortcutManager
import android.graphics.drawable.Icon
import android.os.Build
import android.widget.Toast
import androidx.annotation.RequiresApi

@RequiresApi(Build.VERSION_CODES.N_MR1)
object ShortcutHelper {

    fun addSimpleShortcut(context: Context, zodiacSign: String, horoscopeText: String) {
        val intent = Intent(context, MainActivity::class.java).apply {
            action = Intent.ACTION_VIEW  // Установлюємо дію
            putExtra("zodiacSign", zodiacSign)
            putExtra("horoscopeText", horoscopeText)
        }

        val shortcut = ShortcutInfo.Builder(context, zodiacSign)
            .setShortLabel(zodiacSign)
            .setLongLabel("Horoscope for $zodiacSign")
            .setIcon(Icon.createWithResource(context, R.drawable.ic_launcher_foreground))
            .setIntent(intent)  // Використовуємо коректно налаштований Intent
            .build()

        val shortcutManager = context.getSystemService(ShortcutManager::class.java)
        shortcutManager?.addDynamicShortcuts(listOf(shortcut))
    }
}

@RequiresApi(Build.VERSION_CODES.O)
object Pinned {

    fun addPinnedShortcut(context: Context, zodiacSign: String, horoscopeText: String) {
        val shortcutManager = context.getSystemService(ShortcutManager::class.java)

        // Перевіряємо, чи підтримуються закріплені ярлики
        if (shortcutManager?.isRequestPinShortcutSupported == true) {
            // Створюємо Intent для шорткату
            val intent = Intent(context, MainActivity::class.java).apply {
                action = Intent.ACTION_VIEW  // Установлюємо дію
                putExtra("zodiacSign", zodiacSign)
                putExtra("horoscopeText", horoscopeText)
            }

            // Створюємо ShortcutInfo
            val shortcut = ShortcutInfo.Builder(context, zodiacSign)
                .setShortLabel(zodiacSign)
                .setLongLabel("Horoscope for $zodiacSign")
                .setIcon(Icon.createWithResource(context, R.drawable.ic_launcher_foreground))
                .setIntent(intent)  // Вказуємо Intent
                .build()

            // Створюємо PendingIntent для зворотного виклику (необов’язково)
            val callbackIntent = shortcutManager.createShortcutResultIntent(shortcut)
            val pendingIntent = PendingIntent.getBroadcast(
                context,
                0,
                callbackIntent,
                PendingIntent.FLAG_IMMUTABLE
            )

            // Надсилаємо запит на закріплення шорткату
            val success = shortcutManager.requestPinShortcut(shortcut, pendingIntent.intentSender)

            // Показуємо повідомлення користувачу
            if (success) {
                Toast.makeText(context, "Pinned shortcut requested!", Toast.LENGTH_SHORT).show()
            } else {
                Toast.makeText(context, "Pinned shortcut failed.", Toast.LENGTH_SHORT).show()
            }
        } else {
            Toast.makeText(context, "Pinned shortcuts are not supported on this device.", Toast.LENGTH_SHORT).show()
        }
    }
}
