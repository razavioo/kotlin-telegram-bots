package echo

import org.telegram.telegrambots.bots.TelegramLongPollingBot
import org.telegram.telegrambots.meta.api.methods.send.SendMessage
import org.telegram.telegrambots.meta.api.objects.Update

class EchoBot(token: String) : TelegramLongPollingBot(token) {

    override fun getBotUsername(): String = System.getenv("BOT_USERNAME") ?: "echo_thursday_bot"

    override fun onUpdateReceived(update: Update) {
        if (update.hasMessage() && update.message.hasText()) {
            val text = update.message.text
            val chatId = update.message.chatId
            sendTextMessage(chatId, text)
        }
    }

    private fun sendTextMessage(chatId: Long, text: String) {
        val sendMessage = SendMessage()
        sendMessage.chatId = chatId.toString()
        sendMessage.text = text
        execute(sendMessage)
    }
}
