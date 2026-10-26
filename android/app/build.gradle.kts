plugins {
    id("com.android.application")
}

val buildNumber = (System.getenv("GITHUB_RUN_NUMBER") ?: "1").toInt()

android {
    namespace = "com.martinb.alphaomega"
    compileSdk = 34

    defaultConfig {
        applicationId = "com.martinb.alphaomega"
        minSdk = 24
        targetSdk = 34
        versionCode = buildNumber
        versionName = "1.0.$buildNumber"
    }

    // Release key: the workflow decodes the ALPHAOMEGA_KEYSTORE_B64 secret into alphaomega.jks and passes
    // the password as an env var, so every new APK installs over the previous one and keeps the reader's data.
    // Never commit the key or its password.
    val keystore = file("alphaomega.jks")
    signingConfigs {
        create("release") {
            storeFile = keystore
            storePassword = System.getenv("ALPHAOMEGA_KEYSTORE_PASSWORD")
            keyAlias = "alphaomega"
            keyPassword = System.getenv("ALPHAOMEGA_KEYSTORE_PASSWORD")
        }
    }

    buildTypes {
        getByName("release") {
            isMinifyEnabled = false
            // Pull requests build without the secret: they get the debug key and are never published.
            signingConfig = signingConfigs.getByName(if (keystore.exists()) "release" else "debug")
        }
    }

    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_17
        targetCompatibility = JavaVersion.VERSION_17
    }

    // The app's pages come straight from the repository's app/ folder (the same files as the web version).
    sourceSets["main"].assets.srcDirs("../../app")

    lint {
        checkReleaseBuilds = false
        abortOnError = false
    }
}

dependencies {
    implementation("androidx.webkit:webkit:1.11.0")
}
