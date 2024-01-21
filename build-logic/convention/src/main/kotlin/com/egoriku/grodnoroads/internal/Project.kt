package com.egoriku.grodnoroads.internal

import com.android.build.api.dsl.ApplicationExtension
import com.android.build.api.dsl.LibraryExtension
import org.gradle.accessors.dm.LibrariesForLibs
import org.gradle.api.Project
import org.gradle.kotlin.dsl.configure
import org.gradle.kotlin.dsl.the
import org.jetbrains.kotlin.gradle.dsl.KotlinAndroidProjectExtension
import org.jetbrains.kotlin.gradle.dsl.KotlinMultiplatformExtension

internal val Project.libs
    get() = the<LibrariesForLibs>()

internal fun Project.libraryExtension(action: LibraryExtension.() -> Unit) =
    extensions.configure<LibraryExtension>(action)

internal fun Project.kmmExtension(action: KotlinMultiplatformExtension.() -> Unit) =
    extensions.configure<KotlinMultiplatformExtension>(action)

internal fun Project.applicationExtension(action: ApplicationExtension.() -> Unit) =
    extensions.configure<ApplicationExtension>(action)

internal fun Project.configureKotlinAndroidToolchain() {
    extensions.configure<KotlinAndroidProjectExtension> {
        jvmToolchain(17)
    }
}