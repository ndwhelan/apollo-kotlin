package com.apollographql.apollo3.gradle.api

import org.gradle.api.Action
import org.gradle.api.provider.ListProperty
import org.gradle.api.provider.Property
import org.gradle.api.provider.SetProperty

interface SchemaConfig {
  val packageName: Property<String>

  fun fromFiles(files: Any)
  fun fromFile(file: Any)
  fun fromProject(projectPath: String)
  fun fromDependency(coordinates: String)

  fun introspection(configure: Action<in Introspection>)

  fun registry(configure: Action<in Registry>)

  /**
   * Map a GraphQL scalar type to the Java/Kotlin type.
   * The adapter must be configured at runtime via [com.apollographql.apollo3.ApolloClient.Builder.addCustomScalarAdapter].
   *
   * @param graphQLName: the name of the scalar to map as found in the GraphQL schema
   * @param targetName: the fully qualified Java or Kotlin name of the type the scalar is mapped to
   *
   * For example: `mapScalar("Date", "com.example.Date")`
   */
  fun mapScalar(graphQLName: String, targetName: String)

  /**
   * Map a GraphQL scalar type to the Java/Kotlin type and provided adapter expression.
   * The adapter will be configured at compile time and you must not call [com.apollographql.apollo3.ApolloClient.Builder.addCustomScalarAdapter].
   *
   * @param graphQLName: the name of the scalar to map as found in the GraphQL schema
   * @param targetName: the fully qualified Java or Kotlin name of the type the scalar is mapped to
   * @param expression: an expression that will be used by the codegen to get an adapter for the
   * given scalar. [expression] is passed verbatim to JavaPoet/KotlinPoet.
   *
   * For example in Kotlin:
   * - `mapScalar("Date", "com.example.Date", "com.example.DateAdapter")` (a top level property or object)
   * - `mapScalar("Date", "com.example.Date", "com.example.DateAdapter()")` (create a new instance every time)
   * Or in Java:
   * - `mapScalar("Date", "com.example.Date", "com.example.DateAdapter.INSTANCE")` (a top level property or object)
   * - `mapScalar("Date", "com.example.Date", "new com.example.DateAdapter()")` (create a new instance every time)
   */
  fun mapScalar(graphQLName: String, targetName: String, expression: String)

  /**
   * Map the given GraphQL scalar to [kotlin.String] and use the builtin adapter
   */
  fun mapScalarToKotlinString(graphQLName: String)

  /**
   * Map the given GraphQL scalar to [kotlin.Int] and use the builtin adapter
   */
  fun mapScalarToKotlinInt(graphQLName: String)

  /**
   * Map the given GraphQL scalar to [kotlin.Double] and use the builtin adapter
   */
  fun mapScalarToKotlinDouble(graphQLName: String)

  /**
   * Map the given GraphQL scalar to [kotlin.Float] and use the builtin adapter
   */
  fun mapScalarToKotlinFloat(graphQLName: String)

  /**
   * Map the given GraphQL scalar to [kotlin.Long] and use the builtin adapter
   */
  fun mapScalarToKotlinLong(graphQLName: String)

  /**
   * Map the given GraphQL scalar to [kotlin.Boolean] and use the builtin adapter
   */
  fun mapScalarToKotlinBoolean(graphQLName: String)

  /**
   * Map the given GraphQL scalar to [kotlin.Any] and use the builtin adapter
   */
  fun mapScalarToKotlinAny(graphQLName: String)

  /**
   * Map the given GraphQL scalar to [java.lang.String] and use the builtin adapter
   */
  fun mapScalarToJavaString(graphQLName: String)

  /**
   * Map the given GraphQL scalar to [java.lang.Integer] and use the builtin adapter
   */
  fun mapScalarToJavaInteger(graphQLName: String)

  /**
   * Map the given GraphQL scalar to [java.lang.Double] and use the builtin adapter
   */
  fun mapScalarToJavaDouble(graphQLName: String)

  /**
   * Map the given GraphQL scalar to [java.lang.Float] and use the builtin adapter
   */
  fun mapScalarToJavaFloat(graphQLName: String)

  /**
   * Map the given GraphQL scalar to [java.lang.Long] and use the builtin adapter
   */
  fun mapScalarToJavaLong(graphQLName: String)

  /**
   * Map the given GraphQL scalar to [java.lang.Boolean] and use the builtin adapter
   */
  fun mapScalarToJavaBoolean(graphQLName: String)

  /**
   * Map the given GraphQL scalar to [java.lang.Object] and use the builtin adapter
   */
  fun mapScalarToJavaObject(graphQLName: String)

  /**
   * Map the given GraphQL scalar to [com.apollographql.apollo3.api.Upload] and use the builtin adapter
   */
  fun mapScalarToUpload(graphQLName: String)

  /**
   * A list of [Regex] patterns matching [schema coordinates](https://github.com/magicmark/graphql-spec/blob/add_field_coordinates/rfcs/SchemaCoordinates.md)
   * for types and fields that should be generated whether they are used by queries/fragments in this module or not.
   *
   * When using multiple modules, Apollo Kotlin will generate all the types by default in the root module
   * because the root module doesn't know what types are going to be used by dependent modules. This can be prohibitive in terms
   * of compilation speed for large projects. If that's the case, opt-in the types that are used by multiple dependent modules here.
   * You don't need to add types that are used by a single dependent module.
   *
   * Examples:
   * - listOf(".*"): generate every type and every field in the schema
   * - listOf("User"): generate the user type
   * - listOf(".*User): generate all types ending with "User"
   * - listOf("User\\..*"): generate all fields of type "User"
   *
   * Default value: if (generateApolloMetadata) listOf(".*") else listOf()
   */
  val alwaysGenerateTypesMatching: SetProperty<String>

  /**
   * Whether to generate the Schema class. The Schema class lists all composite
   * types in order to access __typename and/or possibleTypes.
   *
   * Default: false
   */
  val generateSchema: Property<Boolean>

  /**
   * Class name to use when generating the Schema class.
   *
   * Default: "__Schema"
   */
  val generatedSchemaName: Property<String>

  val generateDataBuilders: Property<Boolean>

  val sealedClassesForEnumsMatching: ListProperty<String>
  val classesForEnumsMatching: ListProperty<String>
}