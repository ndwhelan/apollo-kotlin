import com.android.build.gradle.BaseExtension

plugins {
  alias(libs.plugins.android.library)
  alias(libs.plugins.kotlin.android)
  alias(libs.plugins.apollo)
}

dependencies {
  add("implementation", libs.apollo.api)
}

configure<BaseExtension> {
  compileSdkVersion(libs.versions.android.sdkversion.compile.get().toInt())

  defaultConfig {
    minSdkVersion(libs.versions.android.sdkversion.min.get())
    targetSdkVersion(libs.versions.android.sdkversion.target.get())
  }

  // This doesn't really make sense for a library project, but still allows to compile flavor source sets
  flavorDimensions("version")
  productFlavors {
    create("demo") {
      versionNameSuffix = "-demo"
    }
    create("full") {
      versionNameSuffix = "-full"
    }
  }
}

apollo {
  createAllAndroidVariantServices(".", "example") {
    // Here we set the same schema file for all variants
    schemaFile.set(file("src/main/graphql/com/example/schema.sdl"))
    packageName.set("com.example")
  }
}
