@file:JvmName("WatermarkBotKt")

package watermark

import org.telegram.telegrambots.meta.TelegramBotsApi
import org.telegram.telegrambots.meta.exceptions.TelegramApiException
import org.telegram.telegrambots.updatesreceivers.DefaultBotSession

fun main() {
    val token = "6853033970:AAEz1oEIepMm0ferJO0gBJAti999ckdFbGA"
    val watermarkBot = WatermarkBot(token)
    val botsApi = TelegramBotsApi(DefaultBotSession::class.java)
    try {
        botsApi.registerBot(watermarkBot)
    } catch (e: TelegramApiException) {
        e.printStackTrace()
    }
}