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
        maven { url = uri("https://www.jitpack.io") }
    }
}

rootProject.name = "Todo"
include(":app")
include(
    ":core:data",
    ":core:data-api",
    ":core:database",
    ":core:datastore",
    ":core:design-system",
    ":core:domain",
    ":core:model",
    ":core:navigation",
    ":core:utils",
)
include(
    ":feature:home",
    ":feature:main",
    ":feature:setting"
)
