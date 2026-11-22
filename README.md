# deleon_appdevAY2627

Juan Paolo De Leon — App Development, A.Y. 2026–2027

## Lab Activity 0: discussion

Setting up my Android development environment for the first time was a bit challenging, since this was my first time working with Android Studio. I mostly followed the steps given in the instructions, and whenever I got confused, I asked my classmates and friends for help. There were a few warnings and messages that showed up along the way that I didn't fully understand at first, but after checking them and asking around, I was able to continue without any major problems.

One part that took a while was the first build of the project. It took longer than I expected because Android Studio still had to download and set up some files in the background. Creating the emulator was also new to me, and it took some time before it finished loading and the app appeared on the screen. I just had to be patient and wait for everything to finish.

In the end, I was able to run the project and see "Hello World, Juan Paolo De Leon" on the emulator. Even though I don't fully understand everything yet, this activity gave me a basic idea of how an Android project is set up and how Android Studio builds and runs an app. I think it will be easier for me in the next activities now that I've gone through the process once.

## Lab Activity 1: discussion

In this activity, I learned how an Android Activity goes through different lifecycle states. When I pressed the Home button, the log only showed onPause and onStop. When I opened the app again, it showed onStart and onResume. This means the app was just hidden in the background and not closed.

When I rotated the screen, something different happened. The log showed onPause, onStop, onDestroy, and then onCreate, onStart, and onResume again. I found out that Android actually destroys and rebuilds the screen when it rotates. At first I thought the log would disappear, but it stayed because onSaveInstanceState saves it before the screen is destroyed and puts it back in onCreate.

Logcat helped me a lot because I could filter it using the tag "LabCheckLifecycle" and see the exact order and time of each step. This made it easy to compare what happens with rotation versus the Home button.

For the enhancements, I made the header show "CompLab 1" and the app's applicationId. I also added a Clear Log button that resets the log on the screen, and it doesn't crash when I rotate the phone.

The main problem I had was that the code wouldn't build at first because it uses view binding. I had to turn on viewBinding in build.gradle.kts and sync Gradle. I also had to change the package name from the sample code to my own (deleon_appdev).