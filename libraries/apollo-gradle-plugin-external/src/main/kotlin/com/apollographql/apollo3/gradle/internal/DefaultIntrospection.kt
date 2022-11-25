package com.apollographql.apollo3.gradle.internal

import com.apollographql.apollo3.gradle.api.Introspection
import org.gradle.api.file.RegularFileProperty
import org.gradle.api.model.ObjectFactory
import org.gradle.api.provider.MapProperty
import org.gradle.api.provider.Property


class DefaultIntrospection(objects: ObjectFactory): Introspection {
  override val endpointUrl: Property<String> = objects.property(String::class.java)
  override val headers: MapProperty<String, String> = objects.mapProperty(String::class.java, String::class.java)
  override val schemaFile: RegularFileProperty = objects.fileProperty()
}