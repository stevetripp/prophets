// Top-level build file where you can add configuration options common to all sub-projects/modules.
plugins {
    alias(libs.plugins.android.application.plugin) apply false
    alias(libs.plugins.androidx.room.plugin) apply false
    alias(libs.plugins.hilt.android.plugin) apply false
    alias(libs.plugins.kotlin.android.plugin) apply false
    alias(libs.plugins.kotlin.compose.plugin) apply false
    alias(libs.plugins.kotlin.serialization.plugin) apply false
    alias(libs.plugins.ksp.plugin) apply false
}
// Ensure pluginManagement repositories are set in settings.gradle.kts
