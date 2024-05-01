plugins {
    alias(libs.plugins.runmate.android.library)
}

android {
    namespace = "com.romanuel.core.data"
}

dependencies {
    implementation(libs.timber)

    implementation(projects.core.database)
    implementation(projects.core.domain)
}
