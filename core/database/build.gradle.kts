plugins {
    alias(libs.plugins.runmate.android.library)
    alias(libs.plugins.runmate.android.room)
}

android {
    namespace = "com.romanuel.core.database"
}

dependencies {
    implementation(libs.org.mongodb.bson)

    implementation(projects.core.domain)
}
