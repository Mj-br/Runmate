plugins {
    alias(libs.plugins.runmate.android.library)
}

android {
    namespace = "com.romanuel.core.database"
}

dependencies {
    implementation(libs.org.mongodb.bson)

    implementation(projects.core.domain)
}
