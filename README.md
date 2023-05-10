# Coordinator
An implementation of Jetpack Compose version [CollapsingToolbarLayout](https://developer.android.com/reference/com/google/android/material/appbar/CollapsingToolbarLayout) for Jetpack Compose

## Installation
Add `maven { url 'https://jitpack.io' }` to settings.gradle:

```gradle
pluginManagement {
    repositories {
        gradlePluginPortal()
        google()
        mavenCentral()
        maven { url 'https://jitpack.io' }
    }
}
dependencyResolutionManagement {
    repositoriesMode.set(RepositoriesMode.FAIL_ON_PROJECT_REPOS)
    repositories {
        google()
        mavenCentral()
        maven { url 'https://jitpack.io' }
    }
}
```

Add the gradle dependency:

```gradle
implementation 'com.github.Katkov:Coordinator:0.0.2'
```
