plugins {
    alias(libs.plugins.runmate.android.library)
    alias(libs.plugins.runmate.jvm.ktor)
}

android {
    namespace = "com.romanuel.auth.data"
}

dependencies {
    implementation(projects.auth.domain)
    implementation(projects.core.data)
    implementation(projects.core.domain)

}
