package name

import KtorClient.client
import io.ktor.client.call.*
import io.ktor.client.request.*
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import org.telegram.telegrambots.bots.TelegramLongPollingBot
import org.telegram.telegrambots.meta.api.methods.send.SendMessage
import org.telegram.telegrambots.meta.api.objects.Update

class NameBot(token: String) : TelegramLongPollingBot(token) {
    override fun getBotUsername(): String {
        return "nameqombot"
    }

    override fun onUpdateReceived(update: Update) {
        if (update.hasMessage() && update.message.hasText()) {
            val messageText = update.message.text
            val chatId = update.message.chatId
            if (messageText.startsWith("/name")) {
                val name = messageText.split(" ").getOrNull(1)
                if (name != null) {
                    GlobalScope.launch {
                        val nameInfo = getNameInfo(name)
                        sendTextMessage(chatId, nameInfo)
                    }
                } else {
                    sendTextMessage(chatId, "Please provide a name.")
                }
            }
        }
    }

    private suspend fun getNameInfo(name: String): String {
        val genderizeResponse = client.get("https://api.genderize.io?name=$name").body<GenderizeResponse>()
        val agifyResponse = client.get("https://api.agify.io?name=$name").body<AgifyResponse>()
        val nationalizeResponse = client.get("https://api.nationalize.io?name=$name").body<NationalizeResponse>()

        val mostProbableCountry = nationalizeResponse.country.maxByOrNull { it.probability }?.country_id ?: "Unknown"

        return "Information about $name: \nGender: ${genderizeResponse.gender}, Age: ${agifyResponse.age}, Nationality: $mostProbableCountry"
    }

    private fun sendTextMessage(chatId: Long, text: String) {
        val sendMessage = SendMessage()
        sendMessage.chatId = chatId.toString()
        sendMessage.text = text
        execute(sendMessage)
    }
}
