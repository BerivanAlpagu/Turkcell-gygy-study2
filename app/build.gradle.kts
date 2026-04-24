import java.util.Properties

plugins {
    alias(libs.plugins.android.application)
    alias(libs.plugins.kotlin.android)
    alias(libs.plugins.kotlin.compose)
    id("org.jetbrains.kotlin.plugin.serialization") version "2.0.21"
}

//Serialization: SupabaseClienta verilecek URL ve apikey buradan okutulur main activity olsaydı local propertiesden okutabilirdin.
//Serialization: Herhangi bir dosyadan okuyup bunu kotline atama işlemine denir. DeSERİLİZATİON DA BUNUN TAM TERSİDİR.
val localProperties = Properties().apply(){
    val localPropertiesFile = rootProject.file("local.properties")
    if(localPropertiesFile.exists()){ // eğer load.properties varsa yükle bu fileı
        load(localPropertiesFile.inputStream())
    }
}

val supabaseUrl: String=localProperties.getProperty("supabase.url", "")
val supabaseKey: String=localProperties.getProperty("supabase.key", "")

android {
    namespace = "com.example.turkcellgygy"
    compileSdk {
        version = release(36)
    }

    defaultConfig {
        applicationId = "com.example.turkcellgygy"
        minSdk = 24
        targetSdk = 36
        versionCode = 1
        versionName = "1.0"

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
    }

    buildTypes {
        release {
            isMinifyEnabled = false
            proguardFiles(
                getDefaultProguardFile("proguard-android-optimize.txt"),
                "proguard-rules.pro"
            )
        }
    }
    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_11
        targetCompatibility = JavaVersion.VERSION_11
    }
    kotlinOptions {
        jvmTarget = "11"
    }
    buildFeatures {
        compose = true
    }
}

dependencies {
    //TODO: Add all dependencies to libs.versions.toml yap ki magic string olmasın
    implementation(libs.navigation.compose)
    implementation(libs.viewmodel.compose)

    implementation(libs.kotlinx.coroutines.android)
    implementation(libs.retrofit2)
    implementation(libs.retrofit2.converter.gson)

    implementation(libs.supabase.supabase)
    implementation(libs.supabase.postgrest)
    //serialization işlemleri için: supabaseclient için gerekli url ve api keyleri okutabilmek için
    implementation("org.jetbrains.kotlinx:kotlinx-serialization-json:1.11.0")

    //emülatör çökmesi önlemek için retrofite postgrest bağlanmada sorun yaşıyor
    implementation(libs.ktor.client)

    implementation(libs.androidx.core.ktx)
    implementation(libs.androidx.lifecycle.runtime.ktx)
    implementation(libs.androidx.activity.compose)
    implementation(platform(libs.androidx.compose.bom))
    implementation(libs.androidx.compose.ui)
    implementation(libs.androidx.compose.ui.graphics)
    implementation(libs.androidx.compose.ui.tooling.preview)
    implementation(libs.androidx.compose.material3)
    testImplementation(libs.junit)
    androidTestImplementation(libs.androidx.junit)
    androidTestImplementation(libs.androidx.espresso.core)
    androidTestImplementation(platform(libs.androidx.compose.bom))
    androidTestImplementation(libs.androidx.compose.ui.test.junit4)
    debugImplementation(libs.androidx.compose.ui.tooling)
    debugImplementation(libs.androidx.compose.ui.test.manifest)
}