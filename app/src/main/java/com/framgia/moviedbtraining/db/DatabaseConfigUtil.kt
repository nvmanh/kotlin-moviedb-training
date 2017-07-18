package com.framgia.moviedbtraining.db

import com.framgia.moviedbtraining.model.Movies
import com.framgia.moviedbtraining.model.User
import com.j256.ormlite.android.apptools.OrmLiteConfigUtil
import java.io.IOException
import java.sql.SQLException

/**
 * Created by FRAMGIA\pham.duc.nam on 17/07/2017.
 */
class DatabaseConfigUtil : OrmLiteConfigUtil() {
  companion object {
    private val classes = arrayOf<Class<*>>(Movies::class.java, User::class.java)
    @Throws(SQLException::class, IOException::class)
    fun main(args: Array<String>) {
      OrmLiteConfigUtil.writeConfigFile("ormlite_config.txt", classes)
    }
  }
}