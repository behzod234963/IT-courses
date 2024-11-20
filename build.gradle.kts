// Top-level build file where you can add configuration options common to all sub-projects/modules.
buildscript {
    repositories {
        // other repositories...
        google()
        mavenCentral()
    }
    dependencies{
        classpath(libs.dagger.hilt.gradle.plugin)
        classpath(libs.safe.args.classpath)
    }
}
plugins {
    alias(libs.plugins.android.application) apply false
    alias(libs.plugins.kotlin.android) apply false
    alias(libs.plugins.android.library) apply false
    alias(libs.plugins.dagger.hilt.id) apply false
    alias(libs.plugins.google.gms.google.services) apply false
}