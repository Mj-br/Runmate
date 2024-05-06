plugins {
    alias(libs.plugins.runmate.android.library)
    alias(libs.plugins.runmate.jvm.ktor)
}

android {
    namespace = "com.romanuel.run.network"
}

dependencies {
    implementation(projects.core.data)
    implementation(projects.core.domain)
}
