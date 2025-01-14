plugins {
    alias(libs.plugins.kotlinJvm)
    alias(libs.plugins.ktor)
    alias(libs.plugins.kotlinSerialization)
    application
}

group = "com.itami.workout_flow"
version = "1.0.0"
application {
    mainClass.set("com.itami.workout_flow.ApplicationKt")
    applicationDefaultJvmArgs = listOf("-Dio.ktor.development=${extra["io.ktor.development"] ?: "false"}")
}

dependencies {
    implementation(projects.shared)
    implementation(libs.bundles.ktor.server)
    implementation(libs.logback)
    implementation(libs.koin.ktor)
    implementation(libs.postgres)
    implementation(libs.hikari)
    implementation(libs.bundles.exposed)
    implementation(libs.jbcrypt)
}