package com.apollographql.apollo3.gradle.internal

import com.apollographql.apollo3.gradle.api.RegisterOperationsConfig
import org.gradle.api.model.ObjectFactory
import org.gradle.api.provider.Property

class DefaultRegisterOperationsConfig(objects: ObjectFactory): RegisterOperationsConfig {
  override val key: Property<String> = objects.property(String::class.java)
  override val graph: Property<String> = objects.property(String::class.java)
  override val graphVariant: Property<String> = objects.property(String::class.java)
}