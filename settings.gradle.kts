rootProject.name = "TdMultiplatform"
enableFeaturePreview("TYPESAFE_PROJECT_ACCESSORS")

pluginManagement {
    includeBuild("conventions")
    repositories {
        google {
            mavenContent {
                includeGroupAndSubgroups("androidx")
                includeGroupAndSubgroups("com.android")
                includeGroupAndSubgroups("com.google")
            }
        }
        mavenCentral()
        gradlePluginPortal()
    }
}

dependencyResolutionManagement {
    repositories {
        google {
            mavenContent {
                includeGroupAndSubgroups("androidx")
                includeGroupAndSubgroups("com.android")
                includeGroupAndSubgroups("com.google")
            }
        }
        mavenCentral()
    }
}

include(":core:app")
include(":core:coroutines")
include(":core:database:gateway")
include(":core:database:implementation")
include(":core:datastore:gateway")
include(":core:datastore:implementation")
include(":core:environment:gateway")
include(":core:environment:implementation")
include(":core:injection")
include(":core:kotlin")
include(":core:log:gateway")
include(":core:log:implementation")
include(":core:mvi")
include(":core:navigation:gateway")
include(":core:navigation:implementation")
include(":core:network:gateway")
include(":core:network:implementation")
include(":core:repository")
include(":core:testing:gateway")
include(":core:testing:implementation")
include(":core:ui")

// weather app packages
include(":weather:app")
include(":weather:core:measure:gateway")
include(":weather:core:ui")
include(":weather:city:data")
include(":weather:city:domain")
include(":weather:city:ui")
include(":weather:forecast:data")
include(":weather:forecast:domain")
include(":weather:forecast:ui")
include(":weather:onboarding:data")
include(":weather:onboarding:domain")
include(":weather:onboarding:ui")
include(":weather:settings:data")
include(":weather:settings:domain")
include(":weather:settings:ui")

// to-do app packages
include(":todo:app")
include(":todo:core:ui")
include(":todo:calendar:domain")
include(":todo:calendar:ui")
include(":todo:home:domain")
include(":todo:home:ui")
include(":todo:settings:data")
include(":todo:settings:domain")
include(":todo:settings:ui")
include(":todo:onboarding:data")
include(":todo:onboarding:domain")
include(":todo:onboarding:ui")
include(":todo:tasks:data")
include(":todo:tasks:domain")
include(":todo:tasks:ui")

// compiler
include(":core:injection:compiler")

