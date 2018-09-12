package com.envative.envativecodegenexample.database

import android.content.Context
import com.raizlabs.android.dbflow.annotation.Database
import kotlin.Int
import kotlin.String

@Database(
        name = DemoDatabase.NAME,
        version = DemoDatabase.VERSION
)
class DemoDatabase(context: Context) {
    companion object {
        const val NAME: String = "demo_db"

        const val VERSION: Int = 1
    }
}
