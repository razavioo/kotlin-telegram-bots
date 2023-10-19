package watermark

import downloadImage
import kotlinx.coroutines.*
import org.telegram.telegrambots.bots.TelegramLongPollingBot
import org.telegram.telegrambots.meta.api.methods.GetFile
import org.telegram.telegrambots.meta.api.methods.send.SendMessage
import org.telegram.telegrambots.meta.api.methods.send.SendPhoto
import org.telegram.telegrambots.meta.api.objects.InputFile
import org.telegram.telegrambots.meta.api.objects.Update
import org.telegram.telegrambots.meta.api.objects.replykeyboard.ReplyKeyboardMarkup
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.KeyboardRow
import java.awt.Color
import java.awt.Font
import java.io.ByteArrayInputStream
import java.io.ByteArrayOutputStream
import java.io.InputStream
import javax.imageio.ImageIO


class WatermarkBot(token: String) : TelegramLongPollingBot(token), CoroutineScope {

    override val coroutineContext = Dispatchers.Default + SupervisorJob()

    private val dataStorage: HashMap<Long, SavedData> = HashMap()

    override fun getBotUsername(): String = "watermarkqombot"

    override fun onUpdateReceived(update: Update) {
        val message = update.message ?: return
        val chatId = message.chatId

        when {
            message.isCommand -> {
                if (message.text == "/start") {
                    promptForImage(chatId)
                }
            }

            message.hasPhoto() -> {
                launch {
                    val photo = message.photo.last()
                    val filePath = getFileUrl(photo.fileId) ?: return@launch
                    val photoStream = ByteArrayInputStream(downloadImage(filePath))
                    val byteStream = ByteArrayOutputStream()
                    photoStream.copyTo(byteStream)
                    saveImage(chatId, byteStream.toByteArray())
                    promptForWatermarkText(chatId)
                }
            }

            message.hasText() -> {
                val savedData = dataStorage[chatId]
                if (savedData?.imageBytes != null && savedData.text == null) {
                    saveWatermarkText(chatId, message.text)
                    promptForWatermarkColor(chatId)
                } else if (savedData?.text != null && savedData.colorName == null) {
                    saveWatermarkColor(chatId, message.text)
                    processImage(chatId, savedData.imageBytes, savedData.text!!, savedData.colorName!!)
                }
            }

            else -> {
                promptForImage(chatId)
            }
        }
    }

    private fun promptForImage(chatId: Long) {
        execute(SendMessage(chatId.toString(), "Please send the image."))
    }

    private fun promptForWatermarkText(chatId: Long) {
        execute(SendMessage(chatId.toString(), "Please enter the watermark text."))
    }

    private fun promptForWatermarkColor(chatId: Long) {
        val message = SendMessage()
        message.chatId = chatId.toString()
        message.text = "Please choose the color of the watermark text."
        val keyboardMarkup = ReplyKeyboardMarkup()
        val keyboard = mutableListOf<KeyboardRow>()
        val row = KeyboardRow()
        row.add("Blue")
        row.add("Red")
        row.add("Black")
        row.add("White")
        keyboard.add(row)
        keyboardMarkup.keyboard = keyboard
        message.replyMarkup = keyboardMarkup
        execute(message)
    }

    private fun processImage(chatId: Long, imageBytes: ByteArray, text: String, colorName: String) {
        val color = when (colorName) {
            "Blue" -> Color.BLUE
            "Red" -> Color.RED
            "Black" -> Color.BLACK
            "White" -> Color.WHITE
            else -> Color.BLACK
        }

        val inputStream = ByteArrayInputStream(imageBytes)
        val image = ImageIO.read(inputStream)
        val graphics = image.graphics
        graphics.color = color
        graphics.font = Font("Arial", Font.BOLD, 40)
        graphics.drawString(text, 10, image.height - 10)
        graphics.dispose()

        val outputStream = ByteArrayOutputStream()
        ImageIO.write(image, "png", outputStream)

        val watermarkedPhotoStream: InputStream = ByteArrayInputStream(outputStream.toByteArray())
        val inputFile = InputFile(watermarkedPhotoStream, "watermarked_image.png")

        val sendPhoto = SendPhoto()
        sendPhoto.chatId = chatId.toString()
        sendPhoto.photo = inputFile
        execute(sendPhoto)
    }

    private fun saveImage(chatId: Long, imageBytes: ByteArray) {
        val data = dataStorage[chatId]
        if (data == null) {
            dataStorage[chatId] = SavedData(imageBytes, null, null)
        } else {
            data.imageBytes = imageBytes
        }
    }

    private fun saveWatermarkText(chatId: Long, text: String) {
        val data = dataStorage[chatId]
        if (data != null) {
            data.text = text
        } else {
            dataStorage[chatId] = SavedData(byteArrayOf(), text, null)
        }
    }

    private fun saveWatermarkColor(chatId: Long, colorName: String) {
        val data = dataStorage[chatId]
        if (data == null) {
            dataStorage[chatId] = SavedData(byteArrayOf(), null, colorName)
        } else {
            data.colorName = colorName
        }
    }

    private suspend fun getFileUrl(fileId: String): String? {
        val getFile = GetFile()
        getFile.fileId = fileId
        return try {
            val file = withContext(Dispatchers.IO) { execute(getFile) }
            val filePath = file.filePath
            "https://api.telegram.org/file/bot${botToken}/$filePath"
        } catch (e: Exception) {
            e.printStackTrace()
            null
        }
    }
}
