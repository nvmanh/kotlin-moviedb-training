package com.framgia.moviedbtraining.db

import android.content.Context
import android.database.sqlite.SQLiteDatabase
import com.framgia.moviedbtraining.BuildConfig
import com.framgia.moviedbtraining.R
import com.framgia.moviedbtraining.model.Movies
import com.j256.ormlite.android.apptools.OrmLiteSqliteOpenHelper
import com.j256.ormlite.support.ConnectionSource
import com.j256.ormlite.table.TableUtils
import java.sql.SQLException

/**
 * Created by FRAMGIA\pham.duc.nam on 17/07/2017.
 */
class DatabaseHelper : OrmLiteSqliteOpenHelper {

  var mContext: Context? = null

  public constructor(context: Context, databaseName: String, factory: SQLiteDatabase.CursorFactory,
      databaseVersion: Int) : super(context, databaseName, factory, databaseVersion,
      R.raw.ormlite_config) {
    this.mContext = context
  }

  public constructor(context: Context) : super(context, DATABASE_NAME, null, DATABASE_VERSION,
      R.raw.ormlite_config) {
    this.mContext = context
  }

  override fun onCreate(database: SQLiteDatabase?, connectionSource: ConnectionSource?) {
    try {
      TableUtils.createTable(connectionSource, Movies::class.java)
    } catch (e: SQLException) {
      e.printStackTrace()
    }
  }

  override fun onUpgrade(database: SQLiteDatabase?, connectionSource: ConnectionSource?,
      oldVersion: Int, newVersion: Int) {
    TableUtils.dropTable<Movies, Any>(connectionSource, Movies::class.java, true)
    onCreate(database, connectionSource)
  }

  companion object {
    private val DATABASE_NAME = "movie.db"
    private val DATABASE_VERSION = BuildConfig.DATABASE_VERSION
  }
}