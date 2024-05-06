plugins {
    alias(libs.plugins.runmate.jvm.library)
}

dependencies {
    implementation(projects.core.domain)
}
