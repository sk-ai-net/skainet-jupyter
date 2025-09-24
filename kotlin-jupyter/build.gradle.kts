plugins {
    alias(libs.plugins.jetbrainsKotlinJvm)
    `maven-publish`
    alias(libs.plugins.johnrengelman.shadow)
}

dependencies {
    implementation(libs.skainet.api)
    implementation(libs.skainet.core)
    implementation(libs.skainet.nn)
}

tasks.test {
    useJUnitPlatform()
}

kotlin {
    jvmToolchain(21)
}

tasks.shadowJar {
    archiveClassifier.set("")

    // Include sk.ainet.core dependencies
    dependencies {
        include(dependency("sk.ainet.core:.*:.*"))
    }
}

// Configure publishing
publishing {
    publications {
        create<MavenPublication>("maven") {
            groupId = "sk.ainet"
            artifactId = "kotlin-jupyter"
            version = "0.0.1"

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
}
