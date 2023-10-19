# Telegram Bots Repository

This repository contains multiple Telegram bots:

1. **EchoBot** - A simple bot that echoes back any message you send to it.
2. **NameBot** - A bot that provides information about a given name, including its likely gender, average age, and nationality.
3. **WatermarkBot** - A Telegram bot designed to add watermarks to images. Users can specify the text and choose from a selection of colors.

## Getting Started

### Prerequisites

- Java JDK (preferably JDK 8 or above)
- Maven or Gradle for dependency management
- Set up your `BOT_TOKEN` and `BOT_USERNAME` as environment variables or replace them in the code directly.

### Running the bots

To run any of the bots, navigate to their respective main functions and execute the application.

## Features

### EchoBot

- Echoes back any message sent to it.

### NameBot

- Responds with gender, average age, and most probable nationality for a provided name.
- Uses external APIs like `genderize.io`, `agify.io`, and `nationalize.io` for fetching name data.

### WatermarkBot

- **User Interaction**: The bot interacts with the user through a series of prompts.
- **Custom Watermark**: Users can specify their own text for the watermark.
- **Color Selection**: Users can choose from a predefined set of colors for the watermark text.
- **Image Processing**: The bot processes the image and sends back the watermarked image.

## Libraries Used

- **TelegramBots** - Java library to create bots using the Telegram Bots API.
- **Ktor** - Kotlin asynchronous framework for creating microservices, web applications, and more.
- **Kotlinx Serialization** - Kotlin library for JSON serialization.
- Java's AWT and ImageIO: Used for image processing in the WatermarkBot.

## Special Thanks

A special shoutout and thanks to the [TelegramBots library by rubenlagus](https://github.com/rubenlagus/TelegramBots). This project wouldn't have been possible without it!

## Contributing

If you'd like to contribute, please fork the repository and use a feature branch. Pull requests are warmly welcome.

## License

This project is free software; you can redistribute it and/or modify it under the terms of the license provided.

---

Remember, always keep your tokens and sensitive information secret. Never expose them in your code or version control.
