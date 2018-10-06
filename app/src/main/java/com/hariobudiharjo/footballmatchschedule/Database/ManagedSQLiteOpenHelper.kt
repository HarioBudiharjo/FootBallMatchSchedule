package com.hariobudiharjo.footballmatchschedule.Database

import android.content.Context
import android.database.sqlite.SQLiteDatabase
import com.hariobudiharjo.footballmatchschedule.Model.favoriteModel
import org.jetbrains.anko.db.*

class MyDatabaseOpenHelper(ctx: Context) : ManagedSQLiteOpenHelper(ctx, "FavoriteTeam.db", null, 1) {
    companion object {
        private var instance: MyDatabaseOpenHelper? = null

        @Synchronized
        fun getInstance(ctx: Context): MyDatabaseOpenHelper {
            if (instance == null) {
                instance = MyDatabaseOpenHelper(ctx.applicationContext)
            }
            return instance!!
        }
    }

    override fun onCreate(db: SQLiteDatabase) {
        // Here you create tables
        db.createTable(favoriteModel.TABLE_FAVORITE, true,
                favoriteModel.ID to INTEGER + PRIMARY_KEY + AUTOINCREMENT,
                favoriteModel.TEAM_NAME_HOME to TEXT,
                favoriteModel.TEAM_NAME_AWAY to TEXT,
                favoriteModel.TEAM_SCORE_HOME to TEXT,
                favoriteModel.TEAM_SCORE_AWAY to TEXT,
                favoriteModel.DATE_EVENT to TEXT
        )
    }

    override fun onUpgrade(db: SQLiteDatabase, oldVersion: Int, newVersion: Int) {
        // Here you can upgrade tables, as usual
        db.dropTable(favoriteModel.TABLE_FAVORITE, true)
    }
}

// Access property for Context
val Context.database: MyDatabaseOpenHelper
    get() = MyDatabaseOpenHelper.getInstance(applicationContext)