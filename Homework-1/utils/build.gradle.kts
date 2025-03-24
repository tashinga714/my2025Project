group = rootProject.group
version = rootProject.version

plugins {
    java
    id("org.jetbrains.kotlin.jvm")
}

dependencies {
    implementation("org.jetbrains.kotlin:kotlin-reflect:1.7.10")
    implementation("com.fasterxml.jackson.module:jackson-module-kotlin:2.13.+")
}
