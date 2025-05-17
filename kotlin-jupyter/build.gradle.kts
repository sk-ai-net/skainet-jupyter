plugins {
    alias(libs.plugins.jetbrainsKotlinJvm)
    `maven-publish`
    alias(libs.plugins.johnrengelman.shadow)
}

dependencies {
    implementation(libs.skainet.core)
}

tasks.test {
    useJUnitPlatform()
}

kotlin {
    jvmToolchain(21)
}

tasks.shadowJar {
    archiveClassifier.set("")

    // Only include sk.ai.net classes
    relocate("sk.ai.net", "sk.ai.net")
    minimize {
        exclude(dependency("sk.ai.net:.*:.*"))
    }

    // Exclude everything else
    dependencies {
        exclude { it.moduleGroup != "sk.ai.net" }
    }
}

// Configure publishing
publishing {
    publications {
        create<MavenPublication>("maven") {
            groupId = "sk.ai.net"
            artifactId = "kotlin-jupyter"
            version = "0.0.6-SNAPSHOT"

            // Use only the shadow jar
            artifact(tasks.shadowJar.get()) {
                classifier = null
            }
            pom {
                description.set("skainet-jupyter")
                name.set(project.name)
                url.set("https://github.com/sk-ai-net/skainet-jupyter/")
                licenses {
                    license {
                        name.set("MIT")
                        distribution.set("repo")
                    }
                }
                scm {
                    url.set("https://github.com/sk-ai-net/skainet-jupyter/")
                    connection.set("scm:git:git@github.com:sk-ai-net/skainet-jupyter.git")
                    developerConnection.set("scm:git:ssh://git@github.com:sk-ai-net/skainet-jupyter.git")
                }
                developers {
                    developer {
                        id.set("sk-ai-net")
                        name.set("sk-ai-net")
                    }
                }
            }
        }

    }

    repositories {
        maven {
            name = "githubPackages"
            url = uri("https://maven.pkg.github.com/sk-ai-net/skainet-jupyter")
            credentials {
                credentials(PasswordCredentials::class)
            }
        }
    }
}
