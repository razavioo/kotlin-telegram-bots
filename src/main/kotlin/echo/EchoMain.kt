package echo

import org.telegram.telegrambots.meta.TelegramBotsApi
import org.telegram.telegrambots.meta.exceptions.TelegramApiException
import org.telegram.telegrambots.updatesreceivers.DefaultBotSession

fun main() {
    val token = "6448425327:AAH_glzGSVMt_owhQtTRkrynuobO6z8ccwU"
    val echoBot = EchoBot(token)
    val botsApi = TelegramBotsApi(DefaultBotSession::class.java)
    try {
        botsApi.registerBot(echoBot)
    } catch (e: TelegramApiException) {
        e.printStackTrace()
    }
}