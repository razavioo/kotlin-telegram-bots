@file:JvmName("NameBotKt")

package name

import org.telegram.telegrambots.meta.TelegramBotsApi
import org.telegram.telegrambots.meta.exceptions.TelegramApiException
import org.telegram.telegrambots.updatesreceivers.DefaultBotSession

fun main() {
    val token = "6787174509:AAF0FI8aq7PznkU_nRMFceS5QgTGG7-u1z8"
    val nameBot = NameBot(token)
    val botsApi = TelegramBotsApi(DefaultBotSession::class.java)
    try {
        botsApi.registerBot(nameBot)
    } catch (e: TelegramApiException) {
        e.printStackTrace()
    }
}