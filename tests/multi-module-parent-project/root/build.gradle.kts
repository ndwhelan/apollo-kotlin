plugins {
  id("org.jetbrains.kotlin.jvm")
  id("apollo.test")
  id("com.apollographql.apollo3")
}

dependencies {
  implementation(golatac.lib("apollo.api"))
}

apollo {
  service("multimodule") {
    packageName.set("multimodule.root")
    generateDataBuilders.set(true)
    generateApolloMetadata.set(true)
  }
}

