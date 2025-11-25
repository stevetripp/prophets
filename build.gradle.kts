import com.github.benmanes.gradle.versions.updates.DependencyUpdatesTask

// Top-level build file where you can add configuration options common to all sub-projects/modules.
plugins {
    alias(libs.plugins.android.application.plugin) apply false
    alias(libs.plugins.androidx.room.plugin) apply false
    alias(libs.plugins.autonomousapps.dependency.analysis.plugin)
    alias(libs.plugins.ben.manes.versions.plugin)
    alias(libs.plugins.hilt.android.plugin) apply false
    alias(libs.plugins.kotlin.android.plugin) apply false
    alias(libs.plugins.kotlin.compose.plugin) apply false
    alias(libs.plugins.kotlin.serialization.plugin) apply false
    alias(libs.plugins.ksp.plugin) apply false
}
// Ensure pluginManagement repositories are set in settings.gradle.kts

// ===== Gradle Dependency Check =====
// ./gradlew dependencyUpdates -Drevision=release
// ./gradlew dependencyUpdates -Drevision=release --refresh-dependencies
//
// ./gradlew app:dependencyInsight --configuration debugRuntimeClasspath --dependency androidx.room
// ./gradlew shared:dependencyInsight --configuration commonMainApiDependenciesMetadata --dependency androidx.room
// ./gradlew shared:resolvableConfigurations | grep "^Configuration"
tasks.withType<DependencyUpdatesTask> {
    rejectVersionIf { isNonStable(version = candidate.version, includeStablePreRelease = true) }
}

fun isNonStable(version: String, includeStablePreRelease: Boolean): Boolean {
    val stablePreReleaseKeyword = false //  listOf("RC", "BETA").any { version.uppercase().contains(it) }
    val stableKeyword = listOf("RELEASE", "FINAL", "GA").any { version.uppercase().contains(it) }
    val regex = "^[0-9,.v-]+$".toRegex()
    val isStable = if (includeStablePreRelease) {
        stableKeyword || regex.matches(version) || stablePreReleaseKeyword
    } else {
        stableKeyword || regex.matches(version)
    }
    return isStable.not()
}

// ===== Dependency Analysis =====
// ./gradlew projectHealth
dependencyAnalysis {
    issues {
        all {
            onAny { severity("fail") }
            onUnusedDependencies {
                exclude(depGroupAndName(libs.androidx.hilt.navigation.compose))
            }
            onUsedTransitiveDependencies { severity("ignore") }
            onIncorrectConfiguration { severity("ignore") }
            onCompileOnly { severity("ignore") }
            onRuntimeOnly { severity("ignore") }
            onUnusedAnnotationProcessors { }
        }
    }
}

fun depGroupAndName(dependency: Provider<MinimalExternalModuleDependency>): String {
    return dependency.get().let { "${it.group}:${it.name}" }
}

