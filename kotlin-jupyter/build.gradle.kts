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
        }
    }
    repositories {
        mavenLocal()
    }
}
