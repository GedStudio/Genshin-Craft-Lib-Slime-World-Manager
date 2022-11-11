plugins {
    id("com.github.johnrengelman.shadow") version "7.1.2"
    id("io.papermc.paperweight.userdev") version "1.3.5"
}

dependencies {
    paperDevBundle("1.19.2-R0.1-SNAPSHOT")

    compileOnly("io.papermc.paper:paper-api:1.18.2-R0.1-SNAPSHOT")

    implementation(project(":slimeworldmanager-api"))

    implementation("org.javassist:javassist:3.28.0-GA")
    implementation("com.flowpowered:flow-nbt:2.0.2")
    implementation("com.github.luben:zstd-jni:1.5.2-2")
    implementation("com.zaxxer:HikariCP:5.0.1")
    implementation("org.mongodb:mongo-java-driver:3.12.11")
    implementation("io.lettuce:lettuce-core:6.2.0.RELEASE")
    implementation("org.spongepowered:configurate-yaml:4.1.2")
    implementation("commons-io:commons-io:2.11.0")
    implementation("com.github.tomas-langer:chalk:1.0.2")
}

tasks {
    shadowJar {
        archiveClassifier.set("")

        relocate("ninja.leaping.configurate", "net.deechael.genshin.lib.depend.configurate")
        relocate("com.flowpowered.nbt", "net.deechael.genshin.lib.depend.nbt")
        relocate("com.zaxxer.hikari", "net.deechael.genshin.lib.depend.hikari")
        relocate("com.mongodb", "net.deechael.genshin.lib.depend.mongodb")
        relocate("io.lettuce", "net.deechael.genshin.lib.depend.lettuce")
        relocate("org.bson", "net.deechael.genshin.lib.depend.bson")
    }

    assemble {
        dependsOn(reobfJar)
        dependsOn(shadowJar)
    }

    java {
        toolchain {
            languageVersion.set(JavaLanguageVersion.of(17))
        }
    }

    compileJava {
        options.release.set(17)
    }

}



description = "slimeworldmanager-plugin"
