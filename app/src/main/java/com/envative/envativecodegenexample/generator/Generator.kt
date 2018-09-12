package com.envative.envativecodegenexample.generator

import android.content.Context
import com.envative.envativecodegenexample.generator.config.DataModelsConfig
import com.google.gson.Gson
import com.raizlabs.android.dbflow.annotation.Column
import com.raizlabs.android.dbflow.annotation.Database
import com.raizlabs.android.dbflow.annotation.PrimaryKey
import com.raizlabs.android.dbflow.annotation.Table
import com.squareup.kotlinpoet.*
import java.io.File

class Generator {

    val rootDir = "./app/src/main"
    val basePackagePath = "com.envative.envativecodegenexample"
    val databaseClass = ClassName("$basePackagePath.database", "DemoDatabase")

    fun buildDatabase() {

        val fileBuilder = FileSpec.builder(databaseClass.packageName, databaseClass.simpleName)
        val fileTypeSpecBuilder = TypeSpec.classBuilder(databaseClass.simpleName)

        fileTypeSpecBuilder
                .primaryConstructor(FunSpec.constructorBuilder()
                        .addParameter("context", Context::class)
                        .build())
                .addAnnotation(AnnotationSpec.builder(Database::class)
                        .addMember("name = ${databaseClass.simpleName}.NAME")
                        .addMember("version = ${databaseClass.simpleName}.VERSION")
                        .build())
                .addType(TypeSpec.companionObjectBuilder()
                        .addProperty(PropertySpec.builder("NAME", String::class)
                                .addModifiers(KModifier.CONST)
                                .mutable(false)
                                .initializer("\"demo_db\"")
                                .build())
                        .addProperty(PropertySpec.builder("VERSION", Int::class)
                                .addModifiers(KModifier.CONST)
                                .mutable(false)
                                .initializer("1")
                                .build())
                        .build())

        fileBuilder
                .addType(fileTypeSpecBuilder.build())
                .build()
                .writeTo(File("$rootDir/java/"))

        return
    }

    fun buildDataModels() {

        val baseConfigPath = "./app/src/main/assets/app_definitions"
        val dataModelsFileName = "data_models.json"
        val dataModelsString = File("$baseConfigPath/$dataModelsFileName").readText()

        val dataModels: DataModelsConfig = Gson().fromJson(dataModelsString, DataModelsConfig::class.java)

        for (dataModel in dataModels.models) {
            buildDataModel(dataModel)
        }
    }

    private fun buildDataModel(dataModel: DataModelsConfig.DataModelConfig) {
        val packagePath = "$basePackagePath.models"
        val className = "Contact"


        val fileBuilder = FileSpec.builder(packagePath, className)
        val fileTypeSpecBuilder = TypeSpec.classBuilder(className)

        fileTypeSpecBuilder
                .addAnnotation(AnnotationSpec.builder(Table::class)
                        .addMember("database = ${databaseClass.simpleName}::class")
                        .build())

        // add primary key
        for (primaryKey in dataModel.primaryKeys) {
            fileTypeSpecBuilder.addProperty(PropertySpec.builder(primaryKey, Int::class.asTypeName().asNullable())
                    .addAnnotation(AnnotationSpec.builder(PrimaryKey::class).build())
                    .initializer("null")
                    .build())
        }

        // add all other attributes
        for (attribute in dataModel.attributes) {
            attribute.name?.let {
                val type = attribute.getDataType()

                fileTypeSpecBuilder.addProperty(PropertySpec.builder(it, type.asNullable())
                        .addAnnotation(AnnotationSpec.builder(Column::class).build())
                        .initializer("null")
                        .build())
            }
        }

        fileBuilder
                .addImport(databaseClass.packageName, databaseClass.simpleName)
                .addType(fileTypeSpecBuilder.build())
                .build()
                .writeTo(File("$rootDir/java/"))
    }
}