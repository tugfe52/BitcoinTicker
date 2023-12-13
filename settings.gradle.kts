pluginManagement {
    repositories {
        google()
        mavenCentral()
        gradlePluginPortal()
    }
}
dependencyResolutionManagement {
    repositoriesMode.set(RepositoriesMode.FAIL_ON_PROJECT_REPOS)
    repositories {
        google()
        mavenCentral()
        maven {
            url = uri("https://jitpack.io")
            isAllowInsecureProtocol = true
        }
        maven {
            url = uri("https://maven.google.com")
            isAllowInsecureProtocol = true
        }
    }
}

rootProject.name = "BitcoinTicker"
include(":app")
