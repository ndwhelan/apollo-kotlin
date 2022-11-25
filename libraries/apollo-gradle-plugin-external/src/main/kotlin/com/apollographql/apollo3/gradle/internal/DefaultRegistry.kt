package com.apollographql.apollo3.gradle.internal

import com.apollographql.apollo3.gradle.api.Registry
import org.gradle.api.file.RegularFileProperty
import org.gradle.api.model.ObjectFactory
import org.gradle.api.provider.Property

class DefaultRegistry(objects: ObjectFactory) : Registry {
  override val key: Property<String> = objects.property(String::class.java)
  override val graph: Property<String> = objects.property(String::class.java)
  override val graphVariant: Property<String> = objects.property(String::class.java)
  override val schemaFile: RegularFileProperty = objects.fileProperty()
}