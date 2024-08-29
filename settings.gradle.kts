pluginManagement {
    repositories {
        google {
            content {
                includeGroupByRegex("com\\.android.*")
                includeGroupByRegex("com\\.google.*")
                includeGroupByRegex("androidx.*")
            }
        }
        mavenCentral()
        gradlePluginPortal()
    }
}
dependencyResolutionManagement {
    repositoriesMode.set(RepositoriesMode.FAIL_ON_PROJECT_REPOS)
    repositories {
        google()
        mavenCentral()

        // buzzvil
        maven("https://dl.buzzvil.com/public/maven")

    }
}

rootProject.name = "AddOn"
include(":Presenter-App")
include(":Library-SDK-PH")
include("Library-SDK-AddOn:Library-SDK-AddOn-Buzzvil")
include("Library-SDK-AddOn:Library-SDK-AddOn-LockScreen")
include(":Library-SDK-AddOn:Library-SDK-AddOn-Factory")
include(":Library-SDK-AddOn:Library-SDK-AddOn-Connection")
