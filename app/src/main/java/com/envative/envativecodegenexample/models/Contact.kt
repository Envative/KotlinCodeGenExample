package com.envative.envativecodegenexample.models

import com.envative.envativecodegenexample.database.DemoDatabase
import com.raizlabs.android.dbflow.annotation.Column
import com.raizlabs.android.dbflow.annotation.PrimaryKey
import com.raizlabs.android.dbflow.annotation.Table
import kotlin.Int
import kotlin.String

@Table(database = DemoDatabase::class)
class Contact {
    @PrimaryKey
    val id: Int? = null

    @Column
    val firstName: String? = null

    @Column
    val lastName: String? = null

    @Column
    val phone: String? = null
}
