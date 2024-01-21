@file:Suppress("unused")

import com.egoriku.grodnoroads.internal.*
import org.gradle.api.JavaVersion
import org.gradle.api.Plugin
import org.gradle.api.Project
import org.gradle.kotlin.dsl.apply
import org.jetbrains.kotlin.gradle.dsl.KotlinVersion

class AndroidKmpLibraryPlugin : Plugin<Project> {

    override fun apply(target: Project) = with(target) {
        apply(plugin = libraryPluginId)
        apply(plugin = kotlinMultiplatformPluginId)

        kmmExtension {
            targets.all {
                compilations.all {
                    kotlinOptions {
                        freeCompilerArgs += "-Xcontext-receivers"
                        freeCompilerArgs += "-Xexpect-actual-classes"
                        languageVersion = KotlinVersion.KOTLIN_1_9.version
                    }
                }
            }
        }

        libraryExtension {
            defaultConfig {
                minSdk = libs.versions.minSdk.get().toInt()
                compileSdk = libs.versions.compileSdk.get().toInt()

                testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"

                consumerProguardFiles("consumer-rules.pro")
            }

            buildTypes {
                release {
                    isMinifyEnabled = true
                    proguardFiles(
                        getDefaultProguardFile("proguard-android-optimize.txt"),
                        "proguard-rules.pro"
                    )
                }
            }

            compileOptions {
                sourceCompatibility = JavaVersion.VERSION_17
                targetCompatibility = JavaVersion.VERSION_17
            }
        }
    }
}