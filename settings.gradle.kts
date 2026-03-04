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

rootProject.name = "TestTaskEffectiveMobile"
include(":app")
include(":core")
include(":feature")
include(":core:design")
include(":core:network")
include(":core:database")
include(":feature:auth")
include(":feature:main")
include(":feature:favorite")
include(":feature:account")
include(":navigation")
include(":feature:coursedetail")
include(":core:domain")
include(":core:data")
