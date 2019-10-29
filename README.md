Foursquare Venue Demo
------------------------------------------------
Demo app that retrieves nearby venues for a specific location using Foursquare API

API Usage
------------------------------------------------
The app comes bundled with API KEYS (CLIENT ID and CLIENT SECRET) but for the debug build type only.
The API keys are created against default Android signing key found under $HOME/.android/debug.keystore
file. For production keys a developer needs to register their application here: https://foursquare.com/developers/apps

The specific API used for this application is the venues search API.

Documentation on the aforementioned API can be found here: https://developer.foursquare.com/docs/api/venues/search

Application Architecture
------------------------------------------------
Model View Presenter and Clean Architecture principles were utilised as architectural framework

Installation
------------------------------------------------
Clone the project and import it to Android Studio.
During development I used latest stable version of Android Studio 3.5.1
Gradle wrapper 5.4.1

Run the application
------------------------------------------------
If you use Android Studio, after successful source code import the main application module should 
be ready to be deployed to a phone or an emulator. 

From the command line you can use:

./gradlew clean build

The command above will build the project and should produce one debug and one release apk. 
The apk can be installed from the command line as follows (assuming adb is installed):

adb install -r /path/to/apk

Note that gradle build command also runs the tests for all build variants

Run the tests
------------------------------------------------
If you use Android Studio, navigate to src/test/java folder. There you will find all relevant tests.
Right click on the package and execute the desired tests. The tests provided cover 100% our
Presenter class, which incorporates all our business logic.

From the command line you can use:

./gradlew clean test