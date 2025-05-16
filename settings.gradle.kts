pluginManagement {
    repositories {
        google()
        mavenCentral()
        gradlePluginPortal()
    }
}

dependencyResolutionManagement {
    repositories {
        google()
        mavenCentral()
        maven {
            url = uri("https://maven.pkg.github.com/sk-ai-net/skainet")
            credentials {
                username = providers.gradleProperty("gpr.user").orElse(System.getenv("GITHUB_ACTOR")).get()
                password = providers.gradleProperty("gpr.token").orElse(System.getenv("GITHUB_TOKEN")).get()
            }
        }
    }
}

rootProject.name = "skainet-jupyter"

include("kotlin-jupyter")