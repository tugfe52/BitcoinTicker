buildscript {
    repositories {
        google()
        mavenCentral()
    }
    dependencies {
        classpath("androidx.navigation:navigation-safe-args-gradle-plugin:2.5.3")
        classpath("com.google.dagger:hilt-android-gradle-plugin:2.43.2")
        classpath("com.android.tools.build:gradle:7.3.1")
        classpath("com.google.gms:google-services:4.3.14")
        classpath ("com.google.firebase:firebase-crashlytics-gradle:2.8.1")
    }
}
plugins {
    id("com.android.application") version "7.4.0" apply false
    id("org.jetbrains.kotlin.android") version "1.8.0" apply false
    id("com.android.library") version "7.4.0" apply false
}