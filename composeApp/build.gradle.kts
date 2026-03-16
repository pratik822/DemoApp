import org.jetbrains.kotlin.gradle.dsl.JvmTarget

plugins {
    alias(libs.plugins.kotlinMultiplatform)
    alias(libs.plugins.androidApplication)
    alias(libs.plugins.composeMultiplatform)
    alias(libs.plugins.composeCompiler)
    alias(libs.plugins.kotlinSerialization)
    alias(libs.plugins.sqldelight)
}

kotlin {

    androidTarget {
        compilerOptions {
            jvmTarget.set(JvmTarget.JVM_11)
        }
    }

    js(IR) {
        browser()
        binaries.executable()
    }

    sourceSets {

        val commonMain by getting {
            dependencies {

                // Core modules
                implementation(project(":core"))
                implementation(project(":features:dashboard_feature"))
                implementation(project(":features:home_listing_feature"))
                implementation(project(":features:news_detail_feature"))

                // Compose
                implementation(libs.compose.runtime)
                implementation(libs.compose.foundation)
                implementation(libs.compose.material3)
                implementation(libs.compose.ui)
                implementation(libs.compose.components.resources)
                implementation(libs.compose.uiToolingPreview)

                // Lifecycle
                implementation(libs.androidx.lifecycle.viewmodelCompose)
                implementation(libs.androidx.lifecycle.runtimeCompose)

                // Coroutines
                implementation("org.jetbrains.kotlinx:kotlinx-coroutines-core:1.10.1")

                // Navigation
                implementation(libs.androidx.navigation3.ui)

                // Ktor
                implementation(libs.ktor.client.core)
                implementation(libs.ktor.client.content.negotiation)
                implementation(libs.ktor.serialization.kotlinx.json)
                implementation(libs.kotlinx.serialization.json)

                // Koin
                implementation(libs.koin.core)
                implementation(libs.koin.compose)
                implementation(libs.koin.compose.viewmodel)

                // Coil
                implementation("io.coil-kt.coil3:coil-compose:3.0.4")
                implementation("io.coil-kt.coil3:coil-network-ktor3:3.0.4")

                // Icons
                implementation("org.jetbrains.compose.material:material-icons-extended:1.7.3")
                implementation(libs.sqldelight.runtime)
                implementation(libs.sqldelight.coroutines)
            }
        }

        val androidMain by getting {
            dependencies {

                implementation(libs.compose.uiToolingPreview)
                implementation(libs.androidx.activity.compose)
                implementation(libs.sqldelight.android.driver)


                // Ktor Android
                implementation(libs.ktor.client.okhttp)

                // Koin Android
                implementation(libs.koin.android)
                
                // Google Maps
                implementation("com.google.maps.android:maps-compose:8.2.1")
                implementation("com.google.android.gms:play-services-maps:19.0.0")
            }
        }

        val jsMain by getting {
            dependencies {
                implementation(libs.sqldelight.web.driver)
                implementation(compose.html.core)

            }
        }

        val commonTest by getting {
            dependencies {

                implementation(libs.kotlin.test)
                implementation(libs.kotlinx.coroutines.test)
            }
        }

        val androidUnitTest by getting {
            dependencies {

                implementation("org.jetbrains.kotlinx:kotlinx-coroutines-test:1.7.3")
                implementation(libs.mockk)
            }
        }
    }
}

// Extract the worker files from the JS runtime classpath


android {

    namespace = "com.pratik.demoapp"
    compileSdk = libs.versions.android.compileSdk.get().toInt()

    defaultConfig {

        applicationId = "com.pratik.demoapp"
        minSdk = libs.versions.android.minSdk.get().toInt()
        targetSdk = libs.versions.android.targetSdk.get().toInt()

        versionCode = 1
        versionName = "1.0"
    }

    packaging {

        resources {
            excludes += "/META-INF/{AL2.0,LGPL2.1}"
        }
    }

    buildTypes {

        getByName("release") {
            isMinifyEnabled = false
        }
    }

    compileOptions {

        sourceCompatibility = JavaVersion.VERSION_11
        targetCompatibility = JavaVersion.VERSION_11
    }
}

dependencies {

    debugImplementation(libs.compose.uiTooling)
}
