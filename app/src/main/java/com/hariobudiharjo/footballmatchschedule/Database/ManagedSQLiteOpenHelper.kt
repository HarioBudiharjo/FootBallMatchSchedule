package com.hariobudiharjo.footballmatchschedule.Database

import android.content.Context
import android.database.sqlite.SQLiteDatabase
import com.hariobudiharjo.footballmatchschedule.Model.favoriteModel
import com.hariobudiharjo.footballmatchschedule.Model.favoriteTeamModel
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

        db.createTable(favoriteTeamModel.TABLE_FAVORITE_TEAM, true,
                favoriteTeamModel.ID to INTEGER + PRIMARY_KEY + AUTOINCREMENT,
                favoriteTeamModel.TEAM_NAME to TEXT,
                favoriteTeamModel.TEAM_IMAGE to TEXT
        )
    }

    override fun onUpgrade(db: SQLiteDatabase, oldVersion: Int, newVersion: Int) {
        // Here you can upgrade tables, as usual
        db.dropTable(favoriteModel.TABLE_FAVORITE, true)
        db.dropTable(favoriteTeamModel.TABLE_FAVORITE_TEAM, true)
    }
}

// Access property for Context
val Context.database: MyDatabaseOpenHelper
    get() = MyDatabaseOpenHelper.getInstance(applicationContext)