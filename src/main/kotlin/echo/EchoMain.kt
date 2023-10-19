package echo

import org.telegram.telegrambots.meta.TelegramBotsApi
import org.telegram.telegrambots.meta.exceptions.TelegramApiException
import org.telegram.telegrambots.updatesreceivers.DefaultBotSession

fun main() {
    val token = System.getenv("BOT_TOKEN") ?: "BOT_TOKEN"
    val echoBot = EchoBot(token)
    val botsApi = TelegramBotsApi(DefaultBotSession::class.java)
    try {
        botsApi.registerBot(echoBot)
    } catch (e: TelegramApiException) {
        e.printStackTrace()
    }
}