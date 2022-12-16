# OpenAI Java Project

This is an OpenAI project written in Java that sends a prompt to the OpenAI server, receives a response, translates it to a specified language, and displays it to the user. The app also saves a record of the question and answer in a text or document file on the desktop, and allows the user to configure the target language and the format of the saved file.

## Features
- Sends a prompt to the OpenAI server and receives a response
- Translates the response to a specified language
- Displays the translated response to the user
- Saves a record of the question and answer in a text or document file on the desktop
- Allows the user to configure the target language and the format of the saved file

## How to Use
1. Clone or download the repository to your local machine
2. Make sure you have a valid OpenAI API key and enter it in the config.properties file
3. Run the Main.java file
4. Type your prompt and press enter to send it to the OpenAI server
5. The app will display the translated response and save a record of the question and answer to a file on the desktop
6. To exit the app, type "EXIT" and press enter

## Configuration
- To change the target language, modify the target_language property in the config.properties file
- To change the format of the saved file, modify the file_format property in the config.properties file (options are "txt" or "doc")

## Dependencies
- OpenAI API key
- Google Translate API key
- slf4j
- javaxMail
- junit
- Apache POI


## Credits
This project was developed by Ahmed Bughra.

## Contact me
I hope this helps! Let me know if you have any questions or need further assistance.