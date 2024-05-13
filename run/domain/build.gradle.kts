plugins {
    alias(libs.plugins.runmate.jvm.library)
}

dependencies {
    implementation(projects.core.domain)

    implementation(libs.kotlinx.coroutines.core)
}
