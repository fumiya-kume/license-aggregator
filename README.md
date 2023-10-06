# license-aggregator

The license screen generation library for Android, especially for multi-module Android application.

# How to use

1. Update root level `build.gradle` if first time

```gradle
	dependencyResolutionManagement {
		repositoriesMode.set(RepositoriesMode.FAIL_ON_PROJECT_REPOS)
		repositories {
			mavenCentral()
			maven { url 'https://jitpack.io' }
		}
	}
```

2. Add the dependency

```gradle
dependencies {
   implementation 'com.github.fumiya-kume:license-aggregator:Tag'
}
```
