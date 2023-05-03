plugins {
    `java-library`
    id("xyz.jpenilla.run-paper") version "2.0.1" // Adds runServer and runMojangMappedServer tasks for testing
    id("maven-publish")
    id ("com.github.johnrengelman.shadow") version "8.1.1";
    id("io.papermc.paperweight.userdev") version "1.5.4"
}

group = "de.oliver"
version = "1.0.1-folia"
description = "Perks plugin"

java {
    toolchain.languageVersion.set(JavaLanguageVersion.of(17))
}

repositories {
    mavenLocal()
    mavenCentral()
    maven("https://repo.papermc.io/repository/maven-public/")
    maven("https://jitpack.io")
    maven("https://repo.alessiodp.com/releases/")
}

dependencies {
    paperweight.foliaDevBundle("1.19.4-R0.1-SNAPSHOT")
    compileOnly("net.kyori:adventure-text-minimessage:4.13.1")
    compileOnly("com.github.MilkBowl:VaultAPI:1.7.1")
    compileOnly("net.luckperms:api:5.4")

    implementation("net.byteflux:libby-bukkit:1.2.0")
    compileOnly("com.github.FancyMcPlugins:FancyLib:225ba14e03")
}

tasks {
    runServer{
        minecraftVersion("1.19.4")
    }

    shadowJar{
        archiveClassifier.set("")
    }

    publishing {
        publications {
            create<MavenPublication>("maven") {
                groupId = project.group.toString()
                artifactId = project.name
                version = project.version.toString()
                from(project.components["java"])
            }
        }
    }

    compileJava {
        options.encoding = Charsets.UTF_8.name() // We want UTF-8 for everything

        // Set the release flag. This configures what version bytecode the compiler will emit, as well as what JDK APIs are usable.
        // See https://openjdk.java.net/jeps/247 for more information.
        options.release.set(17)
    }
    javadoc {
        options.encoding = Charsets.UTF_8.name() // We want UTF-8 for everything
    }
    processResources {
        filteringCharset = Charsets.UTF_8.name() // We want UTF-8 for everything
    }
}