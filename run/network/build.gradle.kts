plugins {
    alias(libs.plugins.runmate.android.library)
}

android {
    namespace = "com.romanuel.run.network"
}

dependencies {
    implementation(projects.core.data)
    implementation(projects.core.domain)
}
