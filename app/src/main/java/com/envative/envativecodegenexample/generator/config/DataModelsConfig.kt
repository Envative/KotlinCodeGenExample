package com.envative.envativecodegenexample.generator.config

import com.squareup.kotlinpoet.TypeName
import com.squareup.kotlinpoet.asTypeName
import java.util.*

class DataModelsConfig {

    val models: List<DataModelConfig> = mutableListOf()

    class DataModelConfig {

        val modelClassName: String? = null
        val primaryKeys: MutableList<String> = mutableListOf()
        val attributes: MutableList<DataModelAttribute> = mutableListOf()

        class DataModelAttribute {
            val name: String? = null
            val type: String? = null
            val default: String? = null

            val t:kotlin.String = ""

            fun getDataType(): TypeName {
                val dataType = when (type) {
                    "String" -> kotlin.String::class.java
                    "Boolean" -> Boolean::class.java
                    "Int" -> Int::class.java
                    "Double" -> Double::class.java
                    "Date" -> Date::class.java
                    else -> kotlin.String::class.java
                }
                return dataType.kotlin.asTypeName()
            }
        }
    }
}

