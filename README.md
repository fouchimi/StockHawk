# StockHawk
The app currently finds stock prices from the internet and allows users to add stocks to track

<h2>Installation</h2>
On Linux and Mac OS, you can open the project on Android Studio and run <b>./gradlew assembleDebug</b> and <b>adb -d install 
app/{path-to-your-apk}</b> which is often app/build/outputs/apk/{apk_file}.

On Windows, you can run this command instead <b>gradle.bat assembleDebug</b> and <b>adb -d install app/{path-to-your-apk}</b>

Alternatively you can simply click on the run button of the IDE and those steps will be ran automatically and load the application 
on your mobile or virtual device

<h2>Library</h2>
I have used <a href="https://github.com/PhilJay/MPAndroidChart/wiki/Getting-Started">MPAndroidChart</a> to draw the graph.
