import java.util.Properties
import java.io.File

// Helper to read local.properties
val localProperties = Properties().apply {
    val file = rootProject.file("local.properties")
    if (file.exists()) {
        file.inputStream().use { load(it) }
    }
}

publishing {
    publications {
        register<MavenPublication>("release") {
            groupId = "com.builtlab.resources"
            artifactId = "theme"
            version = "1.0.1"

            afterEvaluate {
                from(components["release"])
            }
        }
    }
    repositories {
        maven {
            name = "GitHubPackages"
            url = uri("https://maven.pkg.github.com/Builtlab/Jetpack-Compose-Theme")
            credentials {
                username = System.getenv("GITHUB_ACTOR") 
                    ?: localProperties.getProperty("gpr.user") 
                    ?: "builtlab-bot"
                
                password = System.getenv("PRIVATE_GITHUB_TOKEN")
                    ?: localProperties.getProperty("gpr.key") 
                    ?: ""
            }
        }
    }
}