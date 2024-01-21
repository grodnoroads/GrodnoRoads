import com.github.benmanes.gradle.versions.updates.DependencyUpdatesTask
import org.jetbrains.kotlin.gradle.dsl.KotlinJvmOptions
import org.jetbrains.kotlin.gradle.tasks.KotlinCompile
import java.util.Locale

plugins {
    alias(libs.plugins.android.application) apply false
    alias(libs.plugins.android.library) apply false
    alias(libs.plugins.buildkonfig) apply false
    alias(libs.plugins.firebase.crashlytics) apply false
    alias(libs.plugins.google.services) apply false
    alias(libs.plugins.gradle.dependency.check)
    alias(libs.plugins.kotlin.android) apply false
    alias(libs.plugins.kotlin.multiplatform) apply false
    alias(libs.plugins.kotlin.serialization) apply false
    alias(libs.plugins.secrets) apply false
}

tasks {
    registering(Delete::class) {
        delete(layout.buildDirectory)
    }
    withType<DependencyUpdatesTask> {
        // https://github.com/ben-manes/gradle-versions-plugin/issues/816
        filterConfigurations = Spec<Configuration> {
            !it.name.startsWith("incrementalScalaAnalysis")
        }
        rejectVersionIf {
            isNonStable(candidate.version)
        }
    }
}

fun isNonStable(version: String): Boolean {
    val stableKeyword = listOf("RELEASE", "FINAL", "GA").any {
        version.uppercase(Locale.getDefault()).contains(it)
    }
    val regex = "^[0-9,.v-]+(-r)?$".toRegex()
    val isStable = stableKeyword || regex.matches(version)
    return isStable.not()
}

subprojects {
    tasks.withType<KotlinCompile>().configureEach {
        kotlinOptions {
            if (project.findProperty("enableComposeCompilerReports") == "true") {
                val reportsPath = project.layout.buildDirectory
                    .dir("compose_metrics")
                    .get()
                    .asFile
                    .absolutePath
                composeMetrics(path = reportsPath)
            }
            stabilityConfigurationPath(path = "$rootDir/config/compose-stability.config")
        }
    }
}

fun KotlinJvmOptions.composeMetrics(path: String) {
    freeCompilerArgs += listOf(
        "-P",
        "plugin:androidx.compose.compiler.plugins.kotlin:reportsDestination=$path",
        "-P",
        "plugin:androidx.compose.compiler.plugins.kotlin:metricsDestination=$path"
    )
}

fun KotlinJvmOptions.stabilityConfigurationPath(path: String) {
    freeCompilerArgs += listOf(
        "-P",
        "plugin:androidx.compose.compiler.plugins.kotlin:stabilityConfigurationPath=$path"
    )
}