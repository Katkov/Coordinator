# Coordinator
An implementation of Jetpack Compose version [CoordinatorLayout](https://developer.android.com/reference/androidx/coordinatorlayout/widget/CoordinatorLayout)) for Jetpack Compose

## Installation
Add `maven { url 'https://jitpack.io' }` to settings.gradle:

```gradle
pluginManagement {
    repositories {
        ...
        maven { url 'https://jitpack.io' }
    }
}
dependencyResolutionManagement {
    repositoriesMode.set(RepositoriesMode.FAIL_ON_PROJECT_REPOS)
    repositories {
        ...
        maven { url 'https://jitpack.io' }
    }
}
```

Add the gradle dependency:

```gradle
implementation 'com.github.Katkov:Coordinator:0.0.2'
```

## Usage
`CoordinatorLayout` is a container where you can place one Widget as Toolbar and specify items of the LazyColumn content,

```kotlin
CoordinatorLayout(
        headerElevation = 1.dp,
        behavior = EnterAlwaysCollapsedBehavior(toolbarState),
        toolbarContent = {
            // Toolbar widget (It can be any widget not only Toolbar)
        }) {
        // Any LazyListScope items 
    }
```
`AppBarLayout` is the widget, which mimics CollapsingToolbarLayout behavior

There are 4 behaviors, which consume ToolbarState:
`EnterAlwaysCollapsedBehavior`
`ExitUntilCollapsedBehavior`
`EnterAlwaysBehavior`
`ScrollBehavior`

To `EnterAlwaysCollapsedBehavior` and `ExitUntilCollapsedBehavior` you need to give ToolbarState with initial values of minToolbarHeightPx and maxToolbarHeightPx.
`EnterAlwaysBehavior` and `ScrollBehavior` need only maxToolbarHeightPx.

You can find an example in MainActity of the CoordinatorLayout.

All the behaviors are extendable, so you can implement some kind of animation effects.
You can find an example of extended behavior in this project:
https://github.com/Katkov/WeatherApp/tree/coordinator-library

