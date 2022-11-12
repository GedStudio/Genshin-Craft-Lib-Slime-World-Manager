plugins {
    id("com.github.johnrengelman.shadow") version "7.1.2"
}

dependencies {
    implementation("org.javassist:javassist:3.28.0-GA")
    implementation("org.yaml:snakeyaml:1.30")
}

sourceSets {
    main {
        resources {
            include("**/*")
        }
    }
}

tasks {
    jar {
        manifest {
            attributes["Premain-Class"] = "net.deechael.genshin.lib.impl.world.clsm.NMSTransformer"
        }
    }

    shadowJar {
        archiveClassifier.set("")
    }

    assemble {
        dependsOn(shadowJar)
    }
}

description = "slimeworldmanager-clsm"