plugins {
  id("org.jetbrains.kotlin.jvm")
  id("apollo.test")
  id("com.apollographql.apollo3")
}

dependencies {
  implementation(golatac.lib("apollo.runtime"))
  implementation(project(":multi-module-parent-project:root"))
  testImplementation(golatac.lib("kotlin.test.junit"))
}

apollo {
  service("multimodule") {
    packageName.set("multimodule.child")
    flattenModels.set(false)
    parentProject(":multi-module-parent-project:root")
  }
}
