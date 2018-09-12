package com.envative.envativecodegenexample

import com.envative.envativecodegenexample.generator.Generator

fun main(args: Array<String>) {

    // build database
    Generator().buildDatabase()

    // build data models
    Generator().buildDataModels()

}