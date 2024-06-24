plugins {
    java
    application
}

repositories {
    mavenCentral()
}

dependencies {
    val junitVersion = "5.9.1"
    testImplementation("org.junit.jupiter:junit-jupiter-api:$junitVersion")
    testImplementation("org.junit.jupiter:junit-jupiter-params:$junitVersion")
    testRuntimeOnly("org.junit.jupiter:junit-jupiter-engine:$junitVersion")
    implementation("org.json:json:20090211")
}

tasks.test {
    useJUnitPlatform()
}

sourceSets {
    main {
        java {
            srcDir("src/main/java")
        }
        resources {
            srcDir("src/main/resources")
        }
    }
}

application {
    mainClass.set("slayin.Slayin")
}

tasks.withType<Jar> { duplicatesStrategy = DuplicatesStrategy.EXCLUDE }

tasks.processResources {
    duplicatesStrategy = DuplicatesStrategy.EXCLUDE
}

tasks.jar {
    manifest {
        attributes["Main-Class"] = application.mainClass.get()
    }

    from(configurations.runtimeClasspath.get().map { if (it.isDirectory) it else zipTree(it) })

    from({
        sourceSets.main.get().resources.srcDirs
    })
}