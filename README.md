# Azure APIs Client Examples

Created from the [Bing STT Example project](https://github.com/keedio/bing-speech-to-text-example).

### Get ready to run the application

Quick-and-dirty usage example of [Cortana](https://gallery.cortanaanalytics.com/) RESTful APIs.
This application has been developed using Spring Boot.

Steps to use this app:

1. Create a Microsoft Azure Account: [https://azure.microsoft.com](https://azure.microsoft.com)
2. Generate an unique UUID (command `uuidgen` in UNIX) for this app.
3. Edit `src/main/resources/application.properties` and configure `deviceID` using the value generated at step #2 above.
4. Register applications in your account: [https://datamarket.azure.com/account/keys](https://datamarket.azure.com/account/keys), note down app id and app secret.
5. Edit `src/main/resources/application-PROFILE.properties` and configure `appID` and `appSecret` using the values generated at step #4 above.

### Run the application

To request a service, use:

	$ mvn spring-boot:run -Drun.arguments=ARG -Dspring.profiles.active=PROFILE

Available profiles:

- speech2text: convert a wav file to text. ARG: full path to the wav file to convert.
- translator: translate a text to english. ARG: the text to be translated (enclosed with double quotes).
- facedetection: analyze an image. ARG: full path to the image file. Accepts JPEG, PNG, GIF, BMP, max = 4MB.
