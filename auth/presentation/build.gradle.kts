plugins {
    alias(libs.plugins.runmate.android.feature.ui)
}

android {
    namespace = "com.romanuel.auth.presentation"
}

dependencies {

    implementation(projects.auth.domain)
    implementation(projects.core.domain)
    implementation(projects.core.presentation.ui)
}
