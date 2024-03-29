plugins {
    application
    id("com.google.devtools.ksp") version "1.9.23-1.0.19"
    kotlin("jvm") version "1.9.23"
}

application {
    mainClass.set("it.lorthirk.komapperplayground.ApplicationKt")
}

group = "it.lorthirk"
version = "1.0-SNAPSHOT"

repositories {
    mavenCentral()
}

dependencies {
    val komapperVersion = "1.17.0"
    platform("org.komapper:komapper-platform:$komapperVersion").let {
        implementation(it)
        ksp(it)
    }
    implementation("org.komapper:komapper-starter-r2dbc")
    implementation("org.komapper:komapper-dialect-h2-r2dbc")
    ksp("org.komapper:komapper-processor")
    testImplementation("org.junit.jupiter:junit-jupiter-api:5.8.2")
    testRuntimeOnly("org.junit.jupiter:junit-jupiter-engine:5.8.2")
}

tasks.test {
    useJUnitPlatform()
}
kotlin {
    jvmToolchain(21)
}
