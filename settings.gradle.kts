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
    }
}

enableFeaturePreview("TYPESAFE_PROJECT_ACCESSORS")

//rootProject.name = "MyWeather"
include(":app")
include(":core:common")
include(":feature:home:data")
include(":feature:home:presentation")
include(":feature:search:data")
include(":feature:forecast:data")
include(":core:database")
include(":feature:forecast:presentation")
include(":feature:search:presentation")
include(":feature:favorites:data")
include(":feature:favorites:presentation")
