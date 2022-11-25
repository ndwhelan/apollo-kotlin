package com.apollographql.apollo3.gradle.internal

import com.apollographql.apollo3.gradle.api.Introspection
import com.apollographql.apollo3.gradle.api.Registry
import com.apollographql.apollo3.gradle.api.SchemaConfig
import org.gradle.api.Action
import org.gradle.api.file.ConfigurableFileCollection
import org.gradle.api.model.ObjectFactory
import org.gradle.api.provider.ListProperty
import org.gradle.api.provider.Property
import org.gradle.api.provider.SetProperty

class DefaultSchemaConfig(objects: ObjectFactory) : SchemaConfig {
  override val packageName: Property<String> = objects.property(String::class.java)

  val files: ConfigurableFileCollection = objects.fileCollection()

  var projectPath: String? = null
  var coordinates: String? = null

  val introspection = DefaultIntrospection(objects)
  var hasIntrospection = false
  val registry = DefaultRegistry(objects)
  var hasRegistry = false

  override fun fromFile(file: Any) {
    files.from(file)
  }

  override fun fromFiles(files: Any) {
    this.files.from(files)
  }

  override fun fromProject(projectPath: String) {
    this.projectPath = projectPath
  }

  override fun fromDependency(coordinates: String) {
    this.coordinates = coordinates
  }

  override fun introspection(configure: Action<in Introspection>) {
    hasIntrospection = true
    configure.execute(introspection)
  }

  override fun registry(configure: Action<in Registry>) {
    hasRegistry = true
    configure.execute(registry)
  }

  val scalarTypeMapping = mutableMapOf<String, String>()
  val scalarAdapterMapping = mutableMapOf<String, String>()

  override fun mapScalar(
      graphQLName: String,
      targetName: String,
  ) {
    scalarTypeMapping[graphQLName] = targetName
  }

  override fun mapScalar(
      graphQLName: String,
      targetName: String,
      expression: String,
  ) {
    scalarTypeMapping[graphQLName] = targetName
    scalarAdapterMapping[graphQLName] = expression
  }

  override fun mapScalarToKotlinString(graphQLName: String) = mapScalar(graphQLName, "kotlin.String", "com.apollographql.apollo3.api.StringAdapter")
  override fun mapScalarToKotlinInt(graphQLName: String) = mapScalar(graphQLName, "kotlin.Int", "com.apollographql.apollo3.api.IntAdapter")
  override fun mapScalarToKotlinDouble(graphQLName: String) = mapScalar(graphQLName, "kotlin.Double", "com.apollographql.apollo3.api.DoubleAdapter")
  override fun mapScalarToKotlinFloat(graphQLName: String) = mapScalar(graphQLName, "kotlin.Float", "com.apollographql.apollo3.api.FloatAdapter")
  override fun mapScalarToKotlinLong(graphQLName: String) = mapScalar(graphQLName, "kotlin.Long", "com.apollographql.apollo3.api.LongAdapter")
  override fun mapScalarToKotlinBoolean(graphQLName: String) = mapScalar(graphQLName, "kotlin.Boolean", "com.apollographql.apollo3.api.BooleanAdapter")
  override fun mapScalarToKotlinAny(graphQLName: String) = mapScalar(graphQLName, "kotlin.Any", "com.apollographql.apollo3.api.AnyAdapter")

  override fun mapScalarToJavaString(graphQLName: String) = mapScalar(graphQLName, "java.lang.String", "com.apollographql.apollo3.api.Adapters.StringAdapter")
  override fun mapScalarToJavaInteger(graphQLName: String) = mapScalar(graphQLName, "java.lang.Integer", "com.apollographql.apollo3.api.Adapters.IntAdapter")
  override fun mapScalarToJavaDouble(graphQLName: String) = mapScalar(graphQLName, "java.lang.Double", "com.apollographql.apollo3.api.Adapters.DoubleAdapter")
  override fun mapScalarToJavaFloat(graphQLName: String) = mapScalar(graphQLName, "java.lang.Float", "com.apollographql.apollo3.api.Adapters.FloatAdapter")
  override fun mapScalarToJavaLong(graphQLName: String) = mapScalar(graphQLName, "java.lang.Long", "com.apollographql.apollo3.api.Adapters.LongAdapter")
  override fun mapScalarToJavaBoolean(graphQLName: String) = mapScalar(graphQLName, "java.lang.Boolean", "com.apollographql.apollo3.api.Adapters.BooleanAdapter")
  override fun mapScalarToJavaObject(graphQLName: String) = mapScalar(graphQLName, "java.lang.Object", "com.apollographql.apollo3.api.Adapters.AnyAdapter")

  override fun mapScalarToUpload(graphQLName: String) = mapScalar(graphQLName, "com.apollographql.apollo3.api.Upload", "com.apollographql.apollo3.api.UploadAdapter")

  override val alwaysGenerateTypesMatching: SetProperty<String> = objects.setProperty(String::class.java)
  override val generateSchema: Property<Boolean> = objects.property(Boolean::class.java)
  override val generatedSchemaName: Property<String> = objects.property(String::class.java)
  override val generateDataBuilders: Property<Boolean> = objects.property(Boolean::class.java)
  override val sealedClassesForEnumsMatching: ListProperty<String> = objects.listProperty(String::class.java)
  override val classesForEnumsMatching: ListProperty<String> = objects.listProperty(String::class.java)
}