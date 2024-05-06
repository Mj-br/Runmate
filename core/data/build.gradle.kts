plugins {
    alias(libs.plugins.runmate.android.library)
    alias(libs.plugins.runmate.jvm.ktor)
}

android {
    namespace = "com.romanuel.core.data"
}

dependencies {
    implementation(libs.timber)

    implementation(projects.core.database)
    implementation(projects.core.domain)
}
